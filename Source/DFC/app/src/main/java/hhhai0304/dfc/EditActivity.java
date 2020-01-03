package hhhai0304.dfc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import helper.DatabaseHelper;
import helper.JSONParser;
import helper.PHPUrl;
import item.Cookie;

public class EditActivity extends Activity
{
    EditText edtEditFirstname, edtEditLastname, edtEditAddress, edtEditDistrict, edtEditPhone, edtEditEmail;
    Spinner spnCity;
    Button btnEdit;
    ArrayAdapter<String> adapterCity;
    String[] CityList = {"Hồ Chí Minh", "Hà Nội"};
    Cookie c;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = new DatabaseHelper(this);
        c = db.layCookie();

        edtEditFirstname = (EditText)findViewById(R.id.edtEditFirstname);
        edtEditFirstname.setText(c.FirstName);
        edtEditLastname = (EditText)findViewById(R.id.edtEditLastname);
        edtEditLastname.setText(c.LastName);
        edtEditAddress = (EditText)findViewById(R.id.edtEditAddress);
        edtEditAddress.setText(c.Address);
        edtEditDistrict = (EditText)findViewById(R.id.edtEditDistrict);
        edtEditDistrict.setText(c.District);
        edtEditPhone = (EditText)findViewById(R.id.edtEditPhone);
        edtEditPhone.setText(c.Phone);
        edtEditEmail = (EditText)findViewById(R.id.edtEditEmail);
        edtEditEmail.setText(c.Email);

        spnCity = (Spinner)findViewById(R.id.spnEditCity);
        btnEdit = (Button)findViewById(R.id.btnEdit);

        adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCity.setAdapter(adapterCity);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(edtEditFirstname.getText().toString().equals("") && edtEditLastname.getText().toString().equals("") && edtEditAddress.getText().toString().equals("")
                        && edtEditDistrict.getText().toString().equals("") && edtEditPhone.getText().toString().equals("") && edtEditEmail.getText().toString().equals(""))
                {
                    Toast.makeText(EditActivity.this, "Không được để trống tất cả các mục", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String id = c.UserID, fname, lname, address, district, phone, email, city = spnCity.getSelectedItem().toString();

                    if (edtEditFirstname.getText().toString().equals(""))
                        fname = c.FirstName;
                    else
                        fname = edtEditFirstname.getText().toString();

                    if (edtEditLastname.getText().toString().equals(""))
                        lname = c.LastName;
                    else
                        lname = edtEditLastname.getText().toString();

                    if (edtEditAddress.getText().toString().equals(""))
                        address = c.Address;
                    else
                        address = edtEditAddress.getText().toString();

                    if (edtEditDistrict.getText().toString().equals(""))
                        district = c.District;
                    else
                        district = edtEditDistrict.getText().toString();

                    if (edtEditPhone.getText().toString().equals(""))
                        phone = c.Phone;
                    else
                        phone = edtEditPhone.getText().toString();

                    if (edtEditEmail.getText().toString().equals(""))
                        email = c.Email;
                    else
                        email = edtEditEmail.getText().toString();

                    if (isValidEmail(email))
                    {
                        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                        nameValuePairs.add(new BasicNameValuePair("UserID", id));
                        nameValuePairs.add(new BasicNameValuePair("FirstName", fname));
                        nameValuePairs.add(new BasicNameValuePair("LastName", lname));
                        nameValuePairs.add(new BasicNameValuePair("Address", address));
                        nameValuePairs.add(new BasicNameValuePair("District", district));
                        nameValuePairs.add(new BasicNameValuePair("City", city));
                        nameValuePairs.add(new BasicNameValuePair("Phone", phone));
                        nameValuePairs.add(new BasicNameValuePair("Email", email));

                        doEdit task = new doEdit(nameValuePairs);
                        task.execute();
                    }
                    else
                    {
                        Toast.makeText(EditActivity.this, "Email sai định dạng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public class doEdit extends AsyncTask<Void, Void, Void>
    {
        int status;
        List<NameValuePair> nameValuePairs;
        ProgressDialog progress;

        public doEdit(List<NameValuePair> nameValuePairs)
        {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = new ProgressDialog(EditActivity.this);
            progress.setMessage("Đang cập nhật thông tin");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            JSONParser task = new JSONParser();
            JSONObject json_object = task.getJsonFromUrl(PHPUrl.update_account, nameValuePairs);
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
                Toast.makeText(EditActivity.this, "Lỗi kết nối, hãy thử lại sau", Toast.LENGTH_SHORT).show();
            else if(status == 2)
            {
                Toast.makeText(EditActivity.this, "Chỉnh sửa thông tin thành công. Hãy đăng nhập lại.", Toast.LENGTH_LONG).show();
                db.xoaCookie();
                Intent i = new Intent(EditActivity.this, LoginActivity.class);
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
