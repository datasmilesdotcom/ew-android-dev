package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Awesome Pojo Generator
 */
public class Questions extends RealmObject {

    @SerializedName("questionId")
    @Expose
    @PrimaryKey
    private String questionId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("ans")
    @Expose
    private RealmList<Ans> ans;
    @SerializedName("options")
    @Expose
    private RealmList<Options> options;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RealmList<Ans> getAns() {
        return ans;
    }

    public void setAns(RealmList<Ans> ans) {
        this.ans = ans;
    }

    public RealmList<Options> getOptions() {
        return options;
    }

    public void setOptions(RealmList<Options> options) {
        this.options = options;
    }
}