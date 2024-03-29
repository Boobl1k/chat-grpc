using System.Text;
using api;
using api.Data;
using api.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Polly;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>();
builder.Services.AddSingleton<MessagesEventContainer>();

builder.Services.AddAuthentication(x =>
    {
        x.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
        x.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
    })
    .AddJwtBearer(x =>
    {
        x.RequireHttpsMetadata = false;
        x.SaveToken = true;
        x.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey("idhf239f23ifuosdf0"u8.ToArray()),
            ValidateIssuer = false,
            ValidateAudience = false
        };
    });

builder.Services.AddAuthorization();

builder.Services.AddDbContext<MyBookContext>(options =>
{
    options.UseNpgsql("Host=db;Port=5432;Username=testUser;Password=testPassword;Database=mybook");
    options.UseOpenIddict();
});

builder.Services.AddGrpc();

builder.Services.AddCors();

var app = builder.Build();

await Policy.Handle<Exception>()
    .WaitAndRetryForeverAsync(_ => TimeSpan.FromSeconds(10),
        onRetry: (exception, retryTime) =>
            Console.WriteLine($"Error on migration apply: {exception.Message} | Retry in {retryTime}"))
    .ExecuteAsync(async () =>
    {
        await using var scope = app.Services.CreateAsyncScope();
        var context = scope.ServiceProvider.GetService<AppDbContext>();
        await context!.Database.MigrateAsync();
    });

app.Use((ctx, next) =>
{
    Console.WriteLine(ctx.Request.GetDisplayUrl());
    return next();
});

app.UseCors(x => x.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader());

app.UseAuthentication().UseAuthorization();

app.MapGrpcService<ChatService>();
app.MapGrpcService<AuthService>();
app.MapGet("/",
    () =>
        "Communication with gRPC endpoints must be made through a gRPC client. To learn how to create a client, visit: https://go.microsoft.com/fwlink/?linkid=2086909");

app.Run();