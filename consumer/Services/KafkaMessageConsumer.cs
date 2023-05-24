using Confluent.Kafka;

namespace consumer.Services;

public class KafkaMessageConsumer
{
    private const string Topic = "statistics-topic";
    private readonly ILogger<KafkaMessageConsumer> _logger;

    private readonly ConsumerConfig _config = new()
    {
        GroupId = "first_group",
        BootstrapServers = "kafka:29092",
        AutoOffsetReset = AutoOffsetReset.Earliest
    };

    public KafkaMessageConsumer(ILogger<KafkaMessageConsumer> logger) => _logger = logger;

    public void StartConsumingMessages()
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

                _logger.LogInformation("{Message}", consumeResult.Message.Value);
            }
        }
        catch (OperationCanceledException)
        {
            _logger.LogInformation("\n MESSAGE CANNOT BE CONSUMED \n");
            consumer.Close();
        }
        finally
        {
            _logger.LogInformation("Connection closed");
        }
    }
}