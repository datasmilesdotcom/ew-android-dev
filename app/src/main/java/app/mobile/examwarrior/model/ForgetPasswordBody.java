package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandesh on 5/6/17, 10:41 PM.
 */

public class ForgetPasswordBody {

    @SerializedName("usr_id")
    @Expose
    private String usrId;

    public ForgetPasswordBody(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
