package app.mobile.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;

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

    private CustomFontTextView video_title;
    private AppCompatTextView up_vote_count, down_vote_count;
    private ModuleItem data;
    private VideoEntity videoEntity;

    public VideoContentDetailsAdapter(ModuleItem data, VideoEntity videoEntity) {
        this.data = data;
        this.videoEntity = videoEntity;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        video_title = (CustomFontTextView) viewHolder.getRoot().findViewById(R.id.video_title);
        up_vote_count = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.up_vote_count);
        down_vote_count = (AppCompatTextView) viewHolder.getRoot().findViewById(R.id.down_vote_count);
        if (data != null) {
            video_title.setText(data.getItemName());
        }
        if (videoEntity != null) {
            up_vote_count.setText(videoEntity.getUpvCnt());
            down_vote_count.setText(videoEntity.getDwnCnt());
        }
    }

    @Override
    public void bind(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.bind(holder, position, payloads);
    }

    @Override
    public int getLayout() {
        return R.layout.video_header;
    }
}
