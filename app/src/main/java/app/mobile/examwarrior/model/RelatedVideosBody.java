package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 20/7/17, 9:22 PM.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedVideosBody {

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("courseId")
    @Expose
    private String courseId;

    public RelatedVideosBody(String videoId, String courseId) {
        this.videoId = videoId;
        this.courseId = courseId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
