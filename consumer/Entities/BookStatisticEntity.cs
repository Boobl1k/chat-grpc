namespace consumer.Entities;

public class BookStatisticEntity
{
    public required string BookId { get; init; }
    public required int ReadCount { get; set; }
}