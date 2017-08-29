
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Option extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("originalText")
    @Expose
    private String originalText;
    @SerializedName("ans")
    @Expose
    private Boolean ans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public Boolean getAns() {
        return ans;
    }

    public void setAns(Boolean ans) {
        this.ans = ans;
    }

}
