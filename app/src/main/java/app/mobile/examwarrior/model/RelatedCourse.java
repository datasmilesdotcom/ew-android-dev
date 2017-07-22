
package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelatedCourse {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("author")
    @Expose
    private List<String> author = null;
    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("courseLongDesc")
    @Expose
    private String courseLongDesc;
    @SerializedName("courseShortDesc")
    @Expose
    private String courseShortDesc;
    @SerializedName("courseType")
    @Expose
    private String courseType;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("courseSubGroup")
    @Expose
    private String courseSubGroup;
    @SerializedName("isLiveClass")
    @Expose
    private Object isLiveClass;
    @SerializedName("courseWeight")
    @Expose
    private String courseWeight;
    @SerializedName("firstModuleId")
    @Expose
    private String firstModuleId;
    @SerializedName("firstItemId")
    @Expose
    private String firstItemId;
    @SerializedName("firstItemType")
    @Expose
    private String firstItemType;

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

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
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

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCourseSubGroup() {
        return courseSubGroup;
    }

    public void setCourseSubGroup(String courseSubGroup) {
        this.courseSubGroup = courseSubGroup;
    }

    public Object getIsLiveClass() {
        return isLiveClass;
    }

    public void setIsLiveClass(Object isLiveClass) {
        this.isLiveClass = isLiveClass;
    }

    public String getCourseWeight() {
        return courseWeight;
    }

    public void setCourseWeight(String courseWeight) {
        this.courseWeight = courseWeight;
    }

    public String getFirstModuleId() {
        return firstModuleId;
    }

    public void setFirstModuleId(String firstModuleId) {
        this.firstModuleId = firstModuleId;
    }

    public String getFirstItemId() {
        return firstItemId;
    }

    public void setFirstItemId(String firstItemId) {
        this.firstItemId = firstItemId;
    }

    public String getFirstItemType() {
        return firstItemType;
    }

    public void setFirstItemType(String firstItemType) {
        this.firstItemType = firstItemType;
    }

}
