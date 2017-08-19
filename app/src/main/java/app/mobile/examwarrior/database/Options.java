package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Awesome Pojo Generator
 */
public class Options extends RealmObject {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("id")
    @Expose
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