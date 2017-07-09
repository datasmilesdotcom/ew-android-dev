package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 3/6/17, 6:38 PM.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpBody {

    public SignUpBody(String usrId, String dspNm, String usrEmail, String usrPh, String usrRole, String pwd) {
        this.usrId = usrId;
        this.dspNm = dspNm;
        this.usrEmail = usrEmail;
        this.usrPh = usrPh;
        this.usrRole = usrRole;
        this.pwd = pwd;
    }

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
    @SerializedName("usr_role")
    @Expose
    private String usrRole;
    @SerializedName("pwd")
    @Expose
    private String pwd;

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

    public String getUsrRole() {
        return usrRole;
    }

    public void setUsrRole(String usrRole) {
        this.usrRole = usrRole;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
