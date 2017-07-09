package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("firstTime")
    @Expose
    private Boolean firstTime;

    @PrimaryKey
    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("dsp_nm")
    @Expose
    private String dspNm;
    @SerializedName("usr_email")
    @Expose
    private String usrEmail;
    @SerializedName("usr_ph")
    @Expose
    private String usrPh;
    @SerializedName("usr_pic")
    @Expose
    private String usrPic;
    @SerializedName("is_act")
    @Expose
    private String isAct;
    @SerializedName("user_roles_1")
    @Expose
    private String userRoles1;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getDspNm() {
        return dspNm;
    }

    public void setDspNm(String dspNm) {
        this.dspNm = dspNm;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrPh() {
        return usrPh;
    }

    public void setUsrPh(String usrPh) {
        this.usrPh = usrPh;
    }

    public String getUsrPic() {
        return usrPic;
    }

    public void setUsrPic(String usrPic) {
        this.usrPic = usrPic;
    }

    public String getIsAct() {
        return isAct;
    }

    public void setIsAct(String isAct) {
        this.isAct = isAct;
    }

    public String getUserRoles1() {
        return userRoles1;
    }

    public void setUserRoles1(String userRoles1) {
        this.userRoles1 = userRoles1;
    }


}
