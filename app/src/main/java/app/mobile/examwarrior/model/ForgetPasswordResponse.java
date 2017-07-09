package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandesh on 5/6/17, 10:39 PM.
 */

public class ForgetPasswordResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ph")
    @Expose
    private String ph;

    @SerializedName("err")
    @Expose
    private String err;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
