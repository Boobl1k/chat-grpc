using System.Linq.Expressions;
using Microsoft.EntityFrameworkCore;

namespace api.Extensions;

public static class QueryableExtensions
{
    public static async Task<List<T>> OrderByTakeLastToListAsync<T, TKey>(
        this IQueryable<T> queryable, Expression<Func<T, TKey>> orderBy, int count)
    {
        var res = await queryable.OrderByDescending(orderBy).Take(count).ToListAsync();
        res.Reverse();
        return res;
    }
}