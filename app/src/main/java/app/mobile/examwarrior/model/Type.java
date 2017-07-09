package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 4/5/17, 3:19 PM.
 */

public class Type {

    public Type() {
    }

    public Type(String courseType) {
        this.courseType = courseType;
    }

    private String courseType;

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}
