using Microsoft.EntityFrameworkCore;
using MyBook.DataAccess;
using MyBook.Entity;

namespace MyBook.WebApi.GraphQl;

public class BooksQuery
{
    public IQueryable<Book> BooksList([Service] ApplicationContext dbContext) =>
        dbContext.Books.Include(x => x.Author);
}