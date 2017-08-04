package app.mobile.examwarrior.RealmPrimitiveModel;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by iaman on 22-07-2017.
 */

@RealmClass
public class RealmStringAns extends RealmObject {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}