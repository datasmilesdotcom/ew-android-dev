package app.mobile.examwarrior.ui.activity;


import android.app.Dialog;

import android.content.ComponentName;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.CourseDetailsAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.CourseDetail;
import app.mobile.examwarrior.database.ModuleDetail;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.delegates.CourseModuleItemListener;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.expandable_list.viewholders.ChildViewHolder;
import app.mobile.examwarrior.model.CourseDetailId;
import app.mobile.examwarrior.player.PlayerActivity;
import app.mobile.examwarrior.player.VideoPlayerActivity;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.CustomFontTextView;
import de.timfreiheit.mathjax.android.MathJaxView;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity implements CourseModuleItemListener {

    public static final String KEY_COURSE_ID = "app.mobile.examwarrior.course.id";
    private CourseDetailsAdapter courseDetailsAdapter;
    private RecyclerView courses_detail_list;
    private Realm realm;
    private RealmResults<CourseDetail> courseDetailsList;
    private List<CourseHeader> courseHeaders = new ArrayList<>();
    private Call<List<CourseDetail>> coursesList;
    private CustomFontTextView action_bar_title;
    private String course_name,module_name,description_value="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        action_bar_title= (CustomFontTextView) findViewById(R.id.action_bar_title);
        AppCompatImageView info= (AppCompatImageView) findViewById(R.id.info);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();

        getCourseDetails(getIntent().getStringExtra(KEY_COURSE_ID));
        courses_detail_list = (RecyclerView) findViewById(R.id.courses_detail_list);


        courseDetailsList = realm.where(CourseDetail.class).equalTo("courseId", getIntent().getStringExtra(KEY_COURSE_ID)).findAllAsync();
        courseDetailsList.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<CourseDetail>>() {
            @Override
            public void onChange(RealmResults<CourseDetail> courseDetails, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                if (courseDetails.size() > 0) {

                    if (courseDetails.get(0).getModuleDetail() != null && courseHeaders.size() <= 0)
                        for (ModuleDetail moduleDetail : courseDetails.get(0).getModuleDetail().sort("moduleWeight", Sort.ASCENDING)) {
                            courseHeaders.add(new CourseHeader(moduleDetail.getModuleName(), moduleDetail.getModuleId(), moduleDetail.getModuleItems().sort("itemWeight", Sort.ASCENDING)));
                        }
                    if (courseDetailsAdapter == null || courseDetailsAdapter.getItemCount() <= 0) {
                        courseDetailsAdapter = new CourseDetailsAdapter(CourseDetailsActivity.this, courseHeaders);
                        courseDetailsAdapter.setCourseModuleItemListener(CourseDetailsActivity.this);
                        courses_detail_list.setAdapter(courseDetailsAdapter);

                    }
                    if (!Utility.isEmpty(courseDetails.get(0).getCourseLongDesc()))
                        description_value=  courseDetails.get(0).getCourseLongDesc();
                    if (!Utility.isEmpty(courseDetails.get(0).getCourseSubGroup()))
                        module_name=courseDetails.get(0).getCourseSubGroup();

                }
            }
        });
        courses_detail_list.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator animator = courses_detail_list.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog();
            }
        });
        info.setVisibility(View.VISIBLE);

    }

    /**
     * Get course details for course
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


    private void buildDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.course_details_description_dialog);

        AppCompatTextView course_title = (AppCompatTextView) dialog.findViewById(R.id.course_name);
        AppCompatTextView course_module = (AppCompatTextView) dialog.findViewById(R.id.modue_name);
        MathJaxView description = (MathJaxView) dialog.findViewById(R.id.content);
        course_title.setText("Course : "+course_name);
        course_module.setText("Module :"+module_name);
        description.setInputText(description_value);


        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        dialog.getWindow().getAttributes().gravity = Gravity.RIGHT|Gravity.TOP;
        dialog.show();

    }

    @Override
    public void onModuleClickListener(ChildViewHolder holder, int position, ModuleItem data) {
        Intent intent = new Intent(getIntent());
        //intent.setAction(PlayerActivity.ACTION_VIEW_LIST);
        intent.setComponent(new ComponentName(CourseDetailsActivity.this, VideoPlayerActivity.class));
        intent.putExtra(PlayerActivity.KEY_MODULE_ITEM_ID, data.getItemId());
        startActivity(intent);

    }
}
