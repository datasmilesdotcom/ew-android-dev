package app.mobile.view_holder;

import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.demo.ToggleListener;
import app.mobile.examwarrior.widget.CustomFontTextView;

/**
 * Created by sandesh on 16/7/17, 10:46 PM.
 */

public class RelatedSuggestionHeader extends Item<ViewHolder> implements ExpandableItem {

    private CustomFontTextView tvTitle;
    private CustomFontTextView tvSubTitle;
    private AppCompatImageView icon;
    private String headerTitle;
    private String subTitle;
    private ExpandableGroup expandableGroup;

    private ToggleListener toggleListener;

    public RelatedSuggestionHeader(String headerTitle, String subTitle) {
        this.headerTitle = headerTitle;
        this.subTitle = subTitle;
    }

    public RelatedSuggestionHeader(long id, String headerTitle, String subTitle) {
        super(id);
        this.headerTitle = headerTitle;
        this.subTitle = subTitle;
    }

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
        icon = (AppCompatImageView) viewHolder.getRoot().findViewById(R.id.icon);
        tvTitle = (CustomFontTextView) viewHolder.getRoot().findViewById(R.id.title);
        tvSubTitle = (CustomFontTextView) viewHolder.getRoot().findViewById(R.id.subtitle);

        icon.setVisibility(View.VISIBLE);
        tvTitle.setText(headerTitle);
        tvSubTitle.setText(subTitle);
        icon.setImageResource(expandableGroup.isExpanded() ? R.drawable.collapse : R.drawable.expand);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableGroup.onToggleExpanded();
                bindIcon();
                if (toggleListener != null)
                    toggleListener.onToggle(viewHolder, position, expandableGroup.isExpanded());
            }
        });
    }

    private void bindIcon() {
        icon.setVisibility(View.VISIBLE);
        icon.setImageResource(expandableGroup.isExpanded() ? R.drawable.collapse_animated : R.drawable.expand_animated);
        Animatable drawable = (Animatable) icon.getDrawable();
        drawable.start();
    }

    @Override
    public int getLayout() {
        return R.layout.expandable_header_item;
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(@NonNull View itemView) {
        return super.createViewHolder(itemView);
    }
}
