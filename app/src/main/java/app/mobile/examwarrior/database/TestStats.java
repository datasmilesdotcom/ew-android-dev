package app.mobile.examwarrior.database;

import app.mobile.examwarrior.model.RealmAutoIncrement;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sandesh on 18/8/17, 2:31 PM.
 */

public class TestStats extends RealmObject {

    @PrimaryKey
    @Index
    private long _id = RealmAutoIncrement.getNextIdFromModel(Realm.getDefaultInstance(), TestStats.class, "_id");
    private String course_id;
    private String question_id;
    private boolean is_question_attempted = false;
    private long time_spent;
    private String chosen_answer;
    private boolean added_to_review;
    private boolean is_answer_correct;
    private long job_id;
    private int syncStatus;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public long getJob_id() {
        return job_id;
    }

    public void setJob_id(long job_id) {
        this.job_id = job_id;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public boolean isIs_question_attempted() {
        return is_question_attempted;
    }

    public void setIs_question_attempted(boolean is_question_attempted) {
        this.is_question_attempted = is_question_attempted;
    }

    public long getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(long time_spent) {
        this.time_spent = time_spent;
    }

    public String getChosen_answer() {
        return chosen_answer;
    }

    public void setChosen_answer(String chosen_answer) {
        this.chosen_answer = chosen_answer;
    }

    public boolean isAdded_to_review() {
        return added_to_review;
    }

    public void setAdded_to_review(boolean added_to_review) {
        this.added_to_review = added_to_review;
    }

    public boolean isIs_answer_correct() {
        return is_answer_correct;
    }

    public void setIs_answer_correct(boolean is_answer_correct) {
        this.is_answer_correct = is_answer_correct;
    }
}
