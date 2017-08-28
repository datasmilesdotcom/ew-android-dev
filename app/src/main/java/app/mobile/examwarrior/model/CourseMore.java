package app.mobile.examwarrior.model;

import java.util.List;

/**
 * Created by iaman on 05-08-2017.
 */

public class CourseMore {


    private String _id;
    private String course_cat;
    private String course_cat_title;
    private List<CourseMoreCategories> mcourses;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCourse_cat() {
        return course_cat;
    }

    public void setCourse_cat(String course_cat) {
        this.course_cat = course_cat;
    }

    public String getCourse_cat_title() {
        return course_cat_title;
    }

    public void setCourse_cat_title(String course_cat_title) {
        this.course_cat_title = course_cat_title;
    }

    public List<CourseMoreCategories> getMcourses() {
        return mcourses;
    }

    public void setMcourses(List<CourseMoreCategories> mcourses) {
        this.mcourses = mcourses;
    }
}
