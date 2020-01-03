package helper;

import java.util.ArrayList;

import item.Products;
import item.ProductsListItem;

public class SetProductListHelper
{
    ProductsListItem item;
    ArrayList<Products> aProducts;
    ArrayList<ProductsListItem> itemList;

    public SetProductListHelper(ArrayList<Products> aProducts) {
        this.aProducts = aProducts;
    }

    public ArrayList<ProductsListItem> getProductsListItem() throws NullPointerException
    {
        itemList = new ArrayList<>();
        if ((aProducts.size() % 2) == 0)
        {
            for (int i = 0; i < (aProducts.size() - 1); i++)
            {
                item = new ProductsListItem();
                item.setImgProduct01url(aProducts.get(i).Avatar);
                item.setTvProductName01(aProducts.get(i).ProductName);
                item.setTvPrice01(aProducts.get(i).Price + "");
                item.setDetail01(aProducts.get(i).Detail);
                item.setID01(aProducts.get(i).ProductID);

                item.setImgProduct02url(aProducts.get(i + 1).Avatar);
                item.setTvProductName02(aProducts.get(i + 1).ProductName);
                item.setTvPrice02(aProducts.get(i + 1).Price + "");
                item.setDetail02(aProducts.get(i + 1).Detail);
                item.setID02(aProducts.get(i + 1).ProductID);
                itemList.add(item);
                i++;
            }
        }
        else
        {
            for (int i = 0; i < aProducts.size(); i++)
            {
                item = new ProductsListItem();
                item.setImgProduct01url(aProducts.get(i).Avatar);
                item.setTvProductName01(aProducts.get(i).ProductName);
                item.setTvPrice01(aProducts.get(i).Price + "");
                item.setDetail01(aProducts.get(i).Detail);
                item.setID01(aProducts.get(i).ProductID);

                if (i == (aProducts.size() - 1))
                {
                    item.setImgProduct02url("");
                    item.setTvProductName02("");
                    item.setTvPrice02("");
                    item.setDetail02("");
                    item.setID02("");
                }
                else
                {
                    item.setImgProduct02url(aProducts.get(i + 1).Avatar);
                    item.setTvProductName02(aProducts.get(i + 1).ProductName);
                    item.setTvPrice02(aProducts.get(i + 1).Price + "");
                    item.setDetail02(aProducts.get(i + 1).Detail);
                    item.setID02(aProducts.get(i + 1).ProductID);
                }
                itemList.add(item);
                i++;
            }
        }
        return itemList;
    }
}