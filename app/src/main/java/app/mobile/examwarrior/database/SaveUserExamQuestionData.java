
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SaveUserExamQuestionData extends RealmObject {

    @SerializedName("questionData")
    @Expose
    private String questionData;
    @SerializedName("topic_id")
    @Expose
    private String topicId;

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("token_id")
    @Expose
    private String tokenId;
    @PrimaryKey
    @SerializedName("questionId")
    @Expose
    private String questionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

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
