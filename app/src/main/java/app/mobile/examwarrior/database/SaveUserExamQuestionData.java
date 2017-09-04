
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import app.mobile.examwarrior.model.RealmAutoIncrement;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class SaveUserExamQuestionData extends RealmObject {

    @SerializedName("app_table_pk")
    @Index
    private long app_table_pk;
    @SerializedName("app_update_dt")
    @Expose
    private String app_update_dt;
    @SerializedName("questionData")
    @Expose
    private String questionData;
    @SerializedName("topic_id")
    @Expose
    private String topicId;

    @PrimaryKey
    @SerializedName("questionId")
    @Expose
    private String questionId;

    public long getApp_table_pk() {
        return app_table_pk;
    }

    public void setApp_table_pk() {
        this.app_table_pk = RealmAutoIncrement.getNextIdFromModel(Realm.getDefaultInstance(), SaveUserExamQuestionData.class, "app_table_pk");
    }

    public String getApp_update_dt() {
        return app_update_dt;
    }

    public void setApp_update_dt(String app_update_dt) {
        this.app_update_dt = app_update_dt;
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
