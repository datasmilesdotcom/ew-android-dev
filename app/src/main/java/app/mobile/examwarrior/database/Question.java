package app.mobile.examwarrior.database;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by iaman on 22-07-2017.
 */

public class Question extends RealmObject {

    private int totalQuestions;
    private String usr_id;
    private String topic_id;
    private RealmList<AllQuestionsBean> allQuestions;

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public RealmList<AllQuestionsBean> getAllQuestions() {
        return allQuestions;
    }

    public void setAllQuestions(RealmList<AllQuestionsBean> allQuestions) {
        this.allQuestions = allQuestions;
    }

}
