package redleon.net.comanda.loaders;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import redleon.net.comanda.adapters.ComandasListAdapter;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.model.JsonDinersResult;

/**
 * Created by leon on 08/07/15.
 */
public class ComandasHistoryLoader{
}