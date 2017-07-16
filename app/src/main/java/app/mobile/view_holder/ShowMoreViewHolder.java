package app.mobile.view_holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.OnItemLongClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 17/7/17, 12:34 AM.
 */

public class ShowMoreViewHolder extends Item<ViewHolder> {

    private int pageGroup = -1;
    private ShowMoreClickListener showMoreClickListener;

    public int getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(int pageGroup) {
        this.pageGroup = pageGroup;
    }

    public ShowMoreClickListener getShowMoreClickListener() {
        return showMoreClickListener;
    }

    public void setShowMoreClickListener(ShowMoreClickListener showMoreClickListener) {
        this.showMoreClickListener = showMoreClickListener;
    }

    @Override
    public void bind(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.getRoot().findViewById(R.id.show_more_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showMoreClickListener != null)
                    showMoreClickListener.onShowMoreClickListener(viewHolder, position, pageGroup);
            }
        });
    }

    @Override
    public void bind(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads, @Nullable OnItemClickListener onItemClickListener, @Nullable OnItemLongClickListener onItemLongClickListener) {
        super.bind(holder, position, payloads, onItemClickListener, onItemLongClickListener);
    }

    @Override
    public int getLayout() {
        return R.layout.show_more;
    }

    public interface ShowMoreClickListener {
        public void onShowMoreClickListener(ViewHolder viewHolder, int position, int page);
    }
}
