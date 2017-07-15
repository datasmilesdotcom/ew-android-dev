package app.mobile.examwarrior.listener;

import app.mobile.examwarrior.model.CourseCategories;

public interface ExploreCardClickListener {

    void getMoreCources(String courseId);
    void exploreCources(CourseCategories.McoursesBean itemsEntity);
}
