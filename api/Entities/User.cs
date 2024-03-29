using Microsoft.AspNetCore.Identity;

namespace api.Entities;

public class User : IdentityUser<Guid>
{
    public string Password { get; set; } = null!;
}