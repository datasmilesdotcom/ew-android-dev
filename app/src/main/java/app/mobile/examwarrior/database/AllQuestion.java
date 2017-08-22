
package app.mobile.examwarrior.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AllQuestion  extends RealmObject implements Parcelable {

    @SerializedName("questionId")
    @Expose
    private String questionId;
    @SerializedName("questionTime")
    @Expose
    private Integer questionTime;
    @SerializedName("options")
    @Expose
    private RealmList<Option> options = null;
    @SerializedName("ans")
    @Expose
    private Ans ans;
    @SerializedName("originalQuestion")
    @Expose
    private String originalQuestion;
    @SerializedName("originalTextExplanation")
    @Expose
    private String originalTextExplanation;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Integer questionTime) {
        this.questionTime = questionTime;
    }

    public RealmList<Option> getOptions() {
        return options;
    }

    public void setOptions(RealmList<Option> options) {
        this.options = options;
    }

    public Ans getAns() {
        return ans;
    }

    public void setAns(Ans ans) {
        this.ans = ans;
    }

    public String getOriginalQuestion() {
        return originalQuestion;
    }

    public void setOriginalQuestion(String originalQuestion) {
        this.originalQuestion = originalQuestion;
    }

    public String getOriginalTextExplanation() {
        return originalTextExplanation;
    }

    public void setOriginalTextExplanation(String originalTextExplanation) {
        this.originalTextExplanation = originalTextExplanation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
