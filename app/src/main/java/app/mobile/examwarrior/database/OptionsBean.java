package app.mobile.examwarrior.database;

import io.realm.RealmObject;

/**
 * Created by iaman on 22-07-2017.
 */

public class OptionsBean extends RealmObject {

    private String text;
    private String id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
