package item;

import android.widget.LinearLayout;

public class OrderManagerItem
{
    public LinearLayout llThis;
    public String OrderID, OrderDate, Address, Phone;
    public int Price;

    public OrderManagerItem(LinearLayout llThis, String OrderID, String OrderDate, String Address, String Phone, int Price)
    {
        this.llThis = llThis;
        this.OrderID = OrderID;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.Phone = Phone;
        this.Price = Price;
    }
}
