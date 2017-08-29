package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import app.mobile.examwarrior.R;

/**
 * Created by Kunjan on 26-08-2017.
 */

public class ImageAdapter extends BaseAdapter {
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous, R.drawable.icon_next,
            R.drawable.icon_previous
    };
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }

}
