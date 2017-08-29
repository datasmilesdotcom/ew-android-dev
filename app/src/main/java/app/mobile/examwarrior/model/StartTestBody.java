
package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartTestBody {

    @SerializedName("test_id")
    @Expose
    private String test_id;
    @SerializedName("shouldStartNew")
    @Expose
    private Boolean shouldStartNew;

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public Boolean getShouldStartNew() {
        return shouldStartNew;
    }

    public void setShouldStartNew(Boolean shouldStartNew) {
        this.shouldStartNew = shouldStartNew;
    }

}
