package app.mobile.view_holder;

import android.support.annotation.NonNull;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 16/7/17, 10:08 PM.
 */

public class VideoContentDetailsAdapter extends Item<ViewHolder> {


    public VideoContentDetailsAdapter() {
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getLayout() {
        return R.layout.video_header;
    }
}
