package app.mobile.examwarrior.model;

import java.util.List;

/**
 * Created by iaman on 15-07-2017.
 */

public class CourseMoreCategories {



    private String _id;
    private String course_cat_id;
    private String mcourseid;
    private String mcoursename;
    private String about_mcourse;
    private String buy;
    private List<CoursesBean> courses;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCourse_cat_id() {
        return course_cat_id;
    }

    public void setCourse_cat_id(String course_cat_id) {
        this.course_cat_id = course_cat_id;
    }

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

    public String getAbout_mcourse() {
        return about_mcourse;
    }

    public void setAbout_mcourse(String about_mcourse) {
        this.about_mcourse = about_mcourse;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public List<CoursesBean> getCourses() {
        return courses;
    }

    public void setCourses(List<CoursesBean> courses) {
        this.courses = courses;
    }

    public static class CoursesBean {
        /**
         * COURSEID : informatica-powercenter-basics
         * COURSENAME : Informatica Powercenter Basics
         * PIC : https://s3.ap-south-1.amazonaws.com/dsn.ew.timages/10.jpg
         */

        private String COURSEID;
        private String COURSENAME;
        private String PIC;

        public String getCOURSEID() {
            return COURSEID;
        }

        public void setCOURSEID(String COURSEID) {
            this.COURSEID = COURSEID;
        }

        public String getCOURSENAME() {
            return COURSENAME;
        }

        public void setCOURSENAME(String COURSENAME) {
            this.COURSENAME = COURSENAME;
        }

        public String getPIC() {
            return PIC;
        }

        public void setPIC(String PIC) {
            this.PIC = PIC;
        }
    }
}
