
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveUserExamQuestionData {

    @SerializedName("questionData")
    @Expose
    private String questionData;
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("questionId")
    @Expose
    private String questionId;

    public String getQuestionData() {
        return questionData;
    }

    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

}
