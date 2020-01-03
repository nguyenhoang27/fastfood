package hhhai0304.dfc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import helper.JSONParser;
import helper.PHPUrl;
import item.Cookie;

public class ChangePassActivity extends Activity
{
    EditText edtOldPass, edtNewPass, edtReNewPass;
    Button btnChangePass;
    Cookie c;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        db = new DatabaseHelper(this);

        edtOldPass = (EditText)findViewById(R.id.edtOldPass);
        edtOldPass.setTypeface(Typeface.DEFAULT);
        edtNewPass = (EditText)findViewById(R.id.edtNewPass);
        edtNewPass.setTypeface(Typeface.DEFAULT);
        edtReNewPass = (EditText)findViewById(R.id.edtReNewPass);
        edtReNewPass.setTypeface(Typeface.DEFAULT);
        btnChangePass = (Button)findViewById(R.id.btnChangePass);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = new Cookie();
                c = db.layCookie();
                String oldPass, newPass, newRepass;
                oldPass = edtOldPass.getText().toString();
                newPass = edtNewPass.getText().toString();
                newRepass = edtReNewPass.getText().toString();

                if(newPass.equals("") || newPass.length() < 6)
                {
                    Toast.makeText(ChangePassActivity.this, "Mật khẩu phải từ 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(oldPass.equals(c.Password))
                    {
                        if(newPass.equals(newRepass))
                        {
                            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                            nameValuePairs.add(new BasicNameValuePair("UserID", c.UserID));
                            nameValuePairs.add(new BasicNameValuePair("Password", newPass));

                            changePassword task = new changePassword(nameValuePairs);
                            task.execute();
                        }
                        else
                        {
                            Toast.makeText(ChangePassActivity.this, "Nhập lại mật khẩu mới không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ChangePassActivity.this, "Mật khẩu cũ sai" + oldPass + " - " + c.Password, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public class changePassword extends AsyncTask<Void, Void, Void>
    {
        int status;
        List<NameValuePair> nameValuePairs;
        ProgressDialog progress;

        public changePassword(List<NameValuePair> nameValuePairs)
        {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = new ProgressDialog(ChangePassActivity.this);
            progress.setMessage("Đang thực hiện đổi mật khẩu");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            JSONParser task = new JSONParser();
            JSONObject json_object = task.getJsonFromUrl(PHPUrl.changepass, nameValuePairs);
            try
            {
                status = json_object.getInt("status");
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
                Toast.makeText(ChangePassActivity.this, "Lỗi kết nối, hãy thử lại sau", Toast.LENGTH_SHORT).show();
            else if(status == 2)
            {
                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công. Hãy đăng nhập lại.", Toast.LENGTH_LONG).show();
                db.xoaCookie();
                Intent i = new Intent(ChangePassActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
