package app.mobile.examwarrior.players;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.mobile.examwarrior.R;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<YoutubeVideo> youtubeVideoList;

    public VideoAdapter() {
    }

    public VideoAdapter(List<YoutubeVideo> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_header, parent, false);
                return new HeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_video, parent, false);
                return new VideoViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoViewHolder) {
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.videoWeb.setText("Video " + position);
        }
    }


    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = (TextView) itemView.findViewById(R.id.webVideoView);
            videoWeb.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int i = getLayoutPosition();
            switch (view.getId()) {
                case R.id.webVideoView:
                    MainActivity.youTubePlayer.loadVideo(MainActivity.list.get(i));
                    MainActivity.youTubePlayer.play();
                    break;
            }
        }

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView videoWeb;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
