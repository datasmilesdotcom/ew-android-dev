package app.mobile.examwarrior.util.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sandesh on 16/7/17, 10:26 PM.
 */

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;
    private int layout;

    public ItemOffsetDecoration(int offset, int layout) {
        this.offset = offset;
        this.layout = layout;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        if (!isHeader(view, parent)) return;
        // Add padding only to the zeroth item
        if (parent.getChildAdapterPosition(view) == 0) {

            outRect.right = offset;
            outRect.left = offset;
            outRect.top = offset;
            outRect.bottom = offset;
        }
    }

    public boolean isHeader(View child, RecyclerView parent) {
        int viewType = parent.getLayoutManager().getItemViewType(child);
        return viewType == layout;
    }
}
