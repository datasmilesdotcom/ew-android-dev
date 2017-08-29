package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sandesh on 25/8/17, 11:55 AM.
 */

public class Questions extends RealmObject {

    @PrimaryKey
    @SerializedName("testName")
    private String testName;
    @SerializedName("totalQuestions")
    private RealmList<RealmString> list;

    @SerializedName("attempted_que")
    @Expose
    private RealmList<RealmString> attempted_que;

    @SerializedName("skipped_que")
    private RealmList<RealmString> skipped_que;

    public RealmList<RealmString> getAttempted_que() {
        return attempted_que;
    }

    public void setAttempted_que(RealmList<RealmString> attempted_que) {
        this.attempted_que = attempted_que;
    }

    public RealmList<RealmString> getSkipped_que() {
        return skipped_que;
    }

    public void setSkipped_que(RealmList<RealmString> skipped_que) {
        this.skipped_que = skipped_que;
    }

    public RealmList<RealmString> getList() {
        return list;
    }

    public void setList(RealmList<RealmString> list) {
        this.list = list;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
