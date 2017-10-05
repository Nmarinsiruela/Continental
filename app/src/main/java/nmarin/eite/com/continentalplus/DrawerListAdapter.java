package nmarin.eite.com.continentalplus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class DrawerListAdapter extends BaseAdapter {

    private final Context mContext;
       private final ArrayList<NavItem> mNavItems;

     DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( mNavItems.get(position).mTitle );
        subtitleView.setText( mNavItems.get(position).mSubtitle );
        iconView.setImageResource(mNavItems.get(position).mIcon);

        Typeface typefacebtn = Typeface.createFromAsset(mContext.getAssets(), "fonts/Actor-Regular.ttf");
        titleView.setTypeface(typefacebtn);
        subtitleView.setTypeface(typefacebtn);
        titleView.setTextSize(20);
        subtitleView.setTextSize(14);
        titleView.setTextColor(Color.BLACK);
       // subtitleView.setTextColor(mContext.getResources().getColor(R.color.font2));
        subtitleView.setTextColor(Color.GRAY);

        int color = Color.parseColor("#444444"); //The color u want
        iconView.setColorFilter(color);
        return view;
    }
}