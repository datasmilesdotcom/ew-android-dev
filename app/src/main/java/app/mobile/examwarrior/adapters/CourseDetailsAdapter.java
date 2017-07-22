package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.ModuleDetail;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.expandable_list.ExpandableRecyclerViewAdapter;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.expandable_list.model.ExpandableGroup;
import app.mobile.examwarrior.player.PlayerActivity;
import app.mobile.examwarrior.ui.activity.PracticeActivity;
import app.mobile.examwarrior.util.Utility;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class CourseDetailsAdapter extends ExpandableRecyclerViewAdapter<CourseModuleViewHolder, ModuleItemViewHolder> {

    private Context context;

    public CourseDetailsAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public CourseModuleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_detail_list_row, parent, false);
        return new CourseModuleViewHolder(view, context);
    }

    @Override
    public ModuleItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_module, parent, false);
        return new ModuleItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ModuleItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ModuleItem data = ((CourseHeader) group).getItems().get(childIndex);
        holder.setArtistName(data.getItemName());
        holder.getChildTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PracticeActivity.class);
                intent.putExtra(PlayerActivity.KEY_MODULE_ITEM_ID, data.getItemId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindGroupViewHolder(CourseModuleViewHolder holder,final int flatPosition, ExpandableGroup group) {
        CourseHeader data = (CourseHeader) group;
        holder.settitle(data.getTitle());
        holder.setImage(data.getImage(), context,flatPosition+1);
        holder.directionArrow.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         toggleGroup(flatPosition);
                                                     }
                                                 }
        );
    }
}
