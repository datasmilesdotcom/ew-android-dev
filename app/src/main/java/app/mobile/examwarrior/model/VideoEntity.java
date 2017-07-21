package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 20/7/17, 5:17 PM.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("upv_cnt")
    @Expose
    private String upvCnt;
    @SerializedName("dwn_cnt")
    @Expose
    private String dwnCnt;
    @SerializedName("dwn")
    @Expose
    private Integer dwn;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("url_360")
    @Expose
    private String url360;
    @SerializedName("url_480")
    @Expose
    private String url480;
    @SerializedName("url_720")
    @Expose
    private String url720;
    @SerializedName("subtitleUrl")
    @Expose
    private String subtitleUrl;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl360() {
        return url360;
    }

    public void setUrl360(String url360) {
        this.url360 = url360;
    }

    public String getUrl480() {
        return url480;
    }

    public void setUrl480(String url480) {
        this.url480 = url480;
    }

    public String getUrl720() {
        return url720;
    }

    public void setUrl720(String url720) {
        this.url720 = url720;
    }

    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    public void setSubtitleUrl(String subtitleUrl) {
        this.subtitleUrl = subtitleUrl;
    }

}
