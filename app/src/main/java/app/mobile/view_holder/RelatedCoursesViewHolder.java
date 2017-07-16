package app.mobile.view_holder;

import android.support.annotation.NonNull;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 17/7/17, 12:34 AM.
 */

public class RelatedCoursesViewHolder extends Item<ViewHolder> {
    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getLayout() {
        return R.layout.card_video;
    }
}
