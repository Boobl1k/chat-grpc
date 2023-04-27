using api.Data;
using api.Entities;
using api.Extensions;
using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore;

namespace api.Services;

public class ChatService : Chat.ChatBase
{
    private readonly AppDbContext _dbContext;
    private readonly MyBookContext _myBookContext;
    private readonly MessagesEventContainer _messagesEventContainer;
    private readonly ILogger<ChatService> _logger;

    public ChatService(AppDbContext dbContext, MessagesEventContainer messagesEventContainer,
        ILogger<ChatService> logger, MyBookContext myBookContext)
    {
        _dbContext = dbContext;
        _messagesEventContainer = messagesEventContainer;
        _logger = logger;
        _myBookContext = myBookContext;
    }

    [Authorize]
    public override async Task<Empty> SendMessage(SendMessageRequest request, ServerCallContext context)
    {
        var author = await _myBookContext.Users.FirstAsync(u => u.UserName == context.GetUsername());
        var message = new Message
        {
            Author = author.Id,
            Text = request.Text,
            SentDateTime = DateTime.Now
        };
        _dbContext.Messages.Add(message);
        await Task.WhenAll(_messagesEventContainer.SendMessage(message, author), _dbContext.SaveChangesAsync());
        return new Empty();
    }

    [Authorize]
    public override async Task GetMessages(Empty request, IServerStreamWriter<MessageResponse> responseStream,
        ServerCallContext context)
    {
        var username = context.GetUsername();

        async Task OnNewMessage(Message message, User author)
        {
            try
            {
                await responseStream.WriteAsync(new MessageResponse
                    { AuthorUsername = author.UserName, Text = message.Text, Id = message.Id.ToString() });
            }
            catch (Exception e)
            {
                _logger.LogError("Cannot send message to {username}. Error: {error}",
                    username,
                    e.Message);
            }
        }

        var lastMessages = await _dbContext.Messages
            .OrderByTakeLastToListAsync(m => m.SentDateTime, 20); 
        foreach (var pair in lastMessages.Select(m => new { Message = m, Author = _myBookContext.Users.First(u => u.Id == m.Author) }))
            await OnNewMessage(pair.Message, pair.Author);

        //it's not actually thread-safe. We can lose seme messages sent in this moment
        //https://github.com/Boobl1k/chat-grpc/issues/14

        _logger.LogInformation("user {username} connected", username);
        _messagesEventContainer.OnNewMessage += OnNewMessage;
        context.CancellationToken.Register(() =>
        {
            _logger.LogInformation("user {username} disconnected", username);
            _messagesEventContainer.OnNewMessage -= OnNewMessage;
        });

        while (!context.CancellationToken.IsCancellationRequested) await Task.Delay(5000);
        _logger.LogInformation("stream ended for {username}", username);
    }
}