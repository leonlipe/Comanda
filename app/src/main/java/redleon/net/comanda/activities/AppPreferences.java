package redleon.net.comanda.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import redleon.net.comanda.R;

/**
 * Created by leon on 29/07/15.
 */
public class AppPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
