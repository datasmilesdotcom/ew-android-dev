package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 4/5/17, 3:19 PM.
 */

public class CourseDetailId {

    private String courseId;

    public CourseDetailId() {
    }

    public CourseDetailId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
