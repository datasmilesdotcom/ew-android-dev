
package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartTestBody {

    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("shouldStartNew")
    @Expose
    private Boolean shouldStartNew;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Boolean getShouldStartNew() {
        return shouldStartNew;
    }

    public void setShouldStartNew(Boolean shouldStartNew) {
        this.shouldStartNew = shouldStartNew;
    }

}
