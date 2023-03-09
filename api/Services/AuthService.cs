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

    public AuthService(AppDbContext dbContext) =>
        _dbContext = dbContext;

    public override async Task<AuthToken> Register(UserCredentials request, ServerCallContext context)
    {
        if (await FindUserOrNull(request) is { })
            throw new Exception($"user with username {request.Username} already exists");

        var user = new User
        {
            Username = request.Username,
            Password = request.Password
        };
        _dbContext.Users.Add(user);
        await _dbContext.SaveChangesAsync();

        return CreateToken(request.Username);
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
        var user = await _dbContext.Users.FirstAsync(u => u.Username == context.GetUsername());

        return new MeResponse
        {
            Username = user.Username
        };
    }

    private async Task<User?> FindUserOrNull(UserCredentials userCredentials) =>
        await _dbContext.Users.FirstOrDefaultAsync(u => u.Username == userCredentials.Username);

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