package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Mcourses {
    @SerializedName("mcoursename")
    @Expose
    private String mcoursename;
    @SerializedName("tutors")
    @Expose
    private List<Tutors> tutors;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("mcourseType")
    @Expose
    private String mcourseType;
    @SerializedName("buy")
    @Expose
    private String buy;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("about_mcourse")
    @Expose
    private String about_mcourse;
    @SerializedName("mcourseid")
    @Expose
    private String mcourseid;
    @SerializedName("mcourse_pic")
    @Expose
    private String mcourse_pic;

    public String getMcoursename() {
        return mcoursename;
    }

    public void setMcoursename(String mcoursename) {
        this.mcoursename = mcoursename;
    }

    public List<Tutors> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutors> tutors) {
        this.tutors = tutors;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getMcourseType() {
        return mcourseType;
    }

    public void setMcourseType(String mcourseType) {
        this.mcourseType = mcourseType;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAbout_mcourse() {
        return about_mcourse;
    }

    public void setAbout_mcourse(String about_mcourse) {
        this.about_mcourse = about_mcourse;
    }

    public String getMcourseid() {
        return mcourseid;
    }

    public void setMcourseid(String mcourseid) {
        this.mcourseid = mcourseid;
    }

    public String getMcourse_pic() {
        return mcourse_pic;
    }

    public void setMcourse_pic(String mcourse_pic) {
        this.mcourse_pic = mcourse_pic;
    }
}