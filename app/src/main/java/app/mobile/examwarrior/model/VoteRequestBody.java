package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 21/7/17, 4:39 PM.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoteRequestBody {

    @SerializedName("dwn_cnt")
    @Expose
    private Integer dwnCnt;
    @SerializedName("upv_cnt")
    @Expose
    private Integer upvCnt;
    @SerializedName("vdo_id")
    @Expose
    private String vdoId;

    public VoteRequestBody(Integer dwnCnt, Integer upvCnt, String vdoId) {
        this.dwnCnt = dwnCnt;
        this.upvCnt = upvCnt;
        this.vdoId = vdoId;
    }

    public Integer getDwnCnt() {
        return dwnCnt;
    }

    public void setDwnCnt(Integer dwnCnt) {
        this.dwnCnt = dwnCnt;
    }

    public Integer getUpvCnt() {
        return upvCnt;
    }

    public void setUpvCnt(Integer upvCnt) {
        this.upvCnt = upvCnt;
    }

    public String getVdoId() {
        return vdoId;
    }

    public void setVdoId(String vdoId) {
        this.vdoId = vdoId;
    }
}
