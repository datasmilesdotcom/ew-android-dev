package app.mobile.examwarrior.adapters.explorer;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.Tutors;
import app.mobile.examwarrior.util.Utility;

import static app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter.ITEM_BOOK;
import static app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter.ITEM_COURSES;
import static app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter.ITEM_TUTOR;

public class ExploreHorizontalMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> videoItems;
    private Activity activity;
    private int rootPosition = -1;
    private int ITEM_TYPE = 0;
    private ExploreCardClickListener videoCardClickListener;


    public ExploreHorizontalMoreAdapter(Activity activity, List<Object> videoItems, int rootPosition, int itemtype) {
        this.videoItems = videoItems;
        this.videoCardClickListener = (ExploreCardClickListener) activity;
        this.activity = activity;
        this.rootPosition = rootPosition;
        this.ITEM_TYPE = itemtype;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_COURSES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recycle_sub_categories, parent, false);
                viewHolder = new MyHolder(view);
                break;
            case ITEM_BOOK:
                break;
            case ITEM_TUTOR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recycle_sub_categories, parent, false);
                viewHolder = new MyHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (videoItems.get(position) != null) {
            switch (viewHolder.getItemViewType()) {
                case ITEM_COURSES:
                    MyHolder holder = (MyHolder) viewHolder;
                    final CourseMoreCategories.CoursesBean coursesBean = (CourseMoreCategories.CoursesBean) videoItems.get(position);
                    holder.textTitle.setText(coursesBean.getCourseName().toUpperCase());
                    if (coursesBean.getImageUrl() != null && !coursesBean.getImageUrl().equals(""))
                        Glide.with(activity)
                                .load(coursesBean.getImageUrl())
                                .error(R.drawable.placeholder_)
                                .into(holder.coverImage);
                    else
                        holder.coverImage.setImageResource(R.drawable.placeholder_);
                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            videoCardClickListener.exploreCourcesMore(coursesBean);
                        }
                    });
                    break;
                case ITEM_TUTOR:
                    MyHolder tutorHolder = (MyHolder) viewHolder;
                    final Tutors tutors = (Tutors) videoItems.get(position);
                    tutorHolder.textTitle.setText(tutors.getFst_nm().toUpperCase());
                    if (!Utility.isEmpty(tutors.getTeacher_pic()))
                        Glide.with(activity)
                                .load(tutors.getTeacher_pic())
                                .error(R.drawable.placeholder_)
                                .into(tutorHolder.coverImage);
                    else
                        tutorHolder.coverImage.setImageResource(R.drawable.placeholder_);
                    tutorHolder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return videoItems == null ? 0 : videoItems.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView coverImage;
        RelativeLayout root;

        public MyHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.videoTitle);
            coverImage = (ImageView) itemView.findViewById(R.id.topicImage);
            root = (RelativeLayout) itemView.findViewById(R.id.sub_cat_container);

        }
    }
}

