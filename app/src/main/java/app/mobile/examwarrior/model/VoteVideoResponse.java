package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 21/7/17, 4:43 PM.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoteVideoResponse {

    @SerializedName("dwn_cnt")
    @Expose
    private Integer dwnCnt;
    @SerializedName("upv_cnt")
    @Expose
    private Integer upvCnt;
    @SerializedName("vdo_id")
    @Expose
    private String vdoId;
    @SerializedName("usr_id")
    @Expose
    private String usrId;

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

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
