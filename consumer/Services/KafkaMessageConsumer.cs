using Confluent.Kafka;
using consumer.Entities;
using MongoDB.Driver;

namespace consumer.Services;

public class KafkaMessageConsumer
{
    private const string Topic = "statistics-topic";
    private const string MongoDbName = "statistics_db";
    private const string MongoCollectionName = "books_statistics";
    private readonly ILogger<KafkaMessageConsumer> _logger;
    private readonly IMongoCollection<BookStatistic> _booksStatisticCollection;

    private readonly ConsumerConfig _config = new()
    {
        GroupId = "first_group",
        BootstrapServers = "kafka:29092",
        AutoOffsetReset = AutoOffsetReset.Earliest
    };

    public KafkaMessageConsumer(ILogger<KafkaMessageConsumer> logger)
    {
        _logger = logger;
        var mongoClient = new MongoClient("mongodb://mongo:27017");
        var mongoDatabase = mongoClient.GetDatabase(MongoDbName);
        _booksStatisticCollection = mongoDatabase.GetCollection<BookStatistic>(MongoCollectionName);
    }

    public async Task StartConsumingMessages()
    {
        using var consumer = new ConsumerBuilder<Ignore, string>(_config).Build();
        consumer.Subscribe(Topic);
        try
        {
            while (true)
            {
                _logger.LogInformation("Started consuming at {DateTime}", DateTime.Now);
                var consumeResult =
                    consumer.Consume(new CancellationTokenSource(TimeSpan.FromSeconds(30)).Token);

                var bookId = consumeResult.Message.Value ?? throw new Exception("Дима накосячил опять");
                _logger.LogInformation("Consumed book id [{bookId}]", bookId);

                if (await _booksStatisticCollection.CountDocumentsAsync(_ => true) == 0)
                {
                    await _booksStatisticCollection.InsertOneAsync(new BookStatistic(bookId, 1));
                }
                else
                {
                    var cursor = await _booksStatisticCollection.FindAsync(bs => bs.BookId == bookId);
                    if (await cursor.FirstOrDefaultAsync() is { } statistic)
                    {
                        statistic.ReadCount++;
                        await _booksStatisticCollection.ReplaceOneAsync(bs => bs.BookId == bookId, statistic);
                    }
                    else
                    {
                        statistic = new BookStatistic(bookId, 1);
                        await _booksStatisticCollection.InsertOneAsync(statistic);
                    }
                }
            }
        }
        catch (OperationCanceledException)
        {
            _logger.LogInformation("\n MESSAGE CANNOT BE CONSUMED \n");
            consumer.Close();
        }
        catch (Exception e)
        {
            _logger.LogError(e, "");
        }
        finally
        {
            _logger.LogInformation("Connection closed");
        }
    }
}