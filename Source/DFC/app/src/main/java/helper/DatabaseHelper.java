package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import item.CartItem;
import item.CategoryItem;
import item.Cookie;
import item.Products;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static String DB_NAME = "b8_16415714_shopapp";
    private static final int DATABASE_VERSION = 1;

    private static final String Table_Cookie = "Cookie";
    private static final String Cookie_UserID = "UserID";
    private static final String Cookie_Username = "Username";
    private static final String Cookie_Password = "Password";
    private static final String Cookie_FirstName = "FirstName";
    private static final String Cookie_LastName = "LastName";
    private static final String Cookie_Address = "Address";
    private static final String Cookie_District = "District";
    private static final String Cookie_City = "City";
    private static final String Cookie_Phone = "Phone";
    private static final String Cookie_Email = "Email";

    private static final String Table_Products = "Products";
    private static final String Products_ProductID = "ProductID";
    private static final String Products_ProductName = "ProductName";
    private static final String Products_Detail = "Detail";
    private static final String Products_Avatar = "Avatar";
    private static final String Products_Price = "Price";
    private static final String Products_CategoryID = "CategoryID";
    private static final String Products_ProductNameKhongDau = "KhongDau";

    private static final String Table_Orders = "Orders";
    private static final String Orders_ProductID = "ProductID";
    private static final String Orders_ProductName = "ProductName";
    private static final String Orders_Quantity = "Quantity";
    private static final String Orders_Price = "Price";
    private static final String Orders_Avatar = "Avatar";
    private static final String Orders_Note = "Note";

    private static final String Table_Category = "Category";
    private static final String Category_CategoryID = "CategoryID";
    private static final String Category_Name = "Name";
    private static final String Category_Detail = "Detail";

    private final Context context;
    CovertHelper c = new CovertHelper();

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Khởi tạo các bảng trong Database
        String create_cookie_table = "create table " + Table_Cookie + "(" + Cookie_UserID
                + " text primary key, " + Cookie_Username + " text, " + Cookie_Password + " text, " + Cookie_FirstName + " text, "
                + Cookie_LastName + " text, " + Cookie_Address + " text, " + Cookie_District + " text, "
                + Cookie_City + " text, " + Cookie_Phone + " text, " + Cookie_Email + " text)";
        String create_products_table = "create table " + Table_Products + "(" + Products_ProductID
                + " text primary key, " + Products_ProductName + " text, " + Products_Detail + " text, "
                + Products_Avatar + " text, " + Products_Price + " int, " + Products_ProductNameKhongDau + " int, "
                + Products_CategoryID + " text)";
        String create_orders_table = "create table " + Table_Orders + "(" + Orders_ProductID
                + " text primary key, " + Orders_ProductName + " text, " + Orders_Quantity + " int, "
                + Orders_Price + " int, " + Orders_Avatar + " text, " + Orders_Note + " text)";
        String create_category_table = "create table " + Table_Category + "(" + Category_CategoryID
                + " text primary key, " + Category_Name + " text, " + Category_Detail + " text)";

        db.execSQL(create_cookie_table);
        db.execSQL(create_products_table);
        db.execSQL(create_orders_table);
        db.execSQL(create_category_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + Table_Cookie);
        db.execSQL("drop table if exists " + Table_Products);
        db.execSQL("drop table if exists " + Table_Orders);
        db.execSQL("drop table if exists " + Table_Category);
        onCreate(db);
    }

    //Thêm category
    public void themCategory(CategoryItem ca)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Category_CategoryID, ca.getCategoryID());
        values.put(Category_Name, ca.getName());
        values.put(Category_Detail, ca.getDetail());

        db.insert(Table_Category, null, values);
        db.close();
    }

    //Thêm product mới vào database
    public void themProduct(Products pd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Products_ProductID, pd.ProductID);
        values.put(Products_ProductName, pd.ProductName);
        values.put(Products_Detail, pd.Detail);
        values.put(Products_Avatar, pd.Avatar);
        values.put(Products_Price, pd.Price);
        values.put(Products_CategoryID, pd.CategoryID);
        values.put(Products_ProductNameKhongDau, c.boDauChuoi(pd.ProductName));

        db.insert(Table_Products, null, values);
        db.close();
    }

    //Tạo cookie mới cho phiên đăng nhập
    public void taoCookie(Cookie ck)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Cookie_UserID, ck.UserID);
        values.put(Cookie_Username, ck.Username);
        values.put(Cookie_Password, ck.Password);
        values.put(Cookie_FirstName, ck.FirstName);
        values.put(Cookie_LastName, ck.LastName);
        values.put(Cookie_Address, ck.Address);
        values.put(Cookie_District, ck.District);
        values.put(Cookie_City, ck.City);
        values.put(Cookie_Phone, ck.Phone);
        values.put(Cookie_Email, ck.Email);

        db.insert(Table_Cookie, null, values);
        db.close();
    }

    //Thêm Món ăn vào Giỏ hàng
    public void themCart(CartItem it)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Orders_ProductID, it.getProductID());
        values.put(Orders_ProductName, it.getProductName());
        values.put(Orders_Quantity, it.getQuantity());
        values.put(Orders_Price, it.getPrice());
        values.put(Orders_Avatar, it.getAvatarURL());
        values.put(Orders_Note, it.getNote());

        try
        {
            db.insertOrThrow(Table_Orders, null, values);
        }
        catch (SQLiteConstraintException e)
        {
            int thisQuantity = 0;
            String selectQuery = "SELECT " + Orders_Quantity + " FROM " + Table_Orders + " WHERE " + Orders_ProductID + " = '" + it.getProductID() + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            thisQuantity = cursor.getInt(0);

            updateCartQuantity(it.getProductID(), thisQuantity + it.getQuantity());
        }
        db.close();
    }

    //Lấy Món ăn lên Giỏ hàng
    public ArrayList<CartItem> getCartItem()
    {
        ArrayList<CartItem> fullcart = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String[] colunm = {Orders_ProductID, Orders_ProductName, Orders_Quantity, Orders_Price, Orders_Avatar, Orders_Note};
        Cursor cursor = db.query(true, Table_Orders, colunm, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                String ID = cursor.getString(0);
                String Name = cursor.getString(1);
                int Quantity = cursor.getInt(2);
                int Price = cursor.getInt(3);
                String Avatar = cursor.getString(4);
                String Note = cursor.getString(5);

                CartItem cart = new CartItem();
                cart.setProductID(ID);
                cart.setProductName(Name);
                cart.setQuantity(Quantity);
                cart.setPrice(Price);
                cart.setAvatarURL(Avatar);
                cart.setNote(Note);
                fullcart.add(cart);
            }	while (cursor.moveToNext());
        }
        db.close();
        return fullcart;
    }

    //Lấy cookie
    public Cookie layCookie()
    {
        Cookie cookie;
        SQLiteDatabase db = this.getWritableDatabase();

        String[] colunm = {Cookie_UserID, Cookie_Username, Cookie_Password, Cookie_FirstName, Cookie_LastName,
                            Cookie_Address, Cookie_District, Cookie_City, Cookie_Phone, Cookie_Email};
        Cursor cursor = db.query(true, Table_Cookie, colunm, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                String UserID = cursor.getString(0);
                String Username = cursor.getString(1);
                String Password = cursor.getString(2);
                String FirstName = cursor.getString(3);
                String LastName = cursor.getString(4);
                String Address = cursor.getString(5);
                String District = cursor.getString(6);
                String City = cursor.getString(7);
                String Phone = cursor.getString(8);
                String Email = cursor.getString(9);

                cookie = new Cookie(UserID, Username, Password, FirstName, LastName, Address, District, City, Phone, Email);
            }	while (cursor.moveToNext());
        }
        else
            cookie = new Cookie("null", "null", "null", "null", "null", "null", "null", "null", "null", "null");
        db.close();
        return cookie;
    }

    //Lấy category
    public ArrayList<CategoryItem> getAllCategory()
    {
        ArrayList<CategoryItem> caList = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Table_Category;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst())
        {
            caList = new ArrayList<>();
            do
            {
                String CategoryID = cursor.getString(0);
                String Name = cursor.getString(1);
                String Detail = cursor.getString(2);

                CategoryItem ca = new CategoryItem();
                ca.setCategoryID(CategoryID);
                ca.setName(Name);
                ca.setDetail(Detail);

                caList.add(ca);
            }	while (cursor.moveToNext());
        }
        db.close();
        return caList;
    }

    //Xóa cookie
    public void xoaCookie()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Cookie, null, null);
        db.close();
    }
    //Xóa category
    public void xoaCategory()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Category, null, null);
        db.close();
    }
    //Xóa tất cả Products
    public void xoaAllProducts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Products, null, null);
        db.close();
    }
    //Xóa giỏ hàng
    public void xoaCart()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Orders, null, null);
        db.close();
    }

    //Lấy danh sách các Món ăn
    public ArrayList<Products> getAllProducts(String cot, boolean tangdan, String category, String search)
    {
        String tanggiam = "";
        if (tangdan) tanggiam = "ASC";
        else tanggiam = "DESC";

        ArrayList<Products> pd = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "", and = " WHERE (";
        if (!category.equals(""))
            where = " WHERE " + Products_CategoryID + " = '" + category + "'";
        if (!search.equals(""))
        {
            if (!where.equals(""))
            {
                and = " AND (";
            }
            where = where + and + Products_ProductNameKhongDau + " like '%" + search + "%' OR " + Products_ProductName  + " like '%" + search + "%')";
        }
        String sortQuery = "SELECT * FROM " + Table_Products + where + " ORDER BY " + cot + " " + tanggiam;
        Cursor cursor = db.rawQuery(sortQuery, null);

        if (cursor != null && cursor.moveToFirst())
        {
            pd = new ArrayList<>();
            do
            {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String detail = cursor.getString(2);
                String avatar = cursor.getString(3);
                int price = cursor.getInt(4);
                String ca_id = cursor.getString(5);
                Products product = new Products(id, name, detail, avatar, price, ca_id);
                pd.add(product);
            }	while (cursor.moveToNext());
        }
        db.close();
        return pd;
    }

    public void deleteCart(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Orders, Orders_ProductID + " = ?", new String[] { id });
        db.close();
    }

    public void updateCartQuantity(String id, int quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Orders_Quantity, quantity);
        db.update(Table_Orders, cv, Orders_ProductID + " = '" + id + "'", null);
        db.close();
    }
}