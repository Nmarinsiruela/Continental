package nmarin.eite.com.continentalplus;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;

public class LoadWindow extends AppCompatActivity implements View.OnClickListener{
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.load_window);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]



        // Set main button
        Button btn = (Button) findViewById(R.id.start_button);
        Typeface typefacebtn = Typeface.createFromAsset(getAssets(), "fonts/Actor-Regular.ttf");
        btn.setTypeface(typefacebtn);
        btn.setOnClickListener(this);

        // Barra de Progreso.
        startProgress();

        // Set Toolbar and title
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        TextView myTextView = (TextView) findViewById(R.id.toolbar_title);
        Typeface typefacetitle = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Bold.ttf");
        myTextView.setTypeface(typefacetitle);
        // Erasing standard title
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        mNavItems.add(new NavItem("El Juego", "¿Cómo jugar?", R.drawable.ic_info_black_24px));
        mNavItems.add(new NavItem("Contacto", "¿Quieres contarnos algo?", R.drawable.ic_email_black_24px));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mDrawerLayout.setStatusBarBackgroundColor(Color.BLACK);
        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
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

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);

        }
    }

    @Override
    public void onClick(View v) {

        // [START custom_event]
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Click")
                .setAction("LoadWindow-to-NumberSelect")
                .build());
        // [END custom_event]

        Intent intent = new Intent(v.getContext(), NumberSelect.class);
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
            // Otherwise defer to system default behavior.
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



    // Barra de progreso

    private int mProgressStatus;
    private final Handler mHandler = new Handler();

    private void startProgress() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.determinateBar);
        mProgressStatus = 0;
        progressBar.setProgress(mProgressStatus);
        final Button btn = (Button) findViewById(R.id.start_button);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                while (mProgressStatus < 100) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgressStatus += 20;
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(final Void result){
                // Update your views here
                progressBar.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        }.execute();

    }

    private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

        boolean foundTag = false;
        try {
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();
                    String nameAttr = parser.getAttributeValue(null, "name");

                    foundTag = "string".equals(tagName) && "ga_trackingId".equals(nameAttr);
                }

                if (parser.getEventType() == XmlResourceParser.TEXT) {
                    if (foundTag && parser.getText().contains("REPLACE_ME")) {
                        return false;
                    }
                }

                parser.next();
            }
        } catch (Exception e) {
            Log.w("LoadWindow", "checkConfiguration", e);
            return false;
        }

        return true;
    }
}