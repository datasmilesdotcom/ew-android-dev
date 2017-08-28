package app.mobile.examwarrior.adapters.explorer;

import android.support.annotation.NonNull;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 26/8/17, 2:46 PM.
 */

public class ExploreCarouselCardItem extends Item<ViewHolder> {

    private int colorRes;

    public ExploreCarouselCardItem(int colorRes) {
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getLayout() {
        return R.layout.item_square_card;
    }
}
