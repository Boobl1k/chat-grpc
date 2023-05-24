using System.Net;
using Confluent.Kafka;
using Confluent.Kafka.Admin;

namespace MyBook.WebApi.Services;

public class StatisticsService
{
    private const string Topic = "statistics-topic";
    private bool _topicCreated;

    private readonly ProducerConfig _config = new()
    {
        BootstrapServers = "kafka:29092",
        Acks = Acks.All,
        ClientId = Dns.GetHostName()
    };

    public async Task ProduceStatisticsUpdateMessage(string bookId)
    {
        await EnsureTopicCreated();
        using var producer = new ProducerBuilder<Null, string>(_config).Build();
        await producer.ProduceAsync(Topic, new Message<Null, string> { Value = bookId });
    }

    private async Task EnsureTopicCreated()
    {
        if (_topicCreated) return;
        var adminConfig = new AdminClientConfig { BootstrapServers = "kafka:29092" };
        using var adminClient = new AdminClientBuilder(adminConfig).Build();
        var topicSpecification = new TopicSpecification { Name = Topic, };
        try
        {
            await adminClient.CreateTopicsAsync(new List<TopicSpecification> { topicSpecification });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }

        _topicCreated = true;
    }
}