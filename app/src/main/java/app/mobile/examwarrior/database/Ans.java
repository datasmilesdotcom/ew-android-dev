package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Awesome Pojo Generator
 */
public class Ans extends RealmObject {
    @SerializedName("ans2")
    @Expose
    private String ans2;
    @SerializedName("ans1")
    @Expose
    private String ans1;

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }
}