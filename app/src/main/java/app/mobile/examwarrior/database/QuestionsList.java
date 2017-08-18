package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

/**
 * Awesome Pojo Generator
 */
public class QuestionsList {
    @PrimaryKey
    @SerializedName("course_id")
    @Expose
    private String course_id;
    @SerializedName("questions")
    @Expose
    private RealmList<Questions> questions;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public RealmList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(RealmList<Questions> questions) {
        this.questions = questions;
    }
}