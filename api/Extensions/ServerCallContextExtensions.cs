using Grpc.Core;

namespace api.Extensions;

public static class ServerCallContextExtensions
{
    public static string GetUsername(this ServerCallContext context) =>
        context.GetHttpContext().User.Identity?.Name ?? throw new Exception("User should be authorized");
}