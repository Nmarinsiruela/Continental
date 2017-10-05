package nmarin.eite.com.continentalplus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerNames extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<EditText> arrayNombres;
    private int size = 0;

    // Variable para onBackPressed.
    private boolean ifBack = false;

    // Variables para DrawerLayout.
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private final ArrayList<NavItem> mNavItems = new ArrayList<>();


    //Analytics
    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_names);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]


        Button btn = (Button) findViewById(R.id.next_button2);
        Typeface typefaceAmaranth = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Bold.ttf");
        Typeface typefaceActor = Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf");
        btn.setTypeface(typefaceAmaranth);
        btn.setOnClickListener(this);

        //Importamos número de NumberSelect
        Bundle bundle = getIntent().getExtras();
        String numeroJugadores = bundle.getString("numero");
        size = Integer.parseInt(numeroJugadores);

        arrayNombres = new ArrayList<>();

        // Se genera a continuación la lista de ET y TV para escribir.
        LayoutTable();


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        TextView myTextView = (TextView) findViewById(R.id.toolbar_title);
        myTextView.setTypeface(typefaceAmaranth);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Cambiar fuente texto.
        TextView baraja = (TextView) findViewById(R.id.textView);
        baraja.setTypeface(typefaceActor);

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mDrawerLayout.setStatusBarBackgroundColor(Color.BLACK);
        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mNavItems.add(new NavItem("El Juego", "¿Cómo jugar?", R.drawable.ic_info_black_24px));
        mNavItems.add(new NavItem("Contacto", "¿Quieres contarnos algo?", R.drawable.ic_email_black_24px));
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.fin, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("LoadWindow", "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }        // FIN DE onCREATE

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }


    private void LayoutTable(){
        // CREATE TABLE
        LinearLayout linearTable = (LinearLayout)findViewById(R.id.mainLayout);
        for(int x = 0 ; x < size ; x ++){
            // CREATE TABLE ROW
            LinearLayout linearRow = new LinearLayout(this);
            linearRow.setMeasureWithLargestChildEnabled(true);
            linearRow.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearRow.setPadding(30, 30, 60, 30);
            tParams.bottomMargin = 8;
            tParams.leftMargin = 100;
            tParams.rightMargin = 100;
            linearRow.setLayoutParams(tParams);

            // CREATE TEXTVIEW
            TextView a = new TextView(this);
            EditText b = new EditText(this);

            // EditText options.
            b.setInputType(1); // Forzado a texto.
            b.setGravity(Gravity.CENTER);

            //android:background="@drawable/textfield_multiline_activated_holo_dark"
            // CREATE PARAM FOR MARGINING
            LinearLayout.LayoutParams aParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            bParams.leftMargin = 5;

            // SET TYPEFACE
            a.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf"));
            b.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf"));

            // SET PARAMS
            a.setLayoutParams(aParams);
            b.setLayoutParams(bParams);

            // SET BACKGROUND COLOR
            linearRow.setBackgroundColor(Color.WHITE);


            // SET TEXTVIEW TEXT
            a.setText("Jugador "+  (x+1) + ":");
            //b.setHint("Nombre");
            a.setTextSize(18);
            b.setTextSize(18);
            a.setTextColor(Color.BLACK);

            // ADD TEXTVIEW TO LINEARROW
            linearRow.addView(a);
            linearRow.addView(b);
            // ADD LINEARROW TO LINEARTABLE
            linearTable.addView(linearRow);

            arrayNombres.add(b);
        }
    }

    /*
* Called when a particular item from the navigation drawer
* is selected.
* */
    private void selectItemFromDrawer(int position) {
        switch (position){
            case 0: //Salta el Dialog con el ViewPager

                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("TheGame")
                        .build());
                // [END custom_event]


                mDrawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerPane);

                Intent intentG = new Intent(getApplicationContext(), TheGame.class);
                startActivity(intentG);
                // No se mata la activity para que al volver, estén los datos anteriores.

                break;
            case 1: //Nueva Activity, About.
                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("About")
                        .build());
                // [END custom_event]
                mDrawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerPane);

                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
                // No se mata la activity para que al volver, estén los datos anteriores.
                break;
            default:
                break;

        }
        // Close the drawer

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private boolean probarETs(ArrayList<EditText> et) {
        String test;
        for (int x = 0; x < et.size(); x++) {
            test = et.get(x).getText().toString();
            if (test.equals("")) {
                Toast.makeText(getApplicationContext(), "¡Por favor, rellena todos los nombres!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean suprimirDuplicados(ArrayList<EditText> et) {
        int cuenta;
        ArrayList<String> arrayTest = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            String user = et.get(x).getText().toString();
            arrayTest.add(user);
        }

        for (int z = 0; z < size; z++) {
            cuenta = Collections.frequency(arrayTest, arrayTest.get(z));
            if (cuenta > 1) {
                Toast.makeText(getApplicationContext(), "Nombre duplicado detectado", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> arrayNombresOrdenados = new ArrayList<>();
        if (probarETs(arrayNombres)) {
            for (int x = 0; x < size; x++) {
                String user = arrayNombres.get(x).getText().toString();
                String output = user.substring(0, 1).toUpperCase() + user.substring(1); // Capitaliza primera letra
                arrayNombresOrdenados.add(output);
            }
            Collections.sort(arrayNombresOrdenados);
            ArrayList<String> arrayValoresOrdenados = new ArrayList<>();

            for (int x=0; x< arrayNombresOrdenados.size(); x++){
                arrayValoresOrdenados.add("0");
            }
            if (suprimirDuplicados(arrayNombres)) {

                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Click")
                        .setAction("PlayerNames-to-Rounds")
                        .build());
                // [END custom_event]

                Toast.makeText(getApplicationContext(), "Empieza la partida", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Rounds.class);
                intent.putStringArrayListExtra("arrayNombres", arrayNombresOrdenados);
                intent.putStringArrayListExtra("arrayValores", arrayValoresOrdenados);
                intent.putExtra("ronda", 1);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        mTracker.setScreenName(this.getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END shared_tracker]
    }

    @Override
    public void onBackPressed() {
        if (ifBack) {
            // Otherwise defer to system default behavior.
            super.onBackPressed();
        }
        ifBack = true;
        Toast.makeText(getApplicationContext(), "Vuelve a pulsar el botón para salir.", Toast.LENGTH_SHORT).show();
    }
}
