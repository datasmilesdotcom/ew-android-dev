package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandesh on 3/6/17, 6:40 PM.
 */

public class RegistrationResponse {

    @SerializedName("err")
    @Expose
    private String err;


    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

}
