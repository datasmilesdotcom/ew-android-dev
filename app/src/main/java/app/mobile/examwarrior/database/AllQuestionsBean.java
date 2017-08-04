package app.mobile.examwarrior.database;

import java.util.List;

import app.mobile.examwarrior.RealmPrimitiveModel.RealmStringAns;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by iaman on 22-07-2017.
 */

public class AllQuestionsBean extends RealmObject {

    private String _id;
    private int questionNumber;
    private String questionId;
    private String question;
    private int questionTime;
    private String textExplanation;
    private String questionDifficulty;
    private String isMCQ;
    private String update_ts;
    private String originalQuestion;
    private String originalTextExplanation;
    private RealmList<OptionsBean> options;
    //private RealmList<String> ans;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

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

    public int getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(int questionTime) {
        this.questionTime = questionTime;
    }

    public String getTextExplanation() {
        return textExplanation;
    }

    public void setTextExplanation(String textExplanation) {
        this.textExplanation = textExplanation;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getIsMCQ() {
        return isMCQ;
    }

    public void setIsMCQ(String isMCQ) {
        this.isMCQ = isMCQ;
    }

    public String getUpdate_ts() {
        return update_ts;
    }

    public void setUpdate_ts(String update_ts) {
        this.update_ts = update_ts;
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

    public RealmList<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(RealmList<OptionsBean> options) {
        this.options = options;
    }

   /* public RealmList<RealmStringAns> getAns() {
        return ans;
    }

    public void setAns(RealmList<RealmStringAns> ans) {
        this.ans = ans;
    }
*/


}
