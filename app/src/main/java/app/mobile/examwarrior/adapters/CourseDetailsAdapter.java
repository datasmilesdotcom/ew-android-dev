package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.delegates.CourseModuleItemListener;
import app.mobile.examwarrior.expandable_list.ExpandableRecyclerViewAdapter;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.expandable_list.model.ExpandableGroup;

/**
 * Created by sandesh on 5/5/17, 2:24 AM.
 */

public class CourseDetailsAdapter extends ExpandableRecyclerViewAdapter<CourseModuleViewHolder, ModuleItemViewHolder> {


    private Context context;
    private CourseModuleItemListener courseModuleItemListener;

    public CourseDetailsAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    public CourseModuleItemListener getCourseModuleItemListener() {
        return courseModuleItemListener;
    }

    public void setCourseModuleItemListener(CourseModuleItemListener courseModuleItemListener) {
        this.courseModuleItemListener = courseModuleItemListener;
    }

    @Override
    public CourseModuleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_detail_list_row, parent, false);
        return new CourseModuleViewHolder(view);
    }

    @Override
    public ModuleItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_module, parent, false);
        return new ModuleItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final ModuleItemViewHolder holder, int flatPosition, ExpandableGroup group, final int childIndex) {


        final ModuleItem data = ((CourseHeader) group).getItems().get(childIndex);
        holder.setArtistName(data.getItemName());
        holder.getChildTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseModuleItemListener != null) {
                    courseModuleItemListener.onModuleClickListener(holder, childIndex, data);
                }
            }
        });
    }

    @Override
    public void onBindGroupViewHolder(CourseModuleViewHolder holder, int flatPosition, ExpandableGroup group) {
        CourseHeader data = (CourseHeader) group;
        holder.settitle(data.getTitle());
        holder.setImage(data.getImage(), context);
    }
}
