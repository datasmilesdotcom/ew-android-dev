package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sandesh on 4/5/17, 2:07 AM.
 */

public class Course extends RealmObject {


    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("courseId")
    @Expose
    @PrimaryKey
    private String courseId;

    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("courseLongDesc")
    @Expose
    private String courseLongDesc;
    @SerializedName("courseShortDesc")
    @Expose
    private String courseShortDesc;
    @SerializedName("courseLevel")
    @Expose
    private String courseLevel;
    @SerializedName("courseType")
    @Expose
    private String courseType;
    @SerializedName("courseSubGroup")
    @Expose
    private String courseSubGroup;
    @SerializedName("isLiveClass")
    @Expose
    private boolean isLiveClass;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLongDesc() {
        return courseLongDesc;
    }

    public void setCourseLongDesc(String courseLongDesc) {
        this.courseLongDesc = courseLongDesc;
    }

    public String getCourseShortDesc() {
        return courseShortDesc;
    }

    public void setCourseShortDesc(String courseShortDesc) {
        this.courseShortDesc = courseShortDesc;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseSubGroup() {
        return courseSubGroup;
    }

    public void setCourseSubGroup(String courseSubGroup) {
        this.courseSubGroup = courseSubGroup;
    }

    public boolean getIsLiveClass() {
        return isLiveClass;
    }

    public void setIsLiveClass(boolean isLiveClass) {
        this.isLiveClass = isLiveClass;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
