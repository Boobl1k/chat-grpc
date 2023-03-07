namespace api.Entities;

public class Message
{
    public Guid Id { get; set; } = Guid.NewGuid();
    public required User Author { get; set; }
    public required string Text { get; set; }
    public required DateTime SentDateTime { get; set; }
}