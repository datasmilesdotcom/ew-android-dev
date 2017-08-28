package app.mobile.examwarrior.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by sandesh on 25/8/17, 11:48 AM.
 */
@RealmClass
public class RealmString extends RealmObject {

    public String val;

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
