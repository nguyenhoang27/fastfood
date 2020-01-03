package hhhai0304.dfc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import helper.CovertHelper;
import helper.JSONParser;
import helper.PHPUrl;

public class RegisterActivity extends Activity
{
    EditText edtUsername, edtPassword, edtRePass, edtFirstname, edtLastname, edtAddress, edtDistrict, edtPhone, edtEmail;
    Spinner spnCity;
    Button btnDangky;
    ArrayAdapter<String> adapterCity;
    String[] CityList = {"Hồ Chí Minh", "Hà Nội"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtPassword.setTypeface(Typeface.DEFAULT);
        edtRePass = (EditText)findViewById(R.id.edtRePass);
        edtRePass.setTypeface(Typeface.DEFAULT);
        edtFirstname = (EditText)findViewById(R.id.edtFirstname);
        edtLastname = (EditText)findViewById(R.id.edtLastname);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        edtDistrict = (EditText)findViewById(R.id.edtDistrict);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        spnCity = (Spinner)findViewById(R.id.spnCity);
        btnDangky = (Button)findViewById(R.id.btnDangky);

        adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCity.setAdapter(adapterCity);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("") || edtFirstname.getText().toString().equals("")
                || edtLastname.getText().toString().equals("") || edtAddress.getText().toString().equals("") || edtDistrict.getText().toString().equals("")
                || edtPhone.getText().toString().equals("") || edtEmail.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(edtRePass.getText().toString().equals(edtPassword.getText().toString())) {
                    if(edtPassword.getText().toString().length() < 6)
                    {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải từ 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
                    }
                    else if (isValidEmail(edtEmail.getText().toString()) == false)
                    {
                        Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    else if (edtUsername.getText().toString().length() != edtUsername.getText().toString().replaceAll(" ","").length())
                    {
                        Toast.makeText(RegisterActivity.this, "Tài khoản không được chứa khoảng trắng", Toast.LENGTH_SHORT).show();
                    }
                    else if (edtUsername.getText().toString().length() < 4)
                    {
                        Toast.makeText(RegisterActivity.this, "Tài khoản phải từ 4 ký tự trở lên", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        CovertHelper c = new CovertHelper();
                        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                        nameValuePairs.add(new BasicNameValuePair("UserID", c.randomID(8)));
                        nameValuePairs.add(new BasicNameValuePair("Username", edtUsername.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("Password", edtPassword.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("FirstName", edtFirstname.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("LastName", edtLastname.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("Address", edtAddress.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("District", edtDistrict.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("City", spnCity.getSelectedItem().toString()));
                        nameValuePairs.add(new BasicNameValuePair("Phone", edtPhone.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("Email", edtEmail.getText().toString()));



                        doRegister task = new doRegister(nameValuePairs);
                        task.execute();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class doRegister extends AsyncTask<Void, Void, Void>
    {
        int status;
        List<NameValuePair> nameValuePairs;
        ProgressDialog progress;

        public doRegister(List<NameValuePair> nameValuePairs)
        {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = new ProgressDialog(RegisterActivity.this);
            progress.setMessage("Đang gửi thông tin đăng ký");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            JSONParser task = new JSONParser();
            JSONObject json_object = task.getJsonFromUrl(PHPUrl.register, nameValuePairs);
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
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối, hãy thử lại sau", Toast.LENGTH_SHORT).show();
            else if (status == 1)
                Toast.makeText(RegisterActivity.this, "Tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show();
            else if(status == 2)
            {
                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private boolean isValidEmail(CharSequence target)
    {
        if (target == null) {
            return false;
        }
        else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}