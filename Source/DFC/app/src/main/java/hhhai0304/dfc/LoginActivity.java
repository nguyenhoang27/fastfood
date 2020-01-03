package hhhai0304.dfc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import helper.JSONParser;
import helper.PHPUrl;
import hhhai0304.backend.registration.Registration;
import item.Cookie;

public class LoginActivity extends Activity
{
    Button btnDangnhap;
    TextView tvDangKy;
    EditText edtTaikhoan, edtMatkhau;
    LinearLayout llLogin;

    DatabaseHelper db;
    JSONArray JArr = null;

    Registration regService = null;
    GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        llLogin = (LinearLayout)findViewById(R.id.llLogin);
        tvDangKy = (TextView)findViewById(R.id.tvDangKy);
        btnDangnhap = (Button)findViewById(R.id.btnDangnhap);
        edtTaikhoan = (EditText)findViewById(R.id.edtTaikhoan);
        edtTaikhoan.setTypeface(Typeface.DEFAULT);
        edtMatkhau = (EditText)findViewById(R.id.edtMatkhau);
        edtMatkhau.setTypeface(Typeface.DEFAULT);

        db = new DatabaseHelper(LoginActivity.this);
        Cookie cookie = db.layCookie();

        if(!cookie.UserID.equals("null"))
        {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTaikhoan.getText().toString().equals("") || edtMatkhau.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Hãy điền đầy đủ Tài khoản và Mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkLogin task = new checkLogin(edtTaikhoan.getText().toString(), edtMatkhau.getText().toString());
                    task.execute();
                }
            }
        });

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public class checkLogin extends AsyncTask<Void, Void, Void>
    {
        int status;
        String Username, Password, RegID = "";
        ProgressDialog progress;

        public checkLogin(String Username, String Password) {
            this.Username = Username;
            this.Password = Password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Đang đăng nhập");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            if (regService == null)
            {
                Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://686776422514.appspot.com/_ah/api/");
                regService = builder.build();
            }

            try
            {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(LoginActivity.this);
                }
                RegID = gcm.register(PHPUrl.gcm_senderid);
                regService.register(RegID).execute();

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

            JSONParser task = new JSONParser();
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("user", Username));
            nameValuePairs.add(new BasicNameValuePair("pass", Password));
            nameValuePairs.add(new BasicNameValuePair("RegID", RegID));

            JSONObject json_object = task.getJsonFromUrl(PHPUrl.login, nameValuePairs);
            try
            {
                JArr = new JSONArray();
                JArr = json_object.getJSONArray("json_data");
                JSONObject ck = JArr.getJSONObject(0);
                Cookie cookie = new Cookie();
                status = ck.getInt("status");
                if (status == 2)
                {
                    cookie.UserID = ck.getString("UserID");
                    cookie.Username = ck.getString("Username");
                    cookie.Password = ck.getString("Password");
                    cookie.FirstName = ck.getString("FirstName");
                    cookie.LastName = ck.getString("LastName");
                    cookie.Address = ck.getString("Address");
                    cookie.District = ck.getString("District");
                    cookie.City = ck.getString("City");
                    cookie.Phone = ck.getString("Phone");
                    cookie.Email = ck.getString("Email");
                    db.taoCookie(cookie);
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
            if(status == 0)
                Toast.makeText(LoginActivity.this, "Tài khoản hoặc Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            else if(status == 1)
                Toast.makeText(LoginActivity.this, "Lỗi kết nối, hãy thử lại sau", Toast.LENGTH_SHORT).show();
            else if(status == 2)
            {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}