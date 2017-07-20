package app.mobile.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.model.RelatedVideo;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.AspectRatioImageView;

/**
 * Created by sandesh on 17/7/17, 12:34 AM.
 */

public class RelatedVideosViewHolder extends Item<ViewHolder> {
    private RelatedVideo relatedVideo;
    private AspectRatioImageView video_thumb;
    private AppCompatTextView video_time, video_name, video_views;

    public RelatedVideosViewHolder(RelatedVideo relatedVideo) {
        this.relatedVideo = relatedVideo;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        position = position - 1;
        video_name = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.video_name);
        video_time = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.video_time);
        video_thumb = (AspectRatioImageView) viewHolder.getRoot().findViewById(R.id.video_thumb);
        video_views = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.video_views);
        String thumb = relatedVideo.getThumbUrl();
        video_name.setText(relatedVideo.getVideoName());
        video_time.setText(relatedVideo.getVideoDuration());
        video_views.setText(relatedVideo.getVideoViews() + " Views");
        if (!Utility.isEmpty(thumb))
            Glide.with(viewHolder.getRoot().getContext()).load(thumb).into(video_thumb);
    }

    @Override
    public int getLayout() {
        return R.layout.card_video;
    }
}
