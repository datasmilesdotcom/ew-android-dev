package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class MoreTutorsResponse {
    @SerializedName("mcourses")
    @Expose
    private List<Mcourses> mcourses;
    @SerializedName("course_cat_about")
    @Expose
    private String course_cat_about;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("course_cat_title")
    @Expose
    private String course_cat_title;
    @SerializedName("course_cat")
    @Expose
    private String course_cat;

    public List<Mcourses> getMcourses() {
        return mcourses;
    }

    public void setMcourses(List<Mcourses> mcourses) {
        this.mcourses = mcourses;
    }

    public String getCourse_cat_about() {
        return course_cat_about;
    }

    public void setCourse_cat_about(String course_cat_about) {
        this.course_cat_about = course_cat_about;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCourse_cat_title() {
        return course_cat_title;
    }

    public void setCourse_cat_title(String course_cat_title) {
        this.course_cat_title = course_cat_title;
    }

    public String getCourse_cat() {
        return course_cat;
    }

    public void setCourse_cat(String course_cat) {
        this.course_cat = course_cat;
    }
}