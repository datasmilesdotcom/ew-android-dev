package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandesh on 3/6/17, 5:52 PM.
 */

public class LoginBody {

    public LoginBody(String usrId, String pwd) {
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
