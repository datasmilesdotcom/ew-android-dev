package app.mobile.view_holder;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.model.VideoEntity;
import app.mobile.examwarrior.widget.CustomFontTextView;

/**
 * Created by sandesh on 16/7/17, 10:08 PM.
 */

public class VideoContentDetailsAdapter extends Item<ViewHolder> {

    public static final String UPVOVE_PAYLOAD = "app.mobile.view_holder.video.container.upvote";
    public static final String DOWN_PAYLOAD = "app.mobile.view_holder.video.container.downvote";
    private CustomFontTextView video_title;
    private AppCompatTextView up_vote_count, down_vote_count;
    private ModuleItem data;
    private VideoEntity videoEntity;
    private onItemListener onItemListener;
    private LinearLayout up_vote_view, down_vote_view, save_Video;
    private AppCompatImageView ic_like, ic_dislike;

    public VideoContentDetailsAdapter(ModuleItem data, VideoEntity videoEntity) {
        this.data = data;
        this.videoEntity = videoEntity;
    }

    public ModuleItem getData() {
        return data;
    }

    public void setData(ModuleItem data) {
        this.data = data;
    }

    public VideoEntity getVideoEntity() {
        return videoEntity;
    }

    public void setVideoEntity(VideoEntity videoEntity) {
        this.videoEntity = videoEntity;
    }

    public onItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(onItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    @Override
    public void bind(@NonNull final ViewHolder viewHolder, final int position) {
        video_title = (CustomFontTextView) viewHolder.getRoot().findViewById(R.id.video_title);
        up_vote_count = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.up_vote_count);
        down_vote_count = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.down_vote_count);
        up_vote_view = (LinearLayout) viewHolder.getRoot().findViewById(R.id.up_vote_view);
        down_vote_view = (LinearLayout) viewHolder.getRoot().findViewById(R.id.down_vote_view);
        ic_like = (AppCompatImageView) viewHolder.getRoot().findViewById(R.id.ic_like);
        ic_dislike = (AppCompatImageView) viewHolder.getRoot().findViewById(R.id.ic_dislike);
        save_Video = viewHolder.getRoot().findViewById(R.id.save_Video);
        save_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemListener != null)
                    onItemListener.onDownloadVideo(viewHolder, position, videoEntity);
            }
        });
        up_vote_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemListener != null)
                    onItemListener.onUpVote(viewHolder, position, videoEntity);
            }
        });
        down_vote_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemListener != null)
                    onItemListener.onDownVote(viewHolder, position, videoEntity);
            }
        });
        if (data != null) {
            video_title.setText(data.getItemName());
        }
        if (videoEntity != null) {
            up_vote_count.setText(videoEntity.getUpvCnt());
            down_vote_count.setText(videoEntity.getDwnCnt());
            if (videoEntity.getUpv() != null)
                ic_like.setColorFilter(videoEntity.getUpv() == 1 ? ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.colorPrimaryDark) : ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.fill_color));
            if (videoEntity.getDwn() != null)
                ic_dislike.setColorFilter(videoEntity.getDwn() == 1 ? ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.colorPrimaryDark) : ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.fill_color));
        }
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position, @NonNull List<Object> payloads) {
        if (payloads.contains("UPVOTE")) {
            if (up_vote_count != null) up_vote_count.setText(videoEntity.getUpvCnt());
            if (down_vote_count != null) down_vote_count.setText(videoEntity.getDwnCnt());
            ic_like.setColorFilter(videoEntity.getUpv() == 1 ? ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.colorPrimaryDark) : ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.fill_color));
            ic_dislike.setColorFilter(videoEntity.getDwn() == 1 ? ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.colorPrimaryDark) : ContextCompat.getColor(viewHolder.getRoot().getContext(), R.color.fill_color));
        }
        super.bind(viewHolder, position, payloads);
    }


    @Override
    public int getLayout() {
        return R.layout.video_header;
    }

    public interface onItemListener {
        public void onUpVote(ViewHolder item, int position, VideoEntity videoEntity);

        public void onDownVote(ViewHolder item, int position, VideoEntity videoEntity);

        public void onDownloadVideo(ViewHolder item, int position, VideoEntity videoEntity);
    }
}
