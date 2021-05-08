package net.risingcode.coop_picker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import static net.risingcode.coop_picker.ScrollingActivity.IMAGE_IDS;
import static net.risingcode.coop_picker.ScrollingActivity.TAG;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private int logos[];
    private LayoutInflater inflater;
    protected CustomAdapter(Context applicationContext, int[] logos) {
        this.context = applicationContext;
        this.logos = logos;
        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Log.d(TAG, String.format(Locale.getDefault(),
        //        "i: %d; logos[i]: %s; IMAGE_IDS[i]: %s", i, logos[i], IMAGE_IDS[i]));
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.image_grid, null);
            viewHolder = new ViewHolder();
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linlayout);
            if (IMAGE_IDS[i] == R.id.chosen) {


                TextView chose = new TextView(this.context);
                chose.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                chose.setId(IMAGE_IDS[i]);
                viewHolder.mTextView=(TextView)chose;
                linearLayout.addView(chose);
                ImageView icon = (ImageView) view.findViewById(R.id.icon);
                viewHolder.mImageView=(ImageView)icon;
                linearLayout.removeView(icon);
            } else {
                ImageView icon = (ImageView) view.findViewById(R.id.icon);
                icon.setImageResource(logos[i]);
                icon.setId(IMAGE_IDS[i]);
                viewHolder.mImageView=(ImageView)icon;
            }
            view.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)view.getTag();
        }
        return view;
    }
}

class ViewHolder{
    ImageView mImageView;
    TextView mTextView;
}