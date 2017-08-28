package app.mobile.examwarrior.listener;

import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.Tutors;

public interface ExploreCardClickListener {

    void getMoreCources(int position, String courseId);

    void exploreCources(int position, CourseCategories.McoursesBean itemsEntity);

    void exploreCourcesMore(CourseMoreCategories.CoursesBean itemsEntity);

    void onTutorItemClicked(int position, Tutors tutors);

}
