package app.mobile.examwarrior.util.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sandesh on 26/8/17, 6:50 PM.
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public RecyclerViewDecoration(int spacing) {
        this.spacing = spacing;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = spacing;
        outRect.left = spacing;
        outRect.right = spacing;
        if (parent.getChildAdapterPosition(view) == 0) outRect.top = spacing;
    }


}
