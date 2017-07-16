package app.mobile.examwarrior.demo;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 14/7/17, 3:22 PM.
 */

public class MyContent extends Item<ViewHolder> {


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.getRoot().findViewById(R.id.channelname)).setText("Item " + position);
    }

    @Override
    public int getLayout() {
        return R.layout.card_video;
    }
}
