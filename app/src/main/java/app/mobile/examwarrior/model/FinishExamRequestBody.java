package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class FinishExamRequestBody {
    @SerializedName("test_id")
    @Expose
    private String test_id;

    public FinishExamRequestBody() {
    }

    public FinishExamRequestBody(String test_id) {
        this.test_id = test_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }
}