package redleon.net.comanda.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MenuListAdapter;

public class MaterialMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {
    private MenuListAdapter menuListAdapter;

    ListView lista1;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_mesas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lista1 = (ListView) findViewById(R.id.listView);
        title= (TextView) findViewById(R.id.tvTitle);
        String[] array = new String[] { "Carpaccio con arugula", "Proscuito e Mozzarella", "Proscuito e Molone", "Agffettato Misto"};
        String title="ANTIPASTI";
        iniciaLista(array, title);
    }

    public  void iniciaLista(String [] array, String stringTitle){
        title.setText(stringTitle);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, array){
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent){
//                /// Get the Item from ListView
//                View view = super.getView(position, convertView, parent);
//
//                TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//                // Set the text size 25 dip for ListView each item
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
//                tv.setPadding(10,15,10,15);
//
//                // Return the view
//                return view;
//            }
//        };

        lista1.setAdapter(menuListAdapter);
        lista1.setOnItemClickListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.antipasta) {
            String[] array = new String[] { "Carpaccio con arugula", "Proscuito e Mozzarella", "Proscuito e Molone", "Agffettato Misto"};
            String title="ANTIPASTI";
            iniciaLista(array, title);
        } else if (id == R.id.ensalada) {
            String[] array = new String[] { "La nostra primavera", "Caprese traducional", "Arugula y fresa", "Arugula y pera", "Insalate perla"};
            String title="INSALATE";
            iniciaLista(array, title);
        } else if (id == R.id.pasta) {
            String[] array = new String[] { "Penne alla genovese", "Fusilli a la siciliana", "Olive e capperi", "Bucatini scarperiello", "Gnocchi ai ragú"};
            String title="LE PASTE";
            iniciaLista(array, title);
        } else if (id == R.id.segundo) {
            String[] array = new String[] { "Salciccia alla brace", "Fantasia grigliata", "Fritto di calamari", "Melenzane a scarpone", "Parmigiana di melenzane alla napoletana", "Fritto misto di mare"};
            String title="IL SECONDI";
            iniciaLista(array, title);
        } else if (id == R.id.pizza) {
            String[] array = new String[] { "Pizza Siciliana", "Pizza Scoglio", "Vesuvio", "Spek y provola", "Calabrese", "Mexita", "Mexita provola", "Mexita salmon", "Margherita"};
            String title="LE PIZZE";
            iniciaLista(array, title);

        } else if (id == R.id.bebidas) {
            String[] array = new String[] { "Cola cola", "Refrescos", "Agua Mineral /Naturales", "Acqua San Pellegrino (500 mls)", "Limonada / naranjada", "vino del chef copa", "Limonada / naranjada Jarra 1.65 lts"};
            String title="BEBIDAS";
            iniciaLista(array, title);
        }
        else if (id == R.id.sugerencias) {
            String[] array = new String[] { ""};
            String title="SUGERENCIAS";
            iniciaLista(array, title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String msg = "# "+position;
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        final EditText input = new EditText(MaterialMenuActivity.this);
        final TextView notes = new TextView(MaterialMenuActivity.this);
        input.setHint("Notas");
        notes.setText("Notas");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        notes.setLayoutParams(lp2);

        new AlertDialog.Builder(this)
                .setTitle("Agregar el platillo")
                .setMessage("Desea agregar el platillo?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.ic_local_dining_black_24dp)
                .setView(notes)
                .setView(input)
                .show();
    }
}
