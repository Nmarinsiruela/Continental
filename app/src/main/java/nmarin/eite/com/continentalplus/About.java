package nmarin.eite.com.continentalplus;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class About extends AppCompatActivity implements View.OnClickListener {

    //Analytics
    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]



        Typeface typefaceAmaranth = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Bold.ttf");
        Button btn = (Button) findViewById(R.id.buttonAbout);
        btn.setOnClickListener(this);
        btn.setTypeface(typefaceAmaranth);

        Button btnEmail = (Button) findViewById(R.id.buttonEmail);
        btnEmail.setOnClickListener(this);
        btnEmail.setTypeface(typefaceAmaranth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        TextView myTextView = (TextView) findViewById(R.id.toolbar_title);

        myTextView.setTypeface(typefaceAmaranth);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onClick(View v) {

        int btn = v.getId();

        switch (btn) {
            case R.id.buttonAbout:
                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Click")
                        .setAction("buttonAboutClicked")
                        .build());
                // [END custom_event]


                finish();
                break;
            case R.id.buttonEmail:

                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Click")
                        .setAction("buttonEmailClicked")
                        .build());
                // [END custom_event]

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                break;
            default:
                break;
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
        super.onBackPressed();
   //     startActivity(intent);
        finish();
    }
}
