using System.Text;
using System.Text.Json;
using consumer.Entities;
using RabbitMQ.Client;

namespace consumer.Services;

public class RabbitProducer
{
    private const string QueueName = "statistics_queue";
    private const string ExchangeName = "statistics_exchange";
    private const string RoutingKeyName = "staistics_routing_key";

    private readonly IModel _rabbitChannel;

    public RabbitProducer()
    {
        var factory = new ConnectionFactory { HostName = "rabbit" };
        _rabbitChannel = factory.CreateConnection().CreateModel();
    }

    public void ProduceStatisticsUpdate(BookStatisticEntity bookStatistic)
    {
        _rabbitChannel.QueueDeclare(
            queue: QueueName,
            durable: true,
            exclusive: false,
            autoDelete: false,
            arguments: null);

        _rabbitChannel.ExchangeDeclare(
            exchange: ExchangeName,
            type: "topic",
            durable: true,
            autoDelete: false,
            arguments: null);
        
        _rabbitChannel.QueueBind(
            queue: QueueName,
            exchange: ExchangeName,
            routingKey: RoutingKeyName,
            arguments: null);

        var body = Encoding.UTF8.GetBytes(JsonSerializer.Serialize(bookStatistic));
        _rabbitChannel.BasicPublish(exchange: ExchangeName, routingKey: RoutingKeyName, body: body);
    }
}