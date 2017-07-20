package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelatedVideos {

    @SerializedName("relatedVideos")
    @Expose
    private List<RelatedVideo> relatedVideos = null;
    @SerializedName("relatedTopicsVideos")
    @Expose
    private List<RelatedTopicsVideo> relatedTopicsVideos = null;
    @SerializedName("relatedCourses")
    @Expose
    private List<RelatedCourse> relatedCourses = null;

    public List<RelatedVideo> getRelatedVideos() {
        return relatedVideos;
    }

    public void setRelatedVideos(List<RelatedVideo> relatedVideos) {
        this.relatedVideos = relatedVideos;
    }

    public List<RelatedTopicsVideo> getRelatedTopicsVideos() {
        return relatedTopicsVideos;
    }

    public void setRelatedTopicsVideos(List<RelatedTopicsVideo> relatedTopicsVideos) {
        this.relatedTopicsVideos = relatedTopicsVideos;
    }

    public List<RelatedCourse> getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(List<RelatedCourse> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

}
