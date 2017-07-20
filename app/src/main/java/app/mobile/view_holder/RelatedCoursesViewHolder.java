package app.mobile.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.model.RelatedCourse;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.AspectRatioImageView;

/**
 * Created by sandesh on 17/7/17, 12:34 AM.
 */

public class RelatedCoursesViewHolder extends Item<ViewHolder> {
    private RelatedCourse relatedCourse;

    private AspectRatioImageView video_thumb;
    private AppCompatTextView video_time, video_name, video_views;

    public RelatedCoursesViewHolder(RelatedCourse relatedCourse) {
        this.relatedCourse = relatedCourse;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        video_name = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.video_name);
        video_time = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.video_time);
        video_thumb = (AspectRatioImageView) viewHolder.getRoot().findViewById(R.id.video_thumb);
        video_views = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.video_views);
        String thumb = relatedCourse.getImageUrl();
        video_name.setText(relatedCourse.getCourseName());
        video_time.setText("0.0.0");
        video_views.setText(0 + " Views");
        if (!Utility.isEmpty(thumb))
            Glide.with(viewHolder.getRoot().getContext()).load(thumb).into(video_thumb);
    }

    @Override
    public int getLayout() {
        return R.layout.card_video;
    }
}
