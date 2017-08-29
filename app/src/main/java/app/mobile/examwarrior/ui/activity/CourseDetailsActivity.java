package app.mobile.examwarrior.ui.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.CourseDetailsAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.RealmString;
import app.mobile.examwarrior.api.RealmStringDeserializer;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.CheckTestStatus;
import app.mobile.examwarrior.database.CourseDetail;
import app.mobile.examwarrior.database.FinishUserExam;
import app.mobile.examwarrior.database.FinishUserExamBody;
import app.mobile.examwarrior.database.ModuleDetail;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.delegates.CourseModuleItemListener;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.expandable_list.viewholders.ChildViewHolder;
import app.mobile.examwarrior.model.CheckTestStatusModel;
import app.mobile.examwarrior.model.CourseDetailId;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.util.Utility;
import de.timfreiheit.mathjax.android.MathJaxView;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity implements CourseModuleItemListener {

    public static final String KEY_COURSE_ID = "app.mobile.examwarrior.course.id";
    final Gson gson = new GsonBuilder()
            .setLenient()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
            }.getType(), new RealmStringDeserializer())
            .create();
    private CourseDetailsAdapter courseDetailsAdapter;
    private RecyclerView courses_detail_list;
    private Realm realm;
    private RealmResults<CourseDetail> courseDetailsList;
    private List<CourseHeader> courseHeaders = new ArrayList<>();
    private Call<List<CourseDetail>> coursesList;
    private Call<CheckTestStatus> checkTestStatusCall;
    //private CustomFontTextView action_bar_title;
    private Toolbar toolbar;
    private AppCompatImageView info;
    private String course_name, module_name, description_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        initViews();
        setListner();

        courses_detail_list.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator animator = courses_detail_list.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    private void initViews() {
        realm = Realm.getDefaultInstance();
        toolbar = findViewById(R.id.exploreToolbar);
        //action_bar_title = (CustomFontTextView) findViewById(R.id.action_bar_title);
        info = findViewById(R.id.info);
        info.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getCourseDetails(getIntent().getStringExtra(KEY_COURSE_ID));
        courses_detail_list = findViewById(R.id.courses_detail_list);
    }

    private void setListner() {
        courseDetailsList = realm.where(CourseDetail.class).equalTo("courseId", getIntent().getStringExtra(KEY_COURSE_ID)).findAllAsync();
        courseDetailsList.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<CourseDetail>>() {
            @Override
            public void onChange(RealmResults<CourseDetail> courseDetails, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                if (courseDetails.size() > 0) {

                    if (courseDetails.get(0).getModuleDetail() != null && courseHeaders.size() <= 0)
                        for (ModuleDetail moduleDetail : courseDetails.get(0).getModuleDetail().sort("moduleWeight", Sort.ASCENDING)) {
                            courseHeaders.add(new CourseHeader(moduleDetail.getModuleName(), moduleDetail.getModuleId(), moduleDetail.getModuleId(), moduleDetail.getModuleItems().sort("itemWeight", Sort.ASCENDING)));
                        }
                    if (courseDetailsAdapter == null || courseDetailsAdapter.getItemCount() <= 0) {
                        courseDetailsAdapter = new CourseDetailsAdapter(CourseDetailsActivity.this, courseHeaders);
                        courseDetailsAdapter.setCourseModuleItemListener(CourseDetailsActivity.this);
                        courses_detail_list.setAdapter(courseDetailsAdapter);

                    }
                    if (!Utility.isEmpty(courseDetails.get(0).getCourseLongDesc()))
                        description_value = courseDetails.get(0).getCourseLongDesc();
                    if (!Utility.isEmpty(courseDetails.get(0).getCourseSubGroup()))
                        module_name = courseDetails.get(0).getCourseSubGroup();

                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog();
            }
        });
    }

    private String getToken() {
        String token = null;
        try {
            User user = realm.where(User.class).findFirst();
            if (user != null) {
                token = user.getToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        token = "JWT " + token;
        return token;
    }

    private void getTestStatus(final String item_type_id, final String item_name) {
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);

        CheckTestStatusModel checkTestStatusModel = new CheckTestStatusModel();
        checkTestStatusModel.setTopic_id(item_type_id);

        checkTestStatusCall = apiInterface.getTestStatus(getToken(), checkTestStatusModel);
        checkTestStatusCall.enqueue(new Callback<CheckTestStatus>() {
            @Override
            public void onResponse(Call<CheckTestStatus> call, Response<CheckTestStatus> response) {
                final CheckTestStatus mCheckTestStatus = response.body();
                if (mCheckTestStatus != null) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(mCheckTestStatus);
                        }
                    });
                    customDialog(item_type_id, item_name, mCheckTestStatus.getCompleted(), mCheckTestStatus.getRunning(), mCheckTestStatus.getUserAllowedToAcess());
                }
            }

            @Override
            public void onFailure(Call<CheckTestStatus> call, Throwable t) {
                System.out.println("onFailure");
                Utility.showMessage(t.getMessage());
            }
        });
    }

    private void getCourseDetails(String courseId) {
        //  courseId="oracle12c-sql";
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
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CourseDetail>> call, Throwable t) {
                Utility.showMessage(t.getMessage());
            }
        });
    }

    private void setFinishExamLister() {
        RealmResults<FinishUserExam> finishUserExam = realm.where(FinishUserExam.class).findAll();
        finishUserExam.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<FinishUserExam>>() {
            @Override
            public void onChange(RealmResults<FinishUserExam> finishUserExams, OrderedCollectionChangeSet changeSet) {
                if (finishUserExams.size() > 0) {
                    Intent mIntent = new Intent(CourseDetailsActivity.this, DisplayTestResultActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        });
    }

    private void getFinishExam(String item_type_id) {
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        FinishUserExamBody finishUserExamBody = new FinishUserExamBody();
        finishUserExamBody.setTestId(item_type_id);
        Call<ResponseBody> finishExamJson = apiInterface.getResult(getToken(), finishUserExamBody);
        finishExamJson.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final FinishUserExam[] mfinishUserExam = gson.fromJson(response.body().string(), FinishUserExam[].class);
                    if (mfinishUserExam.length > 0) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.insertOrUpdate(mfinishUserExam[0]);
                            }
                        });
                    }


                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utility.showMessage(t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        realm.close();
        coursesList.cancel();
        super.onDestroy();
    }


    private void buildDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.course_details_description_dialog);

        AppCompatTextView course_title = dialog.findViewById(R.id.course_name);
        AppCompatTextView course_module = dialog.findViewById(R.id.modue_name);
        MathJaxView description = dialog.findViewById(R.id.content);
        course_title.setText("Course : " + course_name);
        course_module.setText("Module :" + module_name);
        description.setInputText(description_value);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.getWindow().getAttributes().gravity = Gravity.RIGHT | Gravity.TOP;
        dialog.show();

    }

    private void customDialog(final String item_type_id, String module_name, boolean completed, boolean running, boolean allowedtoAccess) {
        // custom dialog
        final Dialog dialog = new Dialog(CourseDetailsActivity.this);
        dialog.setContentView(R.layout.custom_dialog);

        // set the custom dialog components - text, image and button
        TextView tv_module_name = dialog.findViewById(R.id.tv_module_name);
        tv_module_name.setText(module_name);
        final Button dialogContinue = dialog.findViewById(R.id.dialogContinue);
        Button dialogStartNew = dialog.findViewById(R.id.dialogStartNew);

        if (completed == false && running == true) {

        } else if (completed == true && running == false) {
            dialogContinue.setText("Finish");
        } else if (completed == false && running == false) {
            dialogContinue.setVisibility(View.GONE);
        }

        // if button is clicked, close the custom dialog
        dialogContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (dialogContinue.getText().toString().contains("finish")) {
                    dialog.dismiss();
                    getFinishExam(item_type_id);
                } else {
                    dialog.dismiss();
                    Intent mIntent = new Intent(CourseDetailsActivity.this, TestActivity1.class);
                    mIntent.putExtra("startNew", false);
                    mIntent.putExtra("item_type_id", item_type_id);
                    startActivity(mIntent);
                }
            }
        });


        // if button is clicked, close the custom dialog
        dialogStartNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent mIntent = new Intent(CourseDetailsActivity.this, TestActivity1.class);
                mIntent.putExtra("startNew", true);
                mIntent.putExtra("item_type_id", item_type_id);
                startActivity(mIntent);
            }
        });

        dialog.show();
    }

    @Override
    public void onModuleClickListener(ChildViewHolder holder, int position, ModuleItem data, CourseHeader courseHeader) {
        String item_type_id = "super-q-test-id";
        getTestStatus(item_type_id, data.getItemName());//data.getItemTypeId()

        /*Intent intent = new Intent(getIntent());
        //intent.setAction(PlayerActivity.ACTION_VIEW_LIST);
        intent.setComponent(new ComponentName(CourseDetailsActivity.this, VideoPlayerActivity.class));
        intent.putExtra(PlayerActivity.KEY_MODULE_ID, courseHeader.getModuleId());
        intent.putExtra(PlayerActivity.KEY_MODULE_ITEM_ID, data.getItemId());
        startActivity(intent);*/

    }
}
