package redleon.net.comanda.network;

/**
 * Created by leon on 14/05/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.*;

import java.io.InputStream;

public class HttpClient {
    //private static final String BASE_URL = "http://172.31.1.19:3000/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String ip_server = sp.getString("ip_server", "NA");
        Log.v("HTTPCLIENT_GET", "http://" + ip_server+url);
        try {
            client.get(getAbsoluteUrl(ip_server, url), params, responseHandler);
        }catch(Exception ex){
            Log.v("Error","Error");
        }
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String ip_server = sp.getString("ip_server", "NA");
        Log.v("HTTPCLIENT_POST", "http://" + ip_server+url);
        try {
        client.post(getAbsoluteUrl(ip_server,url), params, responseHandler);
        }catch(Exception ex){
            Log.v("Error","Error");
        }
    }

    private static String getAbsoluteUrl(String ip_server,String relativeUrl) {

        return "http://"+ip_server + relativeUrl;
    }
}
