package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.CourseDetailsAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.CourseDetail;
import app.mobile.examwarrior.database.ModuleDetail;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.model.CourseDetailId;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.CustomFontTextView;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {

    public static final String KEY_COURSE_ID = "app.mobile.examwarrior.course.id";
    CourseDetailsAdapter courseDetailsAdapter;
    RecyclerView courses_detail_list;
    private Realm realm;
    private RealmResults<CourseDetail> courseDetailsList;
    private List<CourseHeader> courseHeaders = new ArrayList<>();
    private CustomFontTextView courseName;
    private CustomFontTextView courseShortDetail, course_level;
    private Call<List<CourseDetail>> coursesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        realm = Realm.getDefaultInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_course_detail);
        setSupportActionBar(toolbar);
        getCourseDetails(getIntent().getStringExtra(KEY_COURSE_ID));
        courses_detail_list = (RecyclerView) findViewById(R.id.courses_detail_list);
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(CourseDetail.class);
                realm.delete(ModuleDetail.class);
                realm.delete(ModuleItem.class);
            }
        });*/

        courseDetailsList = realm.where(CourseDetail.class).equalTo("courseId", getIntent().getStringExtra(KEY_COURSE_ID)).findAllAsync();
        courseDetailsList.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<CourseDetail>>() {
            @Override
            public void onChange(RealmResults<CourseDetail> courseDetails, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                if (courseDetails.size() > 0) {
                    if (courseDetails.get(0).getModuleDetail() != null && courseHeaders.size() <= 0)
                        for (ModuleDetail moduleDetail : courseDetails.get(0).getModuleDetail()) {
                            courseHeaders.add(new CourseHeader(moduleDetail.getModuleName(), moduleDetail.getModuleId(), moduleDetail.getModuleItems()));
                        }
                    if (courseDetailsAdapter == null || courseDetailsAdapter.getItemCount() <= 0) {
                        courseDetailsAdapter = new CourseDetailsAdapter(CourseDetailsActivity.this, courseHeaders);
                        courses_detail_list.setAdapter(courseDetailsAdapter);
                    }

                    if (!Utility.isEmpty(courseDetails.get(0).getCourseShortDesc()))
                        courseName.setText(courseDetails.get(0).getCourseName());
                    if (!Utility.isEmpty(courseDetails.get(0).getCourseShortDesc()))
                        courseShortDetail.setText(Html.fromHtml(courseDetails.get(0).getCourseShortDesc()));
                    if (!Utility.isEmpty(courseDetails.get(0).getCourseLevel()))
                        course_level.setText("CourseLevel " + courseDetails.get(0).getCourseLevel());
                }
            }
        });
        courses_detail_list.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator animator = courses_detail_list.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        courseName = (CustomFontTextView) findViewById(R.id.course_name);
        courseShortDetail = (CustomFontTextView) findViewById(R.id.course_short_detail);
        course_level = (CustomFontTextView) findViewById(R.id.course_level);

    }

    /**
     * Get course details for course
     *
     * @param courseId
     */
    private void getCourseDetails(String courseId) {
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        coursesList = apiInterface.getCourseDetail(new CourseDetailId(courseId));
        coursesList.enqueue(new Callback<List<CourseDetail>>() {
            @Override
            public void onResponse(Call<List<CourseDetail>> call, final Response<List<CourseDetail>> response) {
                if (response.body() != null && !response.body().isEmpty()) {
                    try {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                for (CourseDetail courseDetail : response.body()) {
                                    realm.insertOrUpdate(courseDetail);
                                }
                            }
                        });

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CourseDetail>> call, Throwable t) {
                Utility.showMessage(t.getMessage());
            }
        });
    }


    @Override
    protected void onDestroy() {
        //realm.close();
        coursesList.cancel();
        super.onDestroy();
    }
}
