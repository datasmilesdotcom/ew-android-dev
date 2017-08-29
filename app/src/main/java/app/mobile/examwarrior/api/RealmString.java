package app.mobile.examwarrior.api;

import io.realm.RealmObject;

/**
 * Created by sandesh on 25/8/17, 11:48 AM.
 */

public class RealmString extends RealmObject {
    private String val;

    public RealmString() {
    }

    public RealmString(String value) {
        this.val = value;
    }

    public String getValue() {
        return val;
    }

    public void setValue(String value) {
        this.val = value;
    }


    @Override
    public String toString() {
        return val;
    }
}
