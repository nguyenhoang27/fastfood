package hhhai0304.dfc;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import helper.ConnectionCheckHelper;
import helper.DatabaseHelper;
import helper.JSONParser;
import helper.PHPUrl;
import item.CategoryItem;
import item.Products;

public class SplashScreenActivity extends Activity
{
    DatabaseHelper db;
    JSONArray JArr = null;
    OutputStreamWriter outputStreamWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ConnectionCheckHelper connect = new ConnectionCheckHelper(this);
        if (connect.isOnline())
        {
            File direct = new File(PHPUrl.productAvatarSavePath);
            if (!direct.exists())
                direct.mkdirs();

            db = new DatabaseHelper(SplashScreenActivity.this);

            File versionFile = new File(getFilesDir() + "/" + PHPUrl.fileVersion);
            if (!versionFile.isFile()) {
                setVersion("0");
            }

            loadData task = new loadData();
            task.execute();
        }
        else
        {
            connect.createInternetDialog();
        }
    }

    public class loadData extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String getVersion = "", thisVersion = getVersion();
            JSONParser task = new JSONParser();
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            JSONObject json_object_version = task.getJsonFromUrl(PHPUrl.getversion, nameValuePairs);
            try {
                getVersion = json_object_version.getString("Version");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if(getVersion.equals(thisVersion) == false)
            {
                db.xoaAllProducts();
                db.xoaCategory();
                File direct = new File(PHPUrl.productAvatarSavePath);
                for (File child : direct.listFiles())
                    child.delete();

                JSONObject json_object = task.getJsonFromUrl(PHPUrl.getallproducts, nameValuePairs);
                try
                {
                    JArr = new JSONArray();
                    JArr = json_object.getJSONArray("Mon_an");
                    for(int i=0; i < JArr.length(); i++)
                    {
                        JSONObject ck = JArr.getJSONObject(i);
                        Products pd = new Products();
                        pd.ProductID = ck.getString("ProductID");
                        pd.ProductName = ck.getString("ProductName");
                        pd.Detail = ck.getString("Detail");
                        downloadProductAvatar(PHPUrl.productAvatar + ck.getString("Avatar"), ck.getString("Avatar"));
                        pd.Avatar = PHPUrl.productAvataronDisk + ck.getString("Avatar");
                        pd.Price = ck.getInt("Price");
                        pd.CategoryID = ck.getString("CategoryID");
                        db.themProduct(pd);
                    }
                    setVersion(getVersion);
                }
                catch(Exception e)
                {
                    Log.i("Lỗi Kết nối", e.toString());
                }

                List<NameValuePair> nameValuePairsCA = new ArrayList<>(2);
                JSONObject json_ca = task.getJsonFromUrl(PHPUrl.get_category, nameValuePairsCA);
                try
                {
                    JArr = new JSONArray();
                    JArr = json_ca.getJSONArray("json_data");
                    for(int i=0; i < JArr.length(); i++)
                    {
                        JSONObject ca = JArr.getJSONObject(i);

                        CategoryItem caItem = new CategoryItem();
                        caItem.setCategoryID(ca.getString("CategoryID"));
                        caItem.setName(ca.getString("Name"));
                        caItem.setDetail(ca.getString("Detail"));

                        db.themCategory(caItem);
                    }
                }
                catch(Exception e)
                {
                    Log.e("Lỗi Kết nối", e.toString());
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void downloadProductAvatar(String url, String fname)
    {
        File imgProduct = new File(PHPUrl.productAvatarSavePath + fname);
        if (imgProduct.isFile() == false)
        {
            File direct = new File(PHPUrl.productAvatarSavePath);

            if (!direct.exists())
                direct.mkdirs();

            DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("Data downloading...")
                    .setDestinationInExternalPublicDir("/DFCTemp/Images", fname);

            mgr.enqueue(request);
        }
    }

    public void setVersion(String version)
    {
        try
        {
            outputStreamWriter = new OutputStreamWriter(openFileOutput(PHPUrl.fileVersion, MODE_PRIVATE));
            outputStreamWriter.write(version);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Lỗi", "Ghi thất bại: " + e.toString());
        }
    }

    public String getVersion()
    {
        String version = "";
        try
        {
            InputStream inputStream = openFileInput(PHPUrl.fileVersion);

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while( (receiveString = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                version = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("Lỗi SplashScreen", "Không tìm thấy file: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("Lỗi SplashScreen", "Không thể đọc file: " + e.toString());
        }
        return version;
    }
}