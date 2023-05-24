using consumer.Services;
using consumer.Services.Background;

start:
try
{
    var builder = Host.CreateDefaultBuilder(args);

    builder.ConfigureServices(services => services.AddSingleton<KafkaMessageConsumer>());
    builder.ConfigureServices(services => { services.AddHostedService<MessageDelayingService>(); });

    var host = builder.Build();
    host.Run();
}
catch
{
    goto start;
}