using api.Entities;
using Microsoft.EntityFrameworkCore;

namespace api.Data;

public class AppDbContext : DbContext
{
    public AppDbContext()
    {
        AppContext.SetSwitch("Npgsql.DisableDateTimeInfinityConversions", true);
        AppContext.SetSwitch("Npgsql.EnableLegacyTimestampBehavior", true);
    }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) => 
        optionsBuilder.UseNpgsql("Host=db;Port=5432;Username=testUser;Password=testPassword;Database=testDb");
    public DbSet<Message> Messages { get; set; } = null!;
}