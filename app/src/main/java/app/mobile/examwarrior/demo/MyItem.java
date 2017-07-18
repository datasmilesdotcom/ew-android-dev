package app.mobile.examwarrior.demo;

import android.support.annotation.NonNull;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 14/7/17, 3:17 PM.
 */

public class MyItem extends Item<ViewHolder> implements ExpandableItem {

    private ExpandableGroup expandableGroup;
    private ToggleListener toggleListener;

    public ToggleListener getToggleListener() {
        return toggleListener;
    }

    public void setToggleListener(ToggleListener toggleListener) {
        this.toggleListener = toggleListener;
    }

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        this.expandableGroup = onToggleListener;
    }


    @Override
    public void bind(@NonNull final ViewHolder viewHolder, final int position) {
       /* ((TextView) viewHolder.getRoot().findViewById(R.id.header_title)).setText("Header Item " + position);
        ((Button) viewHolder.getRoot().findViewById(R.id.toggle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableGroup.onToggleExpanded();
                if (toggleListener != null)
                    toggleListener.onToggle(viewHolder, position, expandableGroup.isExpanded());
            }
        });*/
    }

    @Override
    public int getLayout() {
        return R.layout.expandable_header_item;
    }
}
