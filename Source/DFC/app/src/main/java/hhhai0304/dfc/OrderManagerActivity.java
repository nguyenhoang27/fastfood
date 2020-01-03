package hhhai0304.dfc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import helper.CovertHelper;
import helper.DatabaseHelper;
import helper.DetailDialogHelper;
import helper.JSONParser;
import helper.PHPUrl;
import item.Cookie;
import item.OrderManagerItem;

public class OrderManagerActivity extends AppCompatActivity
{
    LinearLayout llChoDuyet, llDaDuyet, llHoanTat;
    TextView tvChoDuyet, tvDaDuyet, tvHoanTat;
    Cookie c;
    DatabaseHelper db;
    JSONArray JArr = null;
    int choduyet = 0, daduyet = 0, ketthuc = 0;
    ArrayList<OrderManagerItem> listChoduyet, listDaduyet, listKetthuc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanager);
        setTitle("Quản lý Đơn hàng");
        db = new DatabaseHelper(this);
        c = db.layCookie();

        llChoDuyet = (LinearLayout)findViewById(R.id.llChoDuyet);
        llDaDuyet = (LinearLayout)findViewById(R.id.llDaDuyet);
        llHoanTat = (LinearLayout)findViewById(R.id.llHoanTat);
        tvChoDuyet = (TextView)findViewById(R.id.tvChoDuyet);
        tvDaDuyet = (TextView)findViewById(R.id.tvDaDuyet);
        tvHoanTat = (TextView)findViewById(R.id.tvHoanTat);

        listChoduyet = new ArrayList<>();
        listDaduyet = new ArrayList<>();
        listKetthuc = new ArrayList<>();

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        nameValuePairs.add(new BasicNameValuePair("UserID", c.UserID));
        getOrders task = new getOrders(nameValuePairs);
        task.execute();
    }

    public class getOrders extends AsyncTask<Void, Void, Void>
    {
        List<NameValuePair> nameValuePairs;
        ProgressDialog progress;

        public getOrders(List<NameValuePair> nameValuePairs)
        {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(OrderManagerActivity.this);
            progress.setMessage("Đang cập nhật danh sách Đơn hàng");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            JSONParser task = new JSONParser();
            JSONObject json_object = task.getJsonFromUrl(PHPUrl.get_orders, nameValuePairs);
            try
            {
                JArr = json_object.getJSONArray("json_data");
                for(int i=0; i < JArr.length(); i++)
                {
                    JSONObject od = JArr.getJSONObject(i);
                    String OrderID = od.getString("OrderID");
                    String OrderDate = od.getString("OrderDate");
                    String Address = od.getString("Address");
                    String Phone = od.getString("Phone");
                    int Price = od.getInt("Price");
                    String OrderStatus = od.getString("OrderStatus");

                    if(OrderStatus.equals("0"))
                    {
                        OrderManagerItem item = new OrderManagerItem(llChoDuyet, OrderID, OrderDate, Address, Phone, Price);
                        listChoduyet.add(item);
                        choduyet++;
                    }
                    else if(OrderStatus.equals("1"))
                    {
                        OrderManagerItem item = new OrderManagerItem(llDaDuyet, OrderID, OrderDate, Address, Phone, Price);
                        listChoduyet.add(item);
                        daduyet++;
                    }
                    else if(OrderStatus.equals("2"))
                    {
                        OrderManagerItem item = new OrderManagerItem(llHoanTat, OrderID, OrderDate, Address, Phone, Price);
                        listChoduyet.add(item);
                        ketthuc++;
                    }
                }
            }
            catch(Exception e)
            {
                Log.i("Lỗi Kết nối", e.toString());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            progress.dismiss();
            tvChoDuyet.setText("Đang chờ duyệt (" + choduyet + ")");
            tvDaDuyet.setText("Đã duyệt (" + daduyet + ")");
            tvHoanTat.setText("Đã kết thúc (" + ketthuc + ")");
            for (int i = 0; i < listChoduyet.size(); i++)
                addOrderView(listChoduyet.get(i).llThis, listChoduyet.get(i).OrderID, listChoduyet.get(i).OrderDate, listChoduyet.get(i).Address, listChoduyet.get(i).Phone, listChoduyet.get(i).Price);
            for (int i = 0; i < listDaduyet.size(); i++)
                addOrderView(listDaduyet.get(i).llThis, listDaduyet.get(i).OrderID, listDaduyet.get(i).OrderDate, listDaduyet.get(i).Address, listDaduyet.get(i).Phone, listDaduyet.get(i).Price);
            for (int i = 0; i < listKetthuc.size(); i++)
                addOrderView(listKetthuc.get(i).llThis, listKetthuc.get(i).OrderID, listKetthuc.get(i).OrderDate, listKetthuc.get(i).Address, listKetthuc.get(i).Phone, listKetthuc.get(i).Price);
        }
    }

    private void addOrderView(LinearLayout llView, final String OrderID, String OrderDate, final String Address, final String Phone, int Price)
    {
        CovertHelper c = new CovertHelper();
        View vOrder = View.inflate(OrderManagerActivity.this, R.layout.item_ordermanager, null);
        TextView tvOrderID = (TextView) vOrder.findViewById(R.id.tvOrderID);
        TextView tvOrderPrice = (TextView) vOrder.findViewById(R.id.tvOrderPrice);
        TextView tvOrderDate = (TextView) vOrder.findViewById(R.id.tvOrderDate);

        tvOrderID.setText("Mã đơn hàng: " + OrderID);
        tvOrderDate.setText("Ngày lập: " + OrderDate);
        tvOrderPrice.setText(c.chuanHoaPrice(Price + ""));

        vOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialogHelper dialog = new DetailDialogHelper(OrderManagerActivity.this);
                dialog.createDetailDialog(OrderID);
            }
        });
        llView.addView(vOrder);
    }
}
