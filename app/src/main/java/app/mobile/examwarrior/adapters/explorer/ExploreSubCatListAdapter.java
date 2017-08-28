package app.mobile.examwarrior.adapters.explorer;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.Tutors;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.AspectRatioImageView;

public class ExploreSubCatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_BOOK = 1;
    public static final int ITEM_TUTOR = 2;
    public static final int ITEM_COURSES = 3;
    List<Object> videoItems;
    private int ITEM_TYPE = 0;
    private Activity activity;
    private ExploreCardClickListener videoCardClickListener;


    public ExploreSubCatListAdapter(Activity activity, List<Object> videoItems, int item_type) {
        this.videoItems = videoItems;
        this.videoCardClickListener = (ExploreCardClickListener) activity;
        this.activity = activity;
        ITEM_TYPE = item_type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_COURSES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_course_sub_detail_card, parent, false);
                viewHolder = new MyHolder(view);
                break;
            case ITEM_BOOK:
                break;
            case ITEM_TUTOR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_course_sub_detail_card, parent, false);
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
                    holder.description.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.");
                    holder.author_name.setText(String.format("Author : %s", coursesBean.getAuthor() != null && !coursesBean.getAuthor().isEmpty() ? coursesBean.getAuthor().get(0).getAuthorName() : ""));
                    holder.last_updated.setText(String.format("Last Uupdate : %s", coursesBean.getUpdatedAt()));
                    holder.level.setText(String.format("Level : %s", coursesBean.getCourseLevel()));

                    if (!Utility.isEmpty(coursesBean.getImageUrl())) {
                        Glide.with(holder.itemView.getContext()).load(coursesBean.getImageUrl()).asBitmap().error(R.drawable.placeholder_).into(holder.coverImage);
                    }
                    //Glide.with(holder.itemView.getContext()).load("http://lorempixel.com/400/200/business/").asBitmap().signature(new StringSignature(Integer.toString(position))).error(R.drawable.placeholder_).into(holder.coverImage);
                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            videoCardClickListener.exploreCourcesMore(coursesBean);
                        }
                    });
                    break;

                case ITEM_TUTOR:
                    MyHolder tutor_holder = (MyHolder) viewHolder;
                    final Tutors tutor = (Tutors) videoItems.get(position);
                    tutor_holder.textTitle.setText(String.format("%s %s %s", tutor.getFst_nm().toUpperCase(), tutor.getLst_nm().toUpperCase(), tutor.getLocation()));
                    tutor_holder.description.setText(String.format("Speaks : %s\nSubject Teaches : %s\nClasses Covers : %s", tutor.getSpeaks(), tutor.getSub_teach(), tutor.getCls_covers()));
                    tutor_holder.author_name.setText(String.format("Education : %s", tutor.getEducation()));
                    tutor_holder.last_updated.setText(String.format("Teaching Experience : %s", tutor.getTeaching_exp()));
                    tutor_holder.level.setText(String.format("Teaches : %s", tutor.getTeaches()));

                    if (!Utility.isEmpty(tutor.getTeacher_pic())) {
                        Glide.with(tutor_holder.itemView.getContext()).load(tutor.getTeacher_pic()).asBitmap().error(R.drawable.placeholder_).into(tutor_holder.coverImage);
                    }
                    //Glide.with(holder.itemView.getContext()).load("http://lorempixel.com/400/200/business/").asBitmap().signature(new StringSignature(Integer.toString(position))).error(R.drawable.placeholder_).into(holder.coverImage);
                    tutor_holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (videoCardClickListener != null) {
                                videoCardClickListener.onTutorItemClicked(position, tutor);
                            }
                        }
                    });
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textTitle, description, author_name, level, last_updated;
        AspectRatioImageView coverImage;
        CardView root;

        public MyHolder(View itemView) {
            super(itemView);

            textTitle = (AppCompatTextView) itemView.findViewById(R.id.title_sub_cat);
            description = (AppCompatTextView) itemView.findViewById(R.id.des_sub_cat);
            author_name = (AppCompatTextView) itemView.findViewById(R.id.author_name);
            level = (AppCompatTextView) itemView.findViewById(R.id.sub_cat_leval);
            last_updated = (AppCompatTextView) itemView.findViewById(R.id.last_updated);
            coverImage = itemView.findViewById(R.id.coverImage);
            root = (CardView) itemView.findViewById(R.id.sub_cat_container);

        }
    }
}

