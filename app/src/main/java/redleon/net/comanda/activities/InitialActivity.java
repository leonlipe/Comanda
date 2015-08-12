package redleon.net.comanda.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import redleon.net.comanda.R;
import redleon.net.comanda.utils.Encoder;

public class InitialActivity extends ActionBarActivity {
    public static final String PLACE_COCINAS = "COC";
    public static final String PLACE_PIZZERIA = "PIZ";
    public static final String PLACE_MESAS = "MES";
    public static final String PLACE_BARRA = "BAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        goToActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AppPreferences.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        goToActivity();
    }

    public void goToActivity(){
        System.out.println(Encoder.encode("2015-08-12T13:22:00","XXXXXXXXXXXXXXXXXX"));
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String user_type = sp.getString("user_type", "NA");
        if (user_type.equals(PLACE_COCINAS)){
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.PLACE_KEY, "COC");
            startActivity(intent);
        }else if (user_type.equals(PLACE_PIZZERIA)){
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.PLACE_KEY, "PIZ");
            startActivity(intent);
        }else if (user_type.equals(PLACE_MESAS)){
            Intent intent = new Intent(this, TablesActivity.class);
            startActivity(intent);
        }else if (user_type.equals(PLACE_BARRA)){
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.PLACE_KEY, "BAA");
            startActivity(intent);
        }
    }
}
