
package app.mobile.examwarrior.database;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class StartUserExam extends RealmObject{

    @SerializedName("totalQuestions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("allQuestions")
    @Expose
    private RealmList<AllQuestion> allQuestions = null;

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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public RealmList<AllQuestion> getAllQuestions() {
        return allQuestions;
    }

    public void setAllQuestions(RealmList<AllQuestion> allQuestions) {
        this.allQuestions = allQuestions;
    }

}
