using System.Text.Json.Serialization;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace consumer.Entities;

public class BookStatisticMongoModel
{
    public BookStatisticMongoModel(string bookId, int readCount)
    {
        BookId = bookId;
        ReadCount = readCount;
    }

    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string? Id { get; set; }
    public string BookId { get; init; }
    public int ReadCount { get; set; }

    public BookStatisticEntity ToEntity() => new()
    {
        BookId = BookId,
        ReadCount = ReadCount
    };
}