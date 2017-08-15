package app.mobile.examwarrior.adapters.explorer;

import android.app.Activity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseMoreCategories;

public class ExploreSubCatListAdapter extends RecyclerView.Adapter<ExploreSubCatListAdapter.MyHolder> {

    List<CourseMoreCategories.CoursesBean> videoItems;
    private Activity activity;
    private ExploreCardClickListener videoCardClickListener;


    public ExploreSubCatListAdapter(Activity activity, List<CourseMoreCategories.CoursesBean> videoItems) {
        this.videoItems = videoItems;
        this.videoCardClickListener = (ExploreCardClickListener) activity;
        this.activity = activity;

    }

    @Override
    public ExploreSubCatListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_course_sub_detail_card, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.textTitle.setText(videoItems.get(position).getCourseName().toUpperCase());
        holder.description.setText(videoItems.get(position).getCourseLongDesc());
        holder.author_name.setText("Author : "+videoItems.get(position).getAuthor().get(0).getAuthorName());
        holder.last_updated.setText("Last Uupdate : "+videoItems.get(position).getUpdatedAt());
        holder.level.setText("Leval : "+videoItems.get(position).getCourseLevel());
        Picasso.with(activity)
                .load(videoItems.get(position).getImageUrl())
                .error(R.drawable.placeholder_)
                .into(holder.coverImage);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoCardClickListener.exploreCourcesMore(videoItems.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textTitle,description,author_name,level,last_updated;
        ImageView coverImage;
        CardView root;

        public MyHolder(View itemView) {
            super(itemView);

            textTitle = (AppCompatTextView) itemView.findViewById(R.id.title_sub_cat);
            description = (AppCompatTextView) itemView.findViewById(R.id.des_sub_cat);
            author_name = (AppCompatTextView) itemView.findViewById(R.id.author_name);
            level = (AppCompatTextView) itemView.findViewById(R.id.sub_cat_leval);
            last_updated = (AppCompatTextView) itemView.findViewById(R.id.last_updated);
            coverImage = (ImageView) itemView.findViewById(R.id.coverImage);
            root = (CardView) itemView.findViewById(R.id.sub_cat_container);

        }
    }
}

