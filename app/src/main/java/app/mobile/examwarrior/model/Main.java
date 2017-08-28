package app.mobile.examwarrior.model;

import io.realm.RealmList;

/**
 * Created by sandesh on 25/8/17, 11:57 AM.
 */

public class Main {

    private RealmList<Questions> questions;

    public RealmList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(RealmList<Questions> questions) {
        this.questions = questions;
    }
}
