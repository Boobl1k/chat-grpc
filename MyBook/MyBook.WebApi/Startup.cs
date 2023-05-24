using Microsoft.EntityFrameworkCore;
using Microsoft.OpenApi.Models;
using MyBook.DataAccess;
using MyBook.WebApi.GraphQl;
using MyBook.WebApi.Services;
using Polly;

namespace MyBook.WebApi;

public class Startup
{
    private readonly IConfiguration _configuration;
    private readonly IWebHostEnvironment _env;

    public Startup(IConfiguration configuration, IWebHostEnvironment env)
    {
        _configuration = configuration;
        _env = env;
    }

    public void ConfigureServices(IServiceCollection services)
    {
        services.AddControllersWithViews();
        services.AddEndpointsApiExplorer();
        services.AddSwaggerGen(option =>
            {
                option.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
                {
                    In = ParameterLocation.Header,
                    Description = "Please enter a valid token",
                    Name = "Authorization",
                    Type = SecuritySchemeType.Http,
                    BearerFormat = "JWT",
                    Scheme = "Bearer"
                });
                option.AddSecurityRequirement(new OpenApiSecurityRequirement
                {
                    {
                        new OpenApiSecurityScheme
                        {
                            Reference = new OpenApiReference
                            {
                                Type = ReferenceType.SecurityScheme,
                                Id = "Bearer"
                            }
                        },
                        new string[] { }
                    }
                });
            }
        );

        services.AddCors(opt =>
        {
            opt.AddDefaultPolicy(builder =>
            {
                builder
                    .AllowAnyOrigin()
                    .AllowAnyHeader()
                    .AllowAnyMethod();
            });
        });
        services
            .AddAuthenticationAndJwt()
            .AddAuthorization()
            .AddOpenIddictServer(_env);

        services.AddDbContext<ApplicationContext>(options =>
        {
            options.UseNpgsql(_configuration.GetConnectionString("sqlConnection"), b => b.MigrationsAssembly("MyBook.WebApi"));
            options.UseOpenIddict();
        });
        services.AddIdentity();


        services.AddControllersWithViews().AddNewtonsoftJson(options =>
            options.SerializerSettings.ReferenceLoopHandling = Newtonsoft.Json.ReferenceLoopHandling.Ignore
        );

        services.AddScoped<AuthorizeManager>();

        services.AddGraphQLServer()
            .AddQueryType<BooksQuery>();

        services.AddSingleton<StatisticsService>();
    }

    public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
    {
        if (env.IsDevelopment())
        {
            app.UseDeveloperExceptionPage();
            app.UseSwagger();
            app.UseSwaggerUI();
        }
        
        app
            .UseStaticFiles()
            .UseRouting()
            .UseAuthentication()
            .UseAuthorization()
            .UseCors()
            .UseEndpoints(endpoints =>
            {
                endpoints.MapDefaultControllerRoute();
                endpoints.MapGraphQL((PathString)"/graphql");
            });
    }

    public static IHostBuilder CreateHostBuilder(string[] args)
    {
        return Host
            .CreateDefaultBuilder(args)
            .ConfigureAppConfiguration((context, builder) => builder
                .AddJsonFile("appsettings.json", false, true)
                .AddJsonFile($"appsettings.{context.HostingEnvironment.EnvironmentName}.json", true)
                .AddEnvironmentVariables())
            .ConfigureWebHostDefaults(webBuilder => { webBuilder.UseStartup<Startup>(); });
    }

    public static void RunApp(string[] args)
    {
        var app = CreateHostBuilder(args).Build();

        Policy.Handle<Exception>()
            .WaitAndRetryForeverAsync(_ => TimeSpan.FromSeconds(10),
                onRetry: (exception, retryTime) =>
                    Console.WriteLine($"Error on migration apply: {exception.Message} | Retry in {retryTime}"))
            .ExecuteAsync(async () =>
            {
                using var scope = app.Services.CreateScope();
                var context = scope.ServiceProvider.GetService<ApplicationContext>();
                await context!.Database.MigrateAsync();
            }).GetAwaiter().GetResult();

        app.Run();
    }
}