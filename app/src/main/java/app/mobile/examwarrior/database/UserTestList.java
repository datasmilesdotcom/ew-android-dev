package app.mobile.examwarrior.database;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Kunjan on 26-08-2017.
 */


public class UserTestList extends RealmObject {
    RealmList<StartUserExam> startUserExams;

    public RealmList<StartUserExam> getStartUserExams() {
        return startUserExams;
    }

    public void setStartUserExams(RealmList<StartUserExam> startUserExams) {
        this.startUserExams.addAll(startUserExams);
    }


}