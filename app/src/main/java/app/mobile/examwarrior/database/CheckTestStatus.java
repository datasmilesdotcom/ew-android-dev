
package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class CheckTestStatus  extends RealmObject {

    @SerializedName("running")
    @Expose
    private Boolean running;
    @SerializedName("userAllowedToAcess")
    @Expose
    private Boolean userAllowedToAcess;

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Boolean getUserAllowedToAcess() {
        return userAllowedToAcess;
    }

    public void setUserAllowedToAcess(Boolean userAllowedToAcess) {
        this.userAllowedToAcess = userAllowedToAcess;
    }

}
