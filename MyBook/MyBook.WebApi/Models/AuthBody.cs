using System.ComponentModel;

namespace MyBook.Models;

public class AuthBody
{
    [System.ComponentModel.DefaultValue("password")]
    public string grant_type { get; set; }
    public string username { get; set; }
    public string password { get; set; }
}