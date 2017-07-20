
package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedTopicsVideo {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("videoName")
    @Expose
    private String videoName;
    @SerializedName("videoDuration")
    @Expose
    private String videoDuration;
    @SerializedName("thumbUrl")
    @Expose
    private String thumbUrl;
    @SerializedName("videoViews")
    @Expose
    private Integer videoViews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getVideoViews() {
        return videoViews;
    }

    public void setVideoViews(Integer videoViews) {
        this.videoViews = videoViews;
    }

}
