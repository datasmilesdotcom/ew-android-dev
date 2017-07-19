package app.mobile.examwarrior.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iaman on 15-07-2017.
 */

public class CourseCategories {

    private String _id;
    private String course_cat;
    private String course_cat_id;
    private String course_cat_title;
    private String course_type_title;
    private List<McoursesBean> mcourses;

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

    public String getCourse_cat_id() {
        return course_cat_id;
    }

    public void setCourse_cat_id(String course_cat_id) {
        this.course_cat_id = course_cat_id;
    }

    public String getCourse_cat_title() {
        return course_cat_title;
    }

    public void setCourse_cat_title(String course_cat_title) {
        this.course_cat_title = course_cat_title;
    }

    public String getCourse_type_title() {
        return course_type_title;
    }

    public void setCourse_type_title(String course_type_title) {
        this.course_type_title = course_type_title;
    }

    public List<McoursesBean> getMcourses() {
        return mcourses;
    }

    public void setMcourses(List<McoursesBean> mcourses) {
        this.mcourses = mcourses;
    }

    public static class McoursesBean implements Serializable{
        /**
         * mcourseid : informatica
         * mcoursename : informatica
         * pic : https://s3.ap-south-1.amazonaws.com/dsn.ew.timages/1.jpg
         */

        private String mcourseid;
        private String mcoursename;
        private String pic;

        public String getMcourseid() {
            return mcourseid;
        }

        public void setMcourseid(String mcourseid) {
            this.mcourseid = mcourseid;
        }

        public String getMcoursename() {
            return mcoursename;
        }

        public void setMcoursename(String mcoursename) {
            this.mcoursename = mcoursename;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
