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
    private readonly MessagesEventContainer _messagesEventContainer;
    private readonly ILogger<ChatService> _logger;

    public ChatService(AppDbContext dbContext, MessagesEventContainer messagesEventContainer,
        ILogger<ChatService> logger)
    {
        _dbContext = dbContext;
        _messagesEventContainer = messagesEventContainer;
        _logger = logger;
    }

    [Authorize]
    public override async Task<Empty> SendMessage(SendMessageRequest request, ServerCallContext context)
    {
        var author = await _dbContext.Users.FirstAsync(u => u.Username == context.GetUsername());
        var message = new Message
        {
            Author = author,
            Text = request.Text,
            SentDateTime = DateTime.Now
        };
        _dbContext.Messages.Add(message);
        await Task.WhenAll(_messagesEventContainer.SendMessage(message), _dbContext.SaveChangesAsync());
        return new Empty();
    }

    [Authorize]
    public override async Task GetMessages(Empty request, IServerStreamWriter<MessageResponse> responseStream,
        ServerCallContext context)
    {
        var username = context.GetUsername();

        async Task OnNewMessage(Message message)
        {
            try
            {
                await responseStream.WriteAsync(new MessageResponse
                    { AuthorUsername = message.Author.Username, Text = message.Text, Id = message.Id.ToString() });
            }
            catch (Exception e)
            {
                _logger.LogError("Cannot send message to {username}. Error: {error}",
                    username,
                    e.Message);
            }
        }

        var lastMessages = await _dbContext.Messages
            .Include(m => m.Author)
            .OrderByTakeLastToListAsync(m => m.SentDateTime, 20);
        foreach (var message in lastMessages)
            await OnNewMessage(message);

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