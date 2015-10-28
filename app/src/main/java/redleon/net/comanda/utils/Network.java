package redleon.net.comanda.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.loopj.android.http.RequestParams;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leon on 12/08/15.
 */
public class Network {

    public static String addAuthParams(String url, Context context){
        if(!url.endsWith("?"))
            url += "?";
        List<NameValuePair> params = new LinkedList<NameValuePair>();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String token = sp.getString("access_token", "NA");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();

        params.add(new BasicNameValuePair("username", sp.getString("user_login", "NA")));
        params.add(new BasicNameValuePair("reqdate", dateFormat.format(date)));
        params.add(new BasicNameValuePair("signature", Encoder.encode(dateFormat.format(date), token)));
        params.add(new BasicNameValuePair("magicno", sp.getString("magicno","0")));

        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += paramString;
        return url;
    }

    public static RequestParams makeAuthParams( Context context){
        RequestParams params = new RequestParams();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String token = sp.getString("access_token", "NA");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();

        params.put("username", sp.getString("user_login", "NA"));
        params.add("reqdate", dateFormat.format(date));
        params.add("signature", Encoder.encode(dateFormat.format(date), token));
        params.add("magicno", sp.getString("magicno","0"));

        return params;
    }
}
