package helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import hhhai0304.dfc.R;
import item.CartItem;

public class ProductDialogHelper extends AlertDialog
{
    ImageView imgProductDetail;
    TextView tvProductDetail, tvProductName, tvPrice, tvQuantity;
    Button btnMinus, btnPlus;
    EditText edtNote;
    static int price;
    int priceWithQuantity, thisQuantity = 1;
    DatabaseHelper db;
    CovertHelper c;
    public ProductDialogHelper(Context context) {
        super(context);
    }

    public void createProductDialog(final String pName, String pDetail, final String pAvatar, String pPrice, final String pID)
    {
        price = Integer.parseInt(pPrice);
        priceWithQuantity = Integer.parseInt(pPrice);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_productdetail, null);
        builder.setView(view);
        db = new DatabaseHelper(getContext());
        c = new CovertHelper();

        edtNote = (EditText)view.findViewById(R.id.edtNote);
        imgProductDetail = (ImageView)view.findViewById(R.id.imgProductDetail);
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.noimage)
                .showImageOnFail(R.drawable.noimage).build();
        imageLoader.displayImage(pAvatar, imgProductDetail, options);

        tvProductName = (TextView)view.findViewById(R.id.tvProductName);
        tvProductName.setText(pName);

        tvProductDetail = (TextView)view.findViewById(R.id.tvProductDetail);
        tvProductDetail.setText("Chi tiết món ăn:\n" + pDetail);

        tvPrice = (TextView)view.findViewById(R.id.tvPrice);
        tvPrice.setText(c.chuanHoaPrice(priceWithQuantity + ""));

        tvQuantity = (TextView)view.findViewById(R.id.tvQuantity);
        btnMinus = (Button)view.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisQuantity > 1)
                {
                    thisQuantity--;
                    updatePrice(thisQuantity);
                    tvQuantity.setText(thisQuantity + "");
                }
            }
        });

        btnPlus = (Button)view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisQuantity < 100) {
                    thisQuantity++;
                    updatePrice(thisQuantity);
                    tvQuantity.setText(thisQuantity + "");
                }
            }
        });

        builder.setPositiveButton("Thêm vào giỏ hàng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CartItem cart = new CartItem();
                cart.setProductID(pID);
                cart.setProductName(pName);
                cart.setQuantity(thisQuantity);
                cart.setPrice(price);
                cart.setNote(edtNote.getText().toString());
                cart.setAvatarURL(pAvatar);
                db.themCart(cart);
                Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    private void updatePrice(int quantity)
    {
        priceWithQuantity = price * quantity;
        tvPrice.setText(c.chuanHoaPrice(priceWithQuantity + ""));
    }
}