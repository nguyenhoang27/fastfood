package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

import helper.CovertHelper;
import helper.ProductDialogHelper;
import hhhai0304.dfc.R;
import item.ProductsListItem;

public class ProductsAdapter extends BaseAdapter
{
    Context context;
    private ArrayList<ProductsListItem> itemList;

    public ProductsAdapter(Context context, ArrayList<ProductsListItem> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ItemInList_ViewHolder holder;
        CovertHelper c = new CovertHelper();
        convertView = View.inflate(context, R.layout.item_listview, null);
        holder = new ItemInList_ViewHolder();
        holder.tvProductName01 = (TextView) convertView.findViewById(R.id.tvProductName01);
        holder.tvProductName02 = (TextView) convertView.findViewById(R.id.tvProductName02);
        holder.tvPrice01 = (TextView) convertView.findViewById(R.id.tvPrice01);
        holder.tvPrice02 = (TextView) convertView.findViewById(R.id.tvPrice02);
        holder.imgProduct01 = (ImageView) convertView.findViewById(R.id.imgProduct01);
        holder.imgProduct02 = (ImageView) convertView.findViewById(R.id.imgProduct02);
        convertView.setTag(holder);

        holder.tvProductName01.setText(c.rutGon(itemList.get(position).getTvProductName01()));
        holder.tvPrice01.setText(c.chuanHoaPrice(itemList.get(position).getTvPrice01()));
        setProductImage(holder.imgProduct01, itemList.get(position).getImgProduct01url());

        if(itemList.get(position).getTvProductName02().equals(""))
        {
            holder.llProduct02 = (RelativeLayout) convertView.findViewById(R.id.llProduct02);
            holder.llProduct02.setVisibility(View.GONE);
        }
        else
        {
            holder.tvProductName02.setText(c.rutGon(itemList.get(position).getTvProductName02()));
            setProductImage(holder.imgProduct02, itemList.get(position).getImgProduct02url());
            holder.tvPrice02.setText(c.chuanHoaPrice(itemList.get(position).getTvPrice02()));
        }
        holder.imgProduct01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDialogHelper dialog = new ProductDialogHelper(context);
                dialog.createProductDialog(itemList.get(position).getTvProductName01(), itemList.get(position).getDetail01(), itemList.get(position).getImgProduct01url(), itemList.get(position).getTvPrice01(), itemList.get(position).getID01());
            }
        });

        holder.imgProduct02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDialogHelper dialog = new ProductDialogHelper(context);
                dialog.createProductDialog(itemList.get(position).getTvProductName02(), itemList.get(position).getDetail02(), itemList.get(position).getImgProduct02url(), itemList.get(position).getTvPrice02(), itemList.get(position).getID02());
            }
        });

        return convertView;
    }

    private void setProductImage(ImageView imgProduct, String avatarUrl) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.noimage)
                .showImageOnFail(R.drawable.noimage)
                .displayer(new RoundedBitmapDisplayer(20)).build();
        imageLoader.displayImage(avatarUrl, imgProduct, options);
    }

    public class ItemInList_ViewHolder
    {
        ImageView imgProduct01, imgProduct02;
        TextView tvProductName01, tvProductName02, tvPrice01, tvPrice02;
        RelativeLayout llProduct02;
    }
}