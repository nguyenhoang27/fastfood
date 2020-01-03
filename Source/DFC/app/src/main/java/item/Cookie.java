package item;

public class Cookie
{
    public String UserID;
    public String Username;
    public String Password;
    public String FirstName;
    public String LastName;
    public String Address;
    public String District;
    public String City;
    public String Phone;
    public String Email;

    public Cookie() {}
    public Cookie(String UserID, String Username, String Password, String FirstName, String LastName,
                  String Address, String District, String City, String Phone, String Email)
    {
        this.UserID = UserID;
        this.Username = Username;
        this.Password = Password;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Address = Address;
        this.District = District;
        this.City = City;
        this.Phone = Phone;
        this.Email = Email;
    }
}
