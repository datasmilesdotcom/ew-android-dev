
package app.mobile.examwarrior.database;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import app.mobile.examwarrior.model.RealmString;
import io.realm.RealmList;
import io.realm.RealmObject;

public class FinishUserExam extends RealmObject {

    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("wrong_ans")
    @Expose
    private RealmList<RealmString> wrongAns = null;
    @SerializedName("correct_ans")
    @Expose
    private RealmList<RealmString> correctAns = null;
    @SerializedName("attempted_que")
    @Expose
    private RealmList<RealmString> attemptedQue = null;
    @SerializedName("skipped_que")
    @Expose
    private RealmList<RealmString> skippedQue = null;
    @SerializedName("review_que")
    @Expose
    private RealmList<RealmString> reviewQue = null;
    @SerializedName("totalQuestions")
    @Expose
    private RealmList<RealmString> totalQuestions = null;
    @SerializedName("test_id")
    @Expose
    private String testId;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public RealmList<RealmString> getWrongAns() {
        return wrongAns;
    }

    public void setWrongAns(RealmList<RealmString> wrongAns) {
        this.wrongAns = wrongAns;
    }

    public RealmList<RealmString> getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(RealmList<RealmString> correctAns) {
        this.correctAns = correctAns;
    }

    public RealmList<RealmString> getAttemptedQue() {
        return attemptedQue;
    }

    public void setAttemptedQue(RealmList<RealmString> attemptedQue) {
        this.attemptedQue = attemptedQue;
    }

    public RealmList<RealmString> getSkippedQue() {
        return skippedQue;
    }

    public void setSkippedQue(RealmList<RealmString> skippedQue) {
        this.skippedQue = skippedQue;
    }

    public RealmList<RealmString> getReviewQue() {
        return reviewQue;
    }

    public void setReviewQue(RealmList<RealmString> reviewQue) {
        this.reviewQue = reviewQue;
    }

    public RealmList<RealmString> getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(RealmList<RealmString> totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

}
