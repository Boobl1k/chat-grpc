using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using api.Data;
using api.Entities;
using api.Extensions;
using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

namespace api.Services;

public class AuthService : Auth.AuthBase
{
    private readonly AppDbContext _dbContext;
    private readonly MyBookContext _myBookContext;

    public AuthService(AppDbContext dbContext, MyBookContext myBookContext)
    {
        _dbContext = dbContext;
        _myBookContext = myBookContext;
    }

    public override async Task<AuthToken> Login(UserCredentials request, ServerCallContext context)
    {
        var user = await FindUserOrNull(request);
        if (user is null) throw new Exception("user not found");
        if (user.Password != request.Password) throw new Exception("password is wrong");

        return CreateToken(request.Username);
    }

    [Authorize]
    public override async Task<MeResponse> Me(Empty request, ServerCallContext context)
    {
        var user = await _myBookContext.Users.FirstAsync(u => u.UserName == context.GetUsername());

        return new MeResponse
        {
            Username = user.UserName
        };
    }

    private async Task<User?> FindUserOrNull(UserCredentials userCredentials) =>
        await _myBookContext.Users.FirstOrDefaultAsync(u => u.UserName == userCredentials.Username);

    private static AuthToken CreateToken(string username)
    {
        var credentials = new SigningCredentials(new SymmetricSecurityKey("idhf239f23ifuosdf0"u8.ToArray()),
            SecurityAlgorithms.HmacSha256);
        var claims = new[]
        {
            new Claim(ClaimTypes.NameIdentifier, username),
            new Claim(ClaimTypes.Name, username)
        };

        var token = new JwtSecurityToken(claims: claims, signingCredentials: credentials,
            issuer: "http://localhost:5000", audience: "http://localhost:5000", expires: DateTime.Now.AddYears(1));
        return new AuthToken { Token = new JwtSecurityTokenHandler().WriteToken(token) };
    }
}