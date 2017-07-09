package app.mobile.examwarrior.database;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CourseDetail extends RealmObject {

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
    @SerializedName("courseType")
    @Expose
    private String courseType;

    @SerializedName("courseSubGroup")
    @Expose
    private String courseSubGroup;
    @SerializedName("courseLongDesc")
    @Expose
    private String courseLongDesc;
    @SerializedName("courseShortDesc")
    @Expose
    private String courseShortDesc;
    @SerializedName("courseLevel")
    @Expose
    private String courseLevel;
    @SerializedName("courseLevelDesc")
    @Expose
    private String courseLevelDesc;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("moduleDetail")
    @Expose
    private RealmList<ModuleDetail> moduleDetail = null;
    @SerializedName("LastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("isLiveClass")
    @Expose
    private String isLiveClass;

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

    public String getCourseLevelDesc() {
        return courseLevelDesc;
    }

    public void setCourseLevelDesc(String courseLevelDesc) {
        this.courseLevelDesc = courseLevelDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RealmList<ModuleDetail> getModuleDetail() {
        return moduleDetail;
    }

    public void setModuleDetail(RealmList<ModuleDetail> moduleDetail) {
        this.moduleDetail = moduleDetail;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getIsLiveClass() {
        return isLiveClass;
    }

    public void setIsLiveClass(String isLiveClass) {
        this.isLiveClass = isLiveClass;
    }

}
