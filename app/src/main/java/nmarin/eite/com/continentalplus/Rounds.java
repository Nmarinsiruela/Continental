package nmarin.eite.com.continentalplus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Collections;

public class Rounds extends AppCompatActivity implements View.OnClickListener {
    // Variable para onBackPressed.
    private boolean ifBack = false;

    // Variables para DrawerLayout.
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private final ArrayList<NavItem> mNavItems = new ArrayList<>();

    private int ronda;
    private int size;
    private ArrayList<EditText> arrayET;
    private final ArrayList<Player> arrayPlayers = new ArrayList<>();


    //Analytics
    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rounds);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]


        Typeface typefaceAmaranth = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Bold.ttf");
        Typeface typefaceActor = Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf");

        Button btn = (Button) findViewById(R.id.next_button3);
        btn.setTypeface(typefaceAmaranth);
        btn.setOnClickListener(this);

        //Importamos texto de PlayerNames / Rounds
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> jugadores , valores;
        jugadores = bundle.getStringArrayList("arrayNombres");
        valores = bundle.getStringArrayList("arrayValores");
        if (jugadores!=null)
        size = jugadores.size();
        ronda = bundle.getInt("ronda");


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        TextView myTextView = (TextView) findViewById(R.id.toolbar_title);
        myTextView.setTypeface(typefaceAmaranth);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TextView rondaTexto = (TextView) findViewById(R.id.ronda);
        TextView objetivoTexto = (TextView) findViewById(R.id.objetivo);
        TextView reparteTexto = (TextView) findViewById(R.id.reparte);

        rondaTexto.setTypeface(typefaceAmaranth);
        objetivoTexto.setTypeface(typefaceActor);
        reparteTexto.setTypeface(typefaceActor);

        MediaPlayer mp;

        rondaTexto.setText("RONDA " +String.valueOf(ronda));
        reparteTexto.setText("Reparte: " +String.valueOf(6+ronda) + " cartas");
        switch (ronda) {
            case 1:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda1);
                mp.start();
                break;
            case 2:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda2);
                mp.start();
                objetivoTexto.setText(R.string.obj1);
                break;
            case 3:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda3);
                mp.start();
                objetivoTexto.setText(R.string.obj2);
                break;
            case 4:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda4);
                mp.start();
                objetivoTexto.setText(R.string.obj3);
                break;
            case 5:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda5);
                mp.start();
                objetivoTexto.setText(R.string.obj4);
                break;
            case 6:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda6);
                mp.start();
                objetivoTexto.setText(R.string.obj5);
                break;
            case 7:
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ronda7);
                mp.start();
                objetivoTexto.setText(R.string.obj6);
                btn.setText(R.string.fin);
                break;
            default:
                break;
        }

        arrayET = new ArrayList<>();

        ScrollView mainLayout = (ScrollView) findViewById(R.id.mainplay);
        TableLayout tableLayoutProgrammatically = this.tableLayout(jugadores, valores);
        tableLayoutProgrammatically.setHorizontalGravity(Gravity.CENTER);
        mainLayout.addView(tableLayoutProgrammatically);

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    private TableLayout tableLayout(ArrayList<String> jugadores, ArrayList<String> valores){

        // CREATE TABLE
        TableLayout tableLayout = new TableLayout(this);

        TableRow tableTitle = TitleTableRow();
        tableTitle.setGravity(Gravity.CENTER);
        tableLayout.addView(tableTitle);


        //TypeFace
        Typeface typefaceActor = Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf");

        for(int x = 0 ; x < size ; x ++){
            // CREATE TABLE ROW
            TableRow tableRow = new TableRow(this);
            tableRow.setMeasureWithLargestChildEnabled(true);
            tableRow.setGravity(Gravity.CENTER);
            // CREATE TEXTVIEW

            TextView namePlayer = new TextView(this);
            EditText roundPoint = new EditText(this);
            TextView totalPoints = new TextView(this);

            //TypeFace
            namePlayer.setTypeface(typefaceActor);
            roundPoint.setTypeface(typefaceActor);
            totalPoints.setTypeface(typefaceActor);

            namePlayer.setText(jugadores.get(x));
            roundPoint.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

            //TEST
            //  roundPoint.setText("0");

            totalPoints.setText(valores.get(x));

            namePlayer.setTextSize(20);
            roundPoint.setTextSize(20);
            totalPoints.setTextSize(20);



            // CREATE PARAM FOR MARGINING
            TableRow.LayoutParams aParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            aParams.topMargin = 8;
            aParams.rightMargin = 8;

            TableRow.LayoutParams bParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            bParams.topMargin = 8;
            bParams.rightMargin = 8;

            TableRow.LayoutParams cParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            cParams.topMargin = 8;
            cParams.rightMargin = 8;

            //  aParams.width = cParams.width;

            namePlayer.setGravity(Gravity.CENTER);
            roundPoint.setGravity(Gravity.CENTER);
            totalPoints.setGravity(Gravity.CENTER);
            // SET PARAMS

            namePlayer.setLayoutParams(aParams);
            roundPoint.setLayoutParams(bParams);
            totalPoints.setLayoutParams(cParams);

            // SET BACKGROUND COLOR
            namePlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.jugadores));
            roundPoint.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            totalPoints.setBackgroundColor(ContextCompat.getColor(this, R.color.total));

            // SET COLOR
            namePlayer.setTextColor(Color.BLACK);
            roundPoint.setTextColor(Color.BLACK);
            totalPoints.setTextColor(Color.BLACK);

            // SET PADDING

            // Padding especifica tamaño!
            //   namePlayer.setPadding(20, 34, 20, 34);
            namePlayer.setPadding(20, 20, 20, 20);
            roundPoint.setPadding(20, 20, 20, 20);
            totalPoints.setPadding(20, 20, 20, 20);

            // ADD TEXTVIEW TO TABLEROW
            tableRow.addView(namePlayer);
            tableRow.addView(roundPoint);
            tableRow.addView(totalPoints);

            // ADD TABLEROW TO TABLELAYOUT
            tableLayout.addView(tableRow);

            arrayET.add(roundPoint);
            Player jugador = new Player(namePlayer.getText().toString(), Integer.parseInt(totalPoints.getText().toString()));
            arrayPlayers.add(jugador);
        }
        return tableLayout;
    }

    private TableRow TitleTableRow() {
        // CREATE TABLE ROW
        TableRow tableRow = new TableRow(this);
        tableRow.setMeasureWithLargestChildEnabled(true);

        //TypeFace
        Typeface typefaceActor = Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf");

        // CREATE TEXTVIEW

        TextView namePlayer = new TextView(this);
        TextView roundPoint = new TextView(this);
        TextView totalPoints = new TextView(this);

        namePlayer.setTypeface(typefaceActor);
        roundPoint.setTypeface(typefaceActor);
        totalPoints.setTypeface(typefaceActor);

        // CREATE PARAM FOR MARGINING
        TableRow.LayoutParams aParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        aParams.topMargin = 8;
        aParams.rightMargin = 8;

        TableRow.LayoutParams bParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        bParams.topMargin = 8;
        bParams.rightMargin = 8;

        TableRow.LayoutParams cParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        cParams.topMargin = 8;
        cParams.rightMargin = 8;

        namePlayer.setGravity(Gravity.CENTER);
        roundPoint.setGravity(Gravity.CENTER);
        totalPoints.setGravity(Gravity.CENTER);

        // SET PARAMS
        namePlayer.setLayoutParams(aParams);
        roundPoint.setLayoutParams(bParams);
        totalPoints.setLayoutParams(cParams);

        // SET BACKGROUND COLOR
        namePlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.title));
        roundPoint.setBackgroundColor(ContextCompat.getColor(this, R.color.title));
        totalPoints.setBackgroundColor(ContextCompat.getColor(this, R.color.title));

        namePlayer.setText(R.string.players);
        roundPoint.setText(R.string.points);
        totalPoints.setText(R.string.total);

        namePlayer.setTextSize(20);
        roundPoint.setTextSize(20);
        totalPoints.setTextSize(20);

        // SET PADDING
        // Padding especifica tamaño!
        namePlayer.setPadding(30, 30, 30, 30);
        roundPoint.setPadding(30, 30, 30, 30);
        totalPoints.setPadding(30, 30, 30, 30);

        // ADD TEXTVIEW TO TABLEROW
        tableRow.addView(namePlayer);
        tableRow.addView(roundPoint);
        tableRow.addView(totalPoints);

        return tableRow;
    }

    //Gestiono los distintos eventos de pulsar en los botones.
    @Override
    public void onClick(View v) {
        if (verifyRoundPoints()) {
            nextRound();
            Collections.sort(arrayPlayers);
            ArrayList<String> arrayValores = new ArrayList<>();
            ArrayList<String> arrayNombres = new ArrayList<>();
            for (int x=0; x<arrayPlayers.size(); x++){
                arrayNombres.add(arrayPlayers.get(x).getNombreJugador());
                arrayValores.add(String.valueOf(arrayPlayers.get(x).getTotalPoints()));
            }

            ronda++;
            Intent intent;
            if (ronda == 8) {
                intent = new Intent(v.getContext(), ScoreFinal.class);
                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Click")
                        .setAction("Rounds-to-ScoreFinal")
                        .build());
                // [END custom_event]
            }else {
                intent = new Intent(v.getContext(), Rounds.class);
            }
            intent.putStringArrayListExtra("arrayValores", arrayValores);
            intent.putStringArrayListExtra("arrayNombres", arrayNombres);
            intent.putExtra("ronda", ronda);
            startActivity(intent);
            finish();
        }
    }

    // Introduzco los valores de los EditText en el Array de Players.
    private void nextRound(){
        String sEditText;
        int valorET,valorActual;
        for (int x = 0; x < arrayPlayers.size(); x++){
            // Extraigo el valor de la ronda actual del EditText y paso a int.
            sEditText = arrayET.get(x).getText().toString();
            valorET = Integer.parseInt(sEditText);
            valorActual = arrayPlayers.get(x).getTotalPoints();
            arrayPlayers.get(x).setTotalPoints(valorET + valorActual);
        }
    }

    private boolean verifyRoundPoints() {
        String test;
        for (int x = 0; x < arrayET.size(); x++) {
            test = arrayET.get(x).getText().toString();
            if (test.equals("")) {
                Toast.makeText(getApplicationContext(), "¡Por favor, rellena todos los valores!", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!test.matches("[0-9]+")) {
                Toast.makeText(getApplicationContext(), "¡Por favor, introduce un valor válido!", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        mTracker.setScreenName(this.getClass().getSimpleName() + ": " + ronda);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END shared_tracker]
    }

    @Override
    public void onBackPressed() {
        if (ifBack) {
            Intent intent = new Intent(getApplicationContext(), LoadWindow.class);
            //
            startActivity(intent);
            finish();
        }
        ifBack = true;
        Toast.makeText(getApplicationContext(), "Vuelve a pulsar el botón para volver al menú principal.", Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}