package helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailDialogHelper extends AlertDialog
{
    String OrderID;
    JSONArray JArr = null;
    String chitiet = "";
    AlertDialog.Builder builder;

    public DetailDialogHelper(Context context) {
        super(context);
    }

    public void createDetailDialog(String id)
    {
        OrderID = id;

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        nameValuePairs.add(new BasicNameValuePair("OrderID", OrderID));

        getDetail task = new getDetail(nameValuePairs);
        task.execute();
    }

    public class getDetail extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog progress;
        List<NameValuePair> nameValuePairs;

        public getDetail(List<NameValuePair> nameValuePairs)
        {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(getContext());
            progress.setMessage("Đang cập nhật chi tiết Đơn hàng");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            JSONParser task = new JSONParser();
            JSONObject json_object = task.getJsonFromUrl(PHPUrl.get_orders_detail, nameValuePairs);
            try
            {
                JArr = json_object.getJSONArray("orders_detail");
                for(int i=0; i < JArr.length(); i++)
                {
                    JSONObject od = JArr.getJSONObject(i);
                    chitiet += od.getString("ProductName") + " x" + od.getString("Quantity");
                    if (i != JArr.length() - 1)
                    {
                        chitiet += "\n";
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
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            progress.dismiss();
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Chi tiết đơn hàng " + OrderID);
            builder.setPositiveButton("XONG", null);
            builder.setMessage(chitiet);
            builder.create().show();
        }
    }
}