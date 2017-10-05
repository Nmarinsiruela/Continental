package nmarin.eite.com.continentalplus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

public class ScoreFinal extends AppCompatActivity implements View.OnClickListener {
    // Variable para onBackPressed.
    private boolean ifBack = false;

    // Variables para DrawerLayout.
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private final ArrayList<NavItem> mNavItems = new ArrayList<>();


    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_final);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        Button btn = (Button) findViewById(R.id.next_button4);
        Typeface typefaceAmaranth = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Bold.ttf");
        btn.setTypeface(typefaceAmaranth);
        btn.setOnClickListener(this);
        //Importamos texto de Act.2
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> jugadores = bundle.getStringArrayList("arrayNombres");
        ArrayList<String> valores  = bundle.getStringArrayList("arrayValores");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        TextView myTextView = (TextView) findViewById(R.id.toolbar_title);
        myTextView.setTypeface(typefaceAmaranth);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ScrollView mainLayout = (ScrollView) findViewById(R.id.mainScore);
        TableLayout tableLayoutProgrammatically = this.tableLayout(jugadores, valores);
        tableLayoutProgrammatically.setHorizontalGravity(Gravity.CENTER);
        mainLayout.addView(tableLayoutProgrammatically);



        TextView rondaFin = (TextView) findViewById(R.id.fin);
        rondaFin.setTypeface(typefaceAmaranth);

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
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }        // FIN DE onCREATE

    /* The click listener for ListView in the navigation drawer */
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
        for(int x = 0 ; x < jugadores.size() ; x ++){
            // CREATE TABLE ROW
            TableRow tableRow = new TableRow(this);
          // tableRow.setMeasureWithLargestChildEnabled(true);
            tableRow.setGravity(Gravity.CENTER);
            // CREATE TEXTVIEW

            TextView namePlayer = new TextView(getApplicationContext());
            TextView totalPoints = new TextView(getApplicationContext());

            //TypeFace
            namePlayer.setTypeface(typefaceActor);
            totalPoints.setTypeface(typefaceActor);
            // CREATE PARAM FOR MARGINING
            TableRow.LayoutParams aParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            aParams.bottomMargin = 8;
            aParams.rightMargin = 8;

            TableRow.LayoutParams cParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            cParams.bottomMargin = 8;
            cParams.rightMargin = 8;

            namePlayer.setGravity(Gravity.CENTER);
            totalPoints.setGravity(Gravity.CENTER);

            // SET PARAMS
            namePlayer.setLayoutParams(aParams);
            totalPoints.setLayoutParams(cParams);

            ImageView view = new ImageView(this);
            TableRow.LayoutParams iParams;
            iParams = new TableRow.LayoutParams(aParams.getLayoutDirection());
            iParams.bottomMargin = 8;
            iParams.rightMargin = 0;
            view.setLayoutParams(iParams);
            int color;
            switch(x){
                case 0:
                    view.setImageResource(R.drawable.ic_looks_one_black_24dp);
                    color = Color.parseColor("#c2be64"); //The color u want
                    break;
                case 1:
                    view.setImageResource(R.drawable.ic_looks_two_black_24dp);
                    color = Color.parseColor("#b9b4b4"); //The color u want
                    break;
                case 2:
                    view.setImageResource(R.drawable.ic_looks_3_black_24dp);
                    color = Color.parseColor("#da9982"); //The color u want
                    break;
                default:
                    view.setImageResource(R.drawable.ic_looks_3_black_24dp);
                    color = Color.parseColor("#ffffff"); //The color u want
                    break;
            }

            view.setColorFilter(color);
            tableRow.addView(view);

            // SET BACKGROUND COLOR
            namePlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            totalPoints.setBackgroundColor(ContextCompat.getColor(this, R.color.finalpoints));
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            // SET COLOR
            namePlayer.setTextColor(Color.BLACK);
            totalPoints.setTextColor(Color.BLACK);

            namePlayer.setText(jugadores.get(x));
            totalPoints.setText(valores.get(x));

            namePlayer.setTextSize(20);
            totalPoints.setTextSize(20);

            // SET PADDING
            // Padding especifica tamaño!
            namePlayer.setPadding(0, 30, 80, 30);
            totalPoints.setPadding(80, 30, 80, 30);
            view.setPadding(10, 12, 0, 12);

            // ADD TEXTVIEW TO TABLEROW
            tableRow.addView(namePlayer);
            tableRow.addView(totalPoints);

            // ADD TABLEROW TO TABLELAYOUT
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    private TableRow TitleTableRow() {
        // CREATE TABLE ROW
        TableRow tableRow = new TableRow(this);
        // CREATE TEXTVIEW
        TextView namePlayer = new TextView(this);
        TextView totalPoints = new TextView(this);
        TextView extraSpace = new TextView(this);

        //TypeFace
        Typeface typefaceActor = Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf");

        namePlayer.setTypeface(typefaceActor);
        extraSpace.setTypeface(typefaceActor);
        totalPoints.setTypeface(typefaceActor);

        // CREATE PARAM FOR MARGINING
        TableRow.LayoutParams aParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        aParams.rightMargin = 8;
        aParams.bottomMargin = 8;

        TableRow.LayoutParams cParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        cParams.rightMargin = 8;
        cParams.bottomMargin = 8;

        TableRow.LayoutParams sParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        sParams.rightMargin = 0;
        sParams.bottomMargin = 8;

        // SET PARAMS
        namePlayer.setLayoutParams(aParams);
        totalPoints.setLayoutParams(cParams);
        extraSpace.setLayoutParams(sParams);

        // SET BACKGROUND COLOR
        namePlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.title));
        totalPoints.setBackgroundColor(ContextCompat.getColor(this, R.color.title));
        extraSpace.setBackgroundColor(ContextCompat.getColor(this, R.color.title));

        namePlayer.setText(R.string.players);
        namePlayer.setGravity(Gravity.START);
        totalPoints.setText(R.string.total);

        namePlayer.setTextSize(20);
        extraSpace.setTextSize(20);
        totalPoints.setTextSize(20);

        // SET PADDING
        // Padding especifica tamaño!
        namePlayer.setPadding(0, 30, 80, 30);
        totalPoints.setPadding(80, 30, 80, 30);
        extraSpace.setPadding(80, 30, 80, 30);
        // left, top, right, bot

        // ADD TEXTVIEW TO TABLEROW
        tableRow.addView(extraSpace);
        tableRow.addView(namePlayer);
        tableRow.addView(totalPoints);

        return tableRow;
    }

    @Override
    public void onClick(View v) {
        // [START custom_event]
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Click")
                .setAction("ScoreFinal-to-LoadWindow")
                .build());
        // [END custom_event]
        Intent intent = new Intent(v.getContext(), NumberSelect.class);
        //
        startActivity(intent);
        finish();
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
            super.onBackPressed();
        }
        ifBack = true;
        Toast.makeText(getApplicationContext(), "Vuelve a pulsar el botón para salir.", Toast.LENGTH_SHORT).show();
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
}