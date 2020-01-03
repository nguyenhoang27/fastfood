package helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser
{
    static JSONObject jobj = null;
    static String json = "";
    static InputStream is = null;
    String useragent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.13 Safari/537.36";

    public JSONParser() {}

    @SuppressWarnings("deprecation")
    public JSONObject getJsonFromUrl(String url, List<NameValuePair> params)
    {
        try
        {
            // Khởi tạo
            DefaultHttpClient client = new DefaultHttpClient();
            //client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));

            HttpPost httppost = new HttpPost(url);
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, useragent);
            httppost.setHeader("User-Agent", useragent);

            //Gửi thông tin lệnh lên Server
            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));


            // Thực thi, lấy nội dung về
            HttpResponse http_reaponse = client.execute(httppost);
            HttpEntity http_entity = http_reaponse.getEntity();

            is = http_entity.getContent();

            // Đọc dữ liệu
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),8 );
            StringBuilder sb = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null)
            {
                sb.append(line).append("\n");
            }
            is.close();
            json = sb.toString(); // Đọc StringBuilder vào chuỗi
            Log.i("Chuỗi trả về", json);
            Pattern p = Pattern.compile("^.*?\\((.*?)\\);$", Pattern.DOTALL);
            Matcher m = p.matcher(json);
            if (m.matches())
            {
                json = m.group(1);
                @SuppressWarnings("unused")
                JSONObject jo = new JSONObject(json);
                jobj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1)); // Đưa chuỗi vào đối tượng JSon
            }
            else
            {
                jobj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1)); // Đưa chuỗi vào đối tượng JSon
            }
        }
        catch(Exception e)
        {
            Log.i("Lỗi Json", e.toString());
        }
        return jobj;
    }
}