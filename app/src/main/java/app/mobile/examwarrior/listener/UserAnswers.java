package app.mobile.examwarrior.listener;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kunjan on 02-09-2017.
 */

public class UserAnswers extends RealmObject {

    String strOption1;
    String strOption2;
    String strOption3;
    String strOption4;
    @PrimaryKey
    @Index
    int position;

    public String getStrOption1() {
        return strOption1;
    }

    public void setStrOption1(String strOption1) {
        this.strOption1 = strOption1;
    }

    public String getStrOption2() {
        return strOption2;
    }

    public void setStrOption2(String strOption2) {
        this.strOption2 = strOption2;
    }

    public String getStrOption3() {
        return strOption3;
    }

    public void setStrOption3(String strOption3) {
        this.strOption3 = strOption3;
    }

    public String getStrOption4() {
        return strOption4;
    }

    public void setStrOption4(String strOption4) {
        this.strOption4 = strOption4;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
