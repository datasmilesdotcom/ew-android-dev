
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSaveQuestionData {

    @SerializedName("questionId")
    @Expose
    private String questionId;
    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
