using api.Entities;

namespace api;

/// <summary>
/// <para>Simple container for message sending events</para>
/// <para>Should be singleton</para>
/// </summary>
public class MessagesEventContainer
{
    /// <summary>
    /// <para>Event called in <see cref="SendMessage"/> in order to send message to all subscribers</para>
    /// <para>Thread safe (at least when we don't add custom `add`, `remove` handlers)</para>
    /// </summary>
    public event Func<Message, Task>? OnNewMessage;

    /// <summary>
    /// Method just calls <see cref="OnNewMessage"/> event
    /// </summary>
    /// <param name="message">message to send</param>
    public async Task SendMessage(Message message)
    {
        if (OnNewMessage is { })
            await OnNewMessage.Invoke(message);
    }
}