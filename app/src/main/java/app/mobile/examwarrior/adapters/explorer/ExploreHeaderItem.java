package app.mobile.examwarrior.adapters.explorer;

import android.support.annotation.NonNull;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 26/8/17, 2:23 PM.
 */

public class ExploreHeaderItem extends Item<ViewHolder> {
    private String headerTitle;
    private String subTitle;

    public ExploreHeaderItem(String headerTitle, String subTitle) {
        this.headerTitle = headerTitle;
        this.subTitle = subTitle;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getLayout() {
        return R.layout.explore_header_item;
    }
}
