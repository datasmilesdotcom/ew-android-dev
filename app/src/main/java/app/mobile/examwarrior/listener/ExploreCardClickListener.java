package app.mobile.examwarrior.listener;

import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseMoreCategories;

public interface ExploreCardClickListener {

    void getMoreCources(String courseId);
    void exploreCources(CourseCategories.McoursesBean itemsEntity);
    void exploreCourcesMore(CourseMoreCategories.CoursesBean itemsEntity);
}
