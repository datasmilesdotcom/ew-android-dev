package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 20/7/17, 6:05 PM.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoEntityBody {

    @SerializedName("videoId")
    @Expose
    private String videoId;

    public VideoEntityBody(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
