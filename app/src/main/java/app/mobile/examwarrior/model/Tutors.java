package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Tutors {
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("teacher_id")
    @Expose
    private Integer teacher_id;
    @SerializedName("lst_nm")
    @Expose
    private String lst_nm;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("sub_teach")
    @Expose
    private String sub_teach;
    @SerializedName("cls_covers")
    @Expose
    private String cls_covers;
    @SerializedName("fst_nm")
    @Expose
    private String fst_nm;
    @SerializedName("teaching_exp")
    @Expose
    private String teaching_exp;
    @SerializedName("teaches")
    @Expose
    private String teaches;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("speaks")
    @Expose
    private String speaks;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("teacher_pic")
    @Expose
    private String teacher_pic;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getLst_nm() {
        return lst_nm;
    }

    public void setLst_nm(String lst_nm) {
        this.lst_nm = lst_nm;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSub_teach() {
        return sub_teach;
    }

    public void setSub_teach(String sub_teach) {
        this.sub_teach = sub_teach;
    }

    public String getCls_covers() {
        return cls_covers;
    }

    public void setCls_covers(String cls_covers) {
        this.cls_covers = cls_covers;
    }

    public String getFst_nm() {
        return fst_nm;
    }

    public void setFst_nm(String fst_nm) {
        this.fst_nm = fst_nm;
    }

    public String getTeaching_exp() {
        return teaching_exp;
    }

    public void setTeaching_exp(String teaching_exp) {
        this.teaching_exp = teaching_exp;
    }

    public String getTeaches() {
        return teaches;
    }

    public void setTeaches(String teaches) {
        this.teaches = teaches;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSpeaks() {
        return speaks;
    }

    public void setSpeaks(String speaks) {
        this.speaks = speaks;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTeacher_pic() {
        return teacher_pic;
    }

    public void setTeacher_pic(String teacher_pic) {
        this.teacher_pic = teacher_pic;
    }
}