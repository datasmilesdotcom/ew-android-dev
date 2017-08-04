package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 20/7/17, 5:17 PM.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoEntity {

    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("vdo_id")
    @Expose
    private String vdoId;
    @SerializedName("crt_dt")
    @Expose
    private Object crtDt;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("upd_dt")
    @Expose
    private String updDt;
    @SerializedName("upv")
    @Expose
    private Integer upv;
    @SerializedName("avg_rating")
    @Expose
    private double avgRating;

    @SerializedName("video_urls")
    @Expose
    private List<Video_urls> video_urls;
    @SerializedName("upv_cnt")
    @Expose
    private String upvCnt;
    @SerializedName("dwn_cnt")
    @Expose
    private String dwnCnt;
    @SerializedName("dwn")
    @Expose
    private Integer dwn;

    @SerializedName("subtitleUrl")
    @Expose
    private String subtitleUrl;

    public List<Video_urls> getVideo_urls() {
        return video_urls;
    }

    public void setVideo_urls(List<Video_urls> video_urls) {
        this.video_urls = video_urls;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getVdoId() {
        return vdoId;
    }

    public void setVdoId(String vdoId) {
        this.vdoId = vdoId;
    }

    public Object getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(Object crtDt) {
        this.crtDt = crtDt;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUpdDt() {
        return updDt;
    }

    public void setUpdDt(String updDt) {
        this.updDt = updDt;
    }

    public Integer getUpv() {
        return upv;
    }

    public void setUpv(Integer upv) {
        this.upv = upv;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getUpvCnt() {
        return upvCnt;
    }

    public void setUpvCnt(String upvCnt) {
        this.upvCnt = upvCnt;
    }

    public String getDwnCnt() {
        return dwnCnt;
    }

    public void setDwnCnt(String dwnCnt) {
        this.dwnCnt = dwnCnt;
    }

    public Integer getDwn() {
        return dwn;
    }

    public void setDwn(Integer dwn) {
        this.dwn = dwn;
    }

    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    public void setSubtitleUrl(String subtitleUrl) {
        this.subtitleUrl = subtitleUrl;
    }

}
