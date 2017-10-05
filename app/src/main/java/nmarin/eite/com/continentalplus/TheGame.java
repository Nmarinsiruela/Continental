package nmarin.eite.com.continentalplus;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class TheGame extends AppCompatActivity implements View.OnClickListener {

    private NonSwipeableViewPager viewPager;
    private ImageButton leftNav;
    private ImageButton rightNav;
    private final int[] mImages = new int[] {
            R.drawable.info1,
            R.drawable.info2,
            R.drawable.info3,
    };

    //Analytics
    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thegame);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]




        Typeface typefaceAmaranth = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Bold.ttf");
        viewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);

        leftNav = (ImageButton) findViewById(R.id.left_nav);
        rightNav = (ImageButton) findViewById(R.id.right_nav);

        Button btn = (Button) findViewById(R.id.buttonGame);
        btn.setOnClickListener(this);
        btn.setTypeface(typefaceAmaranth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        TextView myTextView = (TextView) findViewById(R.id.toolbar_title);
        myTextView.setTypeface(typefaceAmaranth);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        leftNav.setVisibility(View.GONE);

// Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    rightNav.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(tab);
                    if (tab == 0)
                        leftNav.setVisibility(View.GONE);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                leftNav.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(tab);
                if (tab == mImages.length-1){
                    rightNav.setVisibility(View.GONE);
                }
            }
        });

        final ImagePagerAdapter ipadapter = new ImagePagerAdapter();
        viewPager.setAdapter(ipadapter);
    } // FIN ONCREATE

    @Override
    public void onClick(View v) {
        //       startActivity(intent);

        // [START custom_event]
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Click")
                .setAction("buttonGameClicked")
                .build());
        // [END custom_event]
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
        super.onBackPressed();
        //     startActivity(intent);
        finish();
    }

    private class ImagePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = getApplicationContext();
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_large);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(mImages[position]);
            (container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((ImageView) object);
        }
    }
}