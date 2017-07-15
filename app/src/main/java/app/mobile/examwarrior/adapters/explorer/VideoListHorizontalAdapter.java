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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class VideoListHorizontalAdapter extends RecyclerView.Adapter<VideoListHorizontalAdapter.MyHolder> {

    List<CourseCategories.McoursesBean> videoItems;
    private Activity activity;
    private ExploreCardClickListener videoCardClickListener;


    public VideoListHorizontalAdapter(Activity activity, List<CourseCategories.McoursesBean> videoItems) {
        this.videoItems = videoItems;
        this.videoCardClickListener = (ExploreCardClickListener) activity;
        this.activity = activity;

    }

    @Override
    public VideoListHorizontalAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recycle_sub_categories, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.textTitle.setText(videoItems.get(position).getMcoursename().toUpperCase());
        Picasso.with(activity)
                .load(videoItems.get(position).getPic())
                .error(R.drawable.placeholder_)
                .into(holder.coverImage);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoCardClickListener.exploreCources(videoItems.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoItems.size();
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

