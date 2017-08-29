package app.mobile.examwarrior.adapters.explorer;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.AspectRatioImageView;

public class ExploreHorizontalAdapter extends RecyclerView.Adapter<ExploreHorizontalAdapter.MyHolder> {

    List<CourseCategories.McoursesBean> videoItems;
    int rootPosition = -1;
    private Activity activity;
    private ExploreCardClickListener videoCardClickListener;

    public ExploreHorizontalAdapter(int rootPosition, Activity activity, List<CourseCategories.McoursesBean> videoItems) {
        this.videoItems = videoItems;
        this.videoCardClickListener = (ExploreCardClickListener) activity;
        this.activity = activity;
        this.rootPosition = rootPosition;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recycle_sub_categories, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.textTitle.setText(videoItems.get(position).getMcoursename().toUpperCase());
        if (!Utility.isEmpty(videoItems.get(position).getMcourse_pic())) {
            Glide.with(holder.itemView.getContext()).load(videoItems.get(position).getMcourse_pic()).asBitmap().error(R.drawable.placeholder_).into(holder.coverImage);
        }
        //Glide.with(holder.itemView.getContext()).load("http://lorempixel.com/400/200/business/").asBitmap().signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).error(R.drawable.placeholder_).into(holder.coverImage);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoCardClickListener.exploreCources(rootPosition, videoItems.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        AspectRatioImageView coverImage;
        RelativeLayout root;

        public MyHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.videoTitle);
            coverImage = itemView.findViewById(R.id.topicImage);
            root = (RelativeLayout) itemView.findViewById(R.id.sub_cat_container);

        }
    }
}

