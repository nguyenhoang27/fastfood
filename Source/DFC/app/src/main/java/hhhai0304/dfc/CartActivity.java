package hhhai0304.dfc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.CartAdapter;
import helper.CovertHelper;
import helper.DatabaseHelper;
import helper.JSONParser;
import helper.PHPUrl;
import item.CartItem;
import item.Cookie;

public class CartActivity extends AppCompatActivity
{
    CartAdapter adapter;
    ArrayList<CartItem> itemList;
    ListView lvCart;
    TextView tvCartDetail;
    Button btnPay;
    DatabaseHelper db;
    int Total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Giỏ hàng");

        lvCart = (ListView) findViewById(R.id.lvCart);
        btnPay = (Button) findViewById(R.id.btnPay);
        tvCartDetail = (TextView) findViewById(R.id.tvCartDetail);

        CovertHelper c = new CovertHelper();

        db = new DatabaseHelper(this);
        itemList = new ArrayList<>();
        itemList = db.getCartItem();
        if (itemList.size() == 0)
        {
            tvCartDetail.setText("GIỎ HÀNG TRỐNG");
            tvCartDetail.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btnPay.setVisibility(View.GONE);
        }
        else
        {
            for (int i = 0; i < itemList.size(); i++)
                Total = Total + (itemList.get(i).getPrice() * itemList.get(i).getQuantity());
            btnPay.setText("THANH TOÁN: " + c.chuanHoaPrice(Total + "").toLowerCase());
            adapter = new CartAdapter(this, itemList);
            lvCart.setAdapter(adapter);
        }
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cookie cookie = db.layCookie();
                createOrderDialog(cookie);
            }
        });
    }

    public class addOrder extends AsyncTask<Void, Void, Void>
    {
        List<NameValuePair> nameValuePairs;
        int status;
        ProgressDialog progress;

        public addOrder(List<NameValuePair> nameValuePairs)
        {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = new ProgressDialog(CartActivity.this);
            progress.setMessage("Đang gửi thông tin đơn hàng");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            JSONParser task = new JSONParser();
            JSONObject addOrders = task.getJsonFromUrl(PHPUrl.orders, nameValuePairs);
            try
            {
                status = addOrders.getInt("status");
            }
            catch(Exception e)
            {
                Log.i("Lỗi Kết nối", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            progress.dismiss();
            if(status == 0 || status == 1)
                Toast.makeText(CartActivity.this, "Lỗi kết nối, hãy thử lại sau", Toast.LENGTH_SHORT).show();
            else if(status == 2)
            {
                Toast.makeText(CartActivity.this, "Gửi Đặt hàng thành công.", Toast.LENGTH_LONG).show();
                db.xoaCart();
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private void createOrderDialog(final Cookie cookie)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_orderconfirm, null);
        builder.setView(view);

        final RadioButton rbOriginal = (RadioButton) view.findViewById(R.id.rbOriginal);
        rbOriginal.setText(cookie.Address + ", Quận " + cookie.District + ", " + cookie.City);
        final RadioButton rbOther = (RadioButton) view.findViewById(R.id.rbOther);
        final EditText edtOtherAddress = (EditText) view.findViewById(R.id.edtOtherAddress);
        final EditText edtOtherPhone = (EditText) view.findViewById(R.id.edtOtherPhone);
        final LinearLayout llOther = (LinearLayout) view.findViewById(R.id.llOther);

        RadioGroup rbGroup = (RadioGroup) view.findViewById(R.id.rbGroup);
        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbOther.isChecked()) {
                    llOther.setVisibility(View.VISIBLE);
                }
                else {
                    llOther.setVisibility(View.GONE);
                }
            }
        });

        builder.setTitle("Địa chỉ giao hàng");
        builder.setPositiveButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                CovertHelper c = new CovertHelper();
                Calendar e = Calendar.getInstance();
                String OrderID = c.randomID(10);

                JSONArray jsonOrder = new JSONArray();

                //Dữ liệu cho bảng orders_detail
                for (int i = 0; i < itemList.size(); i++)
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ProductID", itemList.get(i).getProductID());
                        jsonObject.put("Quantity", itemList.get(i).getQuantity() + "");
                        jsonObject.put("Note", itemList.get(i).getNote());

                        jsonOrder.put(jsonObject);
                    }
                    catch (Exception ex){}
                }
                //Dữ liệu cho bảng orders
                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair("OrderID", OrderID));
                nameValuePairs.add(new BasicNameValuePair("UserID", cookie.UserID));
                nameValuePairs.add(new BasicNameValuePair("OrderDate", e.get(Calendar.YEAR) + "-" + e.get(Calendar.MONTH) + "-"
                        + e.get(Calendar.DAY_OF_MONTH) + " " + e.get(Calendar.HOUR)
                        + ":" + e.get(Calendar.MINUTE) + ":" + e.get(Calendar.SECOND)));
                nameValuePairs.add(new BasicNameValuePair("Price", Total + ""));
                nameValuePairs.add(new BasicNameValuePair("Detail", jsonOrder.toString()));

                if (rbOriginal.isChecked())
                {
                    nameValuePairs.add(new BasicNameValuePair("Address", cookie.Address + ", Quận " + cookie.District + ", " + cookie.City));
                    nameValuePairs.add(new BasicNameValuePair("Phone", cookie.Phone));
                }
                else
                {
                    nameValuePairs.add(new BasicNameValuePair("Address", edtOtherAddress.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("Phone", edtOtherPhone.getText().toString()));
                }
                addOrder task = new addOrder(nameValuePairs);
                task.execute();
            }
        });
        builder.setNegativeButton("HỦY", null);
        builder.create().show();
    }
}