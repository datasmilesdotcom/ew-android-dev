
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class StartUserExam extends RealmObject{

    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("totalQuestions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("allQuestions")
    @Expose
    private RealmList<AllQuestion> allQuestions = null;
    @SerializedName("test_id")
    @Expose
    private String testId;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public RealmList<AllQuestion> getAllQuestions() {
        return allQuestions;
    }

    public void setAllQuestions(RealmList<AllQuestion> allQuestions) {
        this.allQuestions = allQuestions;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

}
