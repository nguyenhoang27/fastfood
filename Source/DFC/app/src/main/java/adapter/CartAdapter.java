package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import helper.CovertHelper;
import helper.DatabaseHelper;
import hhhai0304.dfc.CartActivity;
import hhhai0304.dfc.R;
import item.CartItem;

public class CartAdapter extends BaseAdapter
{
    Context context;
    ArrayList<CartItem> listItem;

    public CartAdapter(Context context, ArrayList<CartItem> listItem)
    {
        this.context = context;
        this.listItem = listItem;
    }
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view;
        CovertHelper c = new CovertHelper();
        if (convertView == null)
        {
            view = View.inflate(context, R.layout.item_cart, null);
        }
        else
        {
            view = convertView;
        }

        TextView tvCartName = (TextView) view.findViewById(R.id.tvCartName);
        TextView tvCartPrice = (TextView) view.findViewById(R.id.tvCartPrice);
        TextView tvCartQuantity = (TextView) view.findViewById(R.id.tvCartQuantity);
        TextView tvCartNote = (TextView) view.findViewById(R.id.tvCartNote);

        tvCartName.setText(c.rutGon(listItem.get(position).getProductName()));
        tvCartQuantity.setText(listItem.get(position).getQuantity() + " phần");
        int price = listItem.get(position).getQuantity() * listItem.get(position).getPrice();
        tvCartPrice.setText(c.chuanHoaPrice(price + ""));
        if (listItem.get(position).getNote().equals(""))
            tvCartNote.setVisibility(View.INVISIBLE);
        else
            tvCartNote.setText("Ghi chú: " + listItem.get(position).getNote());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOptionDialog(listItem.get(position).getProductName(), listItem.get(position).getProductID());
            }
        });
        return view;
    }

    private void createOptionDialog(String name, final String idForDel)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(name);
        builder.setMessage("XÓA khỏi giỏ hàng hoặc SỬA SỐ LƯỢNG");
        builder.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteCart(idForDel);
                Intent i = new Intent(context, CartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });
        builder.setNeutralButton("SỬA SỐ LƯỢNG", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createUpdatequantityDialog(idForDel);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createUpdatequantityDialog(final String idForUpdate)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_updatequantity, null);
        builder.setView(view);
        final NumberPicker num = (NumberPicker) view.findViewById(R.id.numberPicker);
        num.setMinValue(1);
        num.setMaxValue(100);
        builder.setTitle("Cập nhật số lượng");
        builder.setPositiveButton("CẬP NHẬT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateCartQuantity(idForUpdate, num.getValue());
                Intent i = new Intent(context, CartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });
        builder.setNegativeButton("HỦY BỎ", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
