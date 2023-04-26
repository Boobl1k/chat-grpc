FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY MyBook.WebApi/MyBook.WebApi.csproj ./MyBook.WebApi/
RUN dotnet restore "MyBook.WebApi/MyBook.WebApi.csproj"
COPY . .
RUN dotnet build "MyBook.WebApi/MyBook.WebApi.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "MyBook.WebApi/MyBook.WebApi.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MyBook.WebApi.dll"]
