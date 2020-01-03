package item;

public class Products
{
    public String ProductID, ProductName, Detail, Avatar, CategoryID;
    public int Price;
    public Products(){}
    public Products(String ProductID, String ProductName, String Detail, String Avatar, int Price, String CategoryID)
    {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Detail = Detail;
        this.Price = Price;
        this.Avatar = Avatar;
        this.CategoryID = CategoryID;
    }
}
