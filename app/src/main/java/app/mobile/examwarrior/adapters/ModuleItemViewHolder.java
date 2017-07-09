package app.mobile.examwarrior.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.expandable_list.viewholders.ChildViewHolder;
import app.mobile.examwarrior.expandable_list.viewholders.GroupViewHolder;
import app.mobile.examwarrior.util.Utility;

/**
 * Created by sandesh on 5/5/17, 2:25 AM.
 */

class ModuleItemViewHolder extends ChildViewHolder {
    private AppCompatTextView childTextView;

    public ModuleItemViewHolder(View itemView) {
        super(itemView);
        childTextView = (AppCompatTextView) itemView.findViewById(R.id.list_item_artist_name);

    }

    public void setArtistName(String name) {
        childTextView.setText(name);
    }

    public AppCompatTextView getChildTextView() {
        return childTextView;
    }

    public void setChildTextView(AppCompatTextView childTextView) {
        this.childTextView = childTextView;
    }
}
