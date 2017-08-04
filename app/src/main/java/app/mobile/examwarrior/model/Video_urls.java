package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Video_urls {
    @SerializedName("res")
    @Expose
    private String res;
    @SerializedName("video_url")
    @Expose
    private String video_url;
    @SerializedName("videoFileSize")
    @Expose
    private Integer videoFileSize;
    @SerializedName("downloadUrl")
    @Expose
    private String downloadUrl;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Integer getVideoFileSize() {
        return videoFileSize;
    }

    public void setVideoFileSize(Integer videoFileSize) {
        this.videoFileSize = videoFileSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}