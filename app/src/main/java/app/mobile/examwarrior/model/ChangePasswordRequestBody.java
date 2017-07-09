package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandesh on 5/6/17, 11:14 PM.
 */

public class ChangePasswordRequestBody {

    public ChangePasswordRequestBody(String usrId, String pwd) {
        this.usrId = usrId;
        this.pwd = pwd;
    }

    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("pwd")
    @Expose
    private String pwd;

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
