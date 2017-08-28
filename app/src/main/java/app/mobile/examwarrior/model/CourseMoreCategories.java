package app.mobile.examwarrior.model;

import java.util.List;

/**
 * Created by iaman on 15-07-2017.
 */

public class CourseMoreCategories {

    private String _id;
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

        private String _id;
        private String courseId;
        private String courseName;
        private String courseLongDesc;
        private String courseShortDesc;
        private String courseLevel;
        private String courseLevelDesc;
        private String courseType;
        private String imageUrl;
        private String courseSubGroup;

        private String createdAt;
        private String updatedAt;
        private List<AuthorBean> author;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getCourseLevelDesc() {
            return courseLevelDesc;
        }

        public void setCourseLevelDesc(String courseLevelDesc) {
            this.courseLevelDesc = courseLevelDesc;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<AuthorBean> getAuthor() {
            return author;
        }

        public void setAuthor(List<AuthorBean> author) {
            this.author = author;
        }

        public static class AuthorBean {
            /**
             * authorId : jeevan
             * authorName : Jeevan Kumar Deva
             */

            private String authorId;
            private String authorName;

            public String getAuthorId() {
                return authorId;
            }

            public void setAuthorId(String authorId) {
                this.authorId = authorId;
            }

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }
        }
    }
}
