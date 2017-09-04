package app.mobile.examwarrior.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.FinishUserExam;
import app.mobile.examwarrior.database.FinishUserExamBody;
import app.mobile.examwarrior.database.ResponseSaveQuestionData;
import app.mobile.examwarrior.database.SaveUserExamQuestionData;
import app.mobile.examwarrior.database.StartUserExam;
import app.mobile.examwarrior.listener.UserAnswers;
import app.mobile.examwarrior.model.RealmString;
import app.mobile.examwarrior.model.RealmStringDeserializer;
import app.mobile.examwarrior.model.StartTestBody;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.sync.SaveAnswersData;
import app.mobile.examwarrior.sync.SyncTag;
import app.mobile.examwarrior.ui.fragments.DisplayQuestionFragment;
import app.mobile.examwarrior.ui.fragments.OneFragment;
import app.mobile.examwarrior.ui.fragments.TwoFragment;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.LockableViewPager;
import app.mobile.examwarrior.widget.PlayPauseButton;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity1 extends AppCompatActivity implements OneFragment.GetPosition, TwoFragment.GetPositionTwo {

    public static final String TAG = TestActivity1.class.getSimpleName();
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
    public Runnable updatePauseTotalTimerThread = new Runnable() {
        @Override
        public void run() {


        }
    };
    String path = "file:///android_asset/";
    long startTime = 0L;
    long updatedTime = 0L;
    long timeSwapBuff = 0L;
    long timeInMilliseconds = 0L;
    long startTotalTime = 0L;
    long updatedTotalTime = 0L;
    long timeTotalSwapBuff = 0L;
    long timeTotalInMilliseconds = 0L;
    DrawerLayout drawer;
    long Qsecs;
    String item_type_id = "";
    ViewPager viewPager;
    StartUserExam userExam;
    int current_position = 0;
    private Call<ResponseSaveQuestionData> mResponseSaveQuestionDataCall;
    private android.support.v4.app.FragmentManager fragmentManager;
    private LockableViewPager mPager;
    private DemoFragmentAdapter mAdapter;
    private Realm realm;
    private TextView tvTotalTime;
    private TextView tvAnsTime;
    private ToggleButton tbtn_review;
    private Handler customHandler = new Handler();
    public Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            Qsecs = secs;
            int mins = secs / 60;
            secs = secs % 60;
            int hour = mins / 60;
            mins = mins % 60;
            //int milliseconds = (int) (updatedTime % 1000);
            tvAnsTime.setText(String.format("%02d", hour) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };
    private Handler pauseHandlerTotal = new Handler();
    private Handler customHandlerTotal = new Handler();
    public Runnable updateTotalTimerThread = new Runnable() {

        public void run() {


            timeTotalInMilliseconds = SystemClock.uptimeMillis() - startTotalTime;
            updatedTotalTime = timeTotalInMilliseconds - timeTotalSwapBuff;

            System.out.println("step startTotalTime :" + startTotalTime);
            System.out.println("step SystemClock.uptimeMillis() :" + SystemClock.uptimeMillis());
            System.out.println("step timeInMilliseconds :" + timeTotalInMilliseconds);
            System.out.println("step timeSwapBuff :" + timeTotalSwapBuff);
            System.out.println("step updatedTime :" + updatedTotalTime);

            int secs = (int) (updatedTotalTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hour = mins / 60;
            mins = mins % 60;
            //int milliseconds = (int) (updatedTime % 1000);
            tvTotalTime.setText(String.format("%02d", hour) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandlerTotal.postDelayed(this, 0);
        }
    };
    private TabLayout tabLayout;
    private boolean finish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        //   que = new String[]{"\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]", "\\Δ FEC≅ \\ΔGBD", "a{x^34} + 87x + 9 = 0", "a{x^2} + 87x + 9 = 0", "a{x^31} + 87x + 9 = 0", "a{x^42} + 87x + 9 = 0", "y-y_0=m(x-x_0)", "\\cos^2θ+\\sin^2θ=1", "∑↙{i=0}↖n i={n(n+1)}/2", "{1+√5}/2=1+1/{1+1/{1+⋯}}", "f'(x)=\\lim↙{h→0}{f(x+h)-f(x)}/h", "\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]", "\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]", "(\\table \\cos θ, - \\sin θ; \\sin θ, \\cos θ)$ gives a rotation by", "$v↖{→}⋅w↖{→} = vw\\cos θ$", "$\\{x:x^2∈\\ℚ\\}$ has measure 0 in $\\ℝ$.", "\\[1/7 = 0.\\ov 142857\\]", "\\[√^n{a}√^n{b} = √^n{ab}\\]", "\\[\\table a, =, b+c; , =, d\\]", "\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]"};
        initViews();
        setListener();
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            final boolean startNew = mBundle.getBoolean("startNew");
            item_type_id = mBundle.getString("item_type_id");

            if (Utility.isNetworkAvailable()) {
                initStartUserExamAPI(startNew);
            } else {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (startNew) {
                            realm.delete(SaveUserExamQuestionData.class);
                        }
                        RealmResults<StartUserExam> mStartUserExams = realm.where(StartUserExam.class).findAll();
                        userExam = mStartUserExams.get(0);
                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        mPager.setAdapter(mAdapter);
                    }
                });

            }
        }

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        startTotalTime = SystemClock.uptimeMillis();
        customHandlerTotal.postDelayed(updateTotalTimerThread, 0);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        OneFragment mFragment1 = new OneFragment();
        mFragment1.setContext(this);
        TwoFragment mFragment2 = new TwoFragment();
        mFragment2.setContext(this);
        adapter.addFrag(mFragment1, "ListView");
        adapter.addFrag(mFragment2, "GridView");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void getData(int i) {

        mPager.setCurrentItem(i);
        drawer.closeDrawers();
    }

    @Override
    public void getDataTwo(int i) {
        mPager.setCurrentItem(i);
        drawer.closeDrawers();
    }

    private void setListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("viewpager onPageScrolled :" + position);
            }

            @Override
            public void onPageSelected(int position) {


                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                if (current_position > position) {
                    if (Utility.isNetworkAvailable()) {
                        UpdateQuestionData(position + 1);
                    } else {
                        insertData(null, position + 1);
                    }
                } else if (current_position < position) {
                    if (Utility.isNetworkAvailable()) {
                        UpdateQuestionData(position - 1);
                    } else {
                        insertData(null, position - 1);
                    }
                } else {
                    if (Utility.isNetworkAvailable()) {
                        UpdateQuestionData(position);
                    } else {
                        insertData(null, position);
                    }
                }
                current_position = position;
                System.out.println("viewpager onPageSelected :" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        /*findViewById(R.id.left_nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateQuestionData();
                mPager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        findViewById(R.id.right_nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateQuestionData();
                mPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });*/

        findViewById(R.id.imgDrawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
                if (userExam != null) {
                    UpdateQuestionData(current_position);
                    finish = true;
                }
            }
        });
        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("next :" + getItem(+1));
                int pos = getItem(+1);
                mPager.setCurrentItem(pos, true);

                if (pos != 20) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }

            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = getItem(-1);
                mPager.setCurrentItem(pos, true);
                if (pos != -1) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }
            }
        });


        setStartExamListner();
        PlayPauseButton playPauseButton = findViewById(R.id.main_play_pause_button);
        playPauseButton.setOnControlStatusChangeListener(new PlayPauseButton.OnControlStatusChangeListener() {
            @Override
            public void onStatusChange(View view, boolean state) {
                if (state) {
                    try {
                        timeTotalSwapBuff = updatedTotalTime;
                        customHandlerTotal.postDelayed(updateTotalTimerThread, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        long pause = SystemClock.uptimeMillis();
                        customHandlerTotal.removeCallbacks(updateTotalTimerThread);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void getAnswerDatabase() {
        RealmResults<SaveUserExamQuestionData> results = realm.where(SaveUserExamQuestionData.class).findAll();
        if (results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {

                if (results.get(i).getQuestionId().equalsIgnoreCase(userExam.getAllQuestions().get(current_position).getQuestionId())) {

                    try {
                        JSONObject mJsonObject = new JSONObject(results.get(i).getQuestionData());
                        String markForReview = mJsonObject.getString("markForReview");
                        String timeSpent = mJsonObject.getString("timeSpent");
                        tvAnsTime.setText(timeSpent);
                        tbtn_review.setChecked(Boolean.parseBoolean(markForReview));
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
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

 /*   private String getString(String path, String equation) {
        final String js = "<html><head>"
                + "<link rel='stylesheet' href='" + path + "jqmath-0.4.3.css'>"
                + "<script src='" + path + "jquery-1.4.3.min.js'></script>"
                + "<script src='" + path + "jqmath-etc-0.4.6.min.js'></script>"
                + "</head>Find the value of <body>"
                + "<script>var s ='$$" + equation + "$$';M.parseMath(s);document.write(s);</script></body>";
        return js;
    }*/

    private void initViews() {
        mPager = findViewById(R.id.pager);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvAnsTime = findViewById(R.id.tvAnsTime);
        tbtn_review = findViewById(R.id.tbtn_review);
        drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        realm = Realm.getDefaultInstance();
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        mAdapter = new DemoFragmentAdapter(getSupportFragmentManager());

    }

    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }

    private SaveUserExamQuestionData getAnswerBody(int position) {
        SaveUserExamQuestionData examQuestionData = new SaveUserExamQuestionData();
        JSONObject mJsonObject = new JSONObject();

        UserAnswers mAnswers = realm.where(UserAnswers.class).equalTo("position", position).findFirst();
        String strOpt1 = "", strOpt2 = "", strOpt3 = "", strOpt4 = "";
        if (mAnswers.getStrOption1() != null) {
            strOpt1 = mAnswers.getStrOption1();
        }
        if (mAnswers.getStrOption2() != null) {
            strOpt2 = mAnswers.getStrOption2();
        }
        if (mAnswers.getStrOption3() != null) {
            strOpt3 = mAnswers.getStrOption3();
        }
        if (mAnswers.getStrOption4() != null) {
            strOpt4 = mAnswers.getStrOption4();
        }

        RealmResults<StartUserExam> startUserExams = realm.where(StartUserExam.class).findAllAsync();
        final String queID = startUserExams.get(0).getAllQuestions().get(position).getQuestionId();

        try {

            boolean isSkipped = false;
            if (strOpt1.equals("") && strOpt2.equals("") && strOpt3.equals("") && strOpt4.equals("")) {
                isSkipped = true;
            }
            mJsonObject.put("isSkipped", isSkipped);
            mJsonObject.put("markForReview", tbtn_review.isChecked());

            RealmResults<SaveUserExamQuestionData> examQuestionData1 = realm.where(SaveUserExamQuestionData.class).equalTo("questionId", queID).findAll();
            if (examQuestionData1 != null && examQuestionData1.size() > 0) {
                try {
                    JSONObject mJsonObject1 = new JSONObject(examQuestionData1.get(0).getQuestionData());
                    String timeSpent = mJsonObject1.getString("timeSpent");
                    Qsecs = Qsecs + Long.parseLong(timeSpent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mJsonObject.put("timeSpent", Qsecs);
            JSONArray mJsonArray = new JSONArray();
            if (!strOpt1.equals("")) {
                strOpt1 = startUserExams.get(0).getAllQuestions().get(position).getOptions().get(0).getId();
                mJsonArray.put(strOpt1);
            }
            if (!strOpt2.equals("")) {
                strOpt2 = startUserExams.get(0).getAllQuestions().get(position).getOptions().get(1).getId();
                mJsonArray.put(strOpt2);
            }
            if (!strOpt3.equals("")) {
                strOpt3 = startUserExams.get(0).getAllQuestions().get(position).getOptions().get(2).getId();
                mJsonArray.put(strOpt3);
            }
            if (!strOpt4.equals("")) {
                strOpt4 = startUserExams.get(0).getAllQuestions().get(position).getOptions().get(3).getId();
                mJsonArray.put(strOpt4);
            }
            mJsonObject.put("userAnswers", mJsonArray);

            examQuestionData.setApp_table_pk();
            examQuestionData.setQuestionData(mJsonObject.toString());
            examQuestionData.setQuestionId(queID);
            examQuestionData.setApp_update_dt(String.valueOf(System.currentTimeMillis()));
            examQuestionData.setTopicId(userExam.getTestId());
            //   examQuestionData.setTokenId(getToken());
            //  User user = realm.where(User.class).findFirst();
            //  examQuestionData.setUserId(user.getUsrId());
            return examQuestionData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertData(SaveUserExamQuestionData examQuestionData, int pos) {
        try {
            if (examQuestionData == null) {
                examQuestionData = getAnswerBody(pos);
            }
            final SaveUserExamQuestionData finalExamQuestionData = examQuestionData;
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(finalExamQuestionData);
                    if (finish) {
                        finish = false;
                        getFinishExam();
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    tbtn_review.setChecked(false);
                    //  mEditor.clear();
                    enqueueJob("ew_" + SaveUserExamQuestionData.class.getSimpleName(), "app_table_pk");
                    getAnswerDatabase();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Log.v(TAG, "Realm error : " + error.getMessage().toString());
                    Toast.makeText(TestActivity1.this, "" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void UpdateQuestionData(final int position) {
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        final SaveUserExamQuestionData examQuestionData = getAnswerBody(position);
        mResponseSaveQuestionDataCall = apiInterface.updateQuestionData(getToken(), examQuestionData);
        mResponseSaveQuestionDataCall.enqueue(new Callback<ResponseSaveQuestionData>() {
            @Override
            public void onResponse(Call<ResponseSaveQuestionData> call, Response<ResponseSaveQuestionData> response) {
                final ResponseSaveQuestionData mResponseSaveQuestionData = response.body();
                if (mResponseSaveQuestionData != null) {
                    System.out.println("questionId :" + mResponseSaveQuestionData.getQuestionId());
                    System.out.println("status :" + mResponseSaveQuestionData.getStatus());
                    insertData(examQuestionData, -1);
                }
            }

            @Override
            public void onFailure(Call<ResponseSaveQuestionData> call, Throwable t) {
                System.out.println("onFailure");
                Utility.showMessage(t.getMessage());
            }
        });
    }

    private void enqueueJob(String table_name, String _id) {
        PersistableBundleCompat persistableBundleCompat = new PersistableBundleCompat();
        persistableBundleCompat.putString(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME, table_name.toLowerCase());
        persistableBundleCompat.putString(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID, _id);
        //  persistableBundleCompat.putString(SyncTag.BUNDLE_KEYS.KEY_USR_ID, user_id);
        // persistableBundleCompat.putString(SyncTag.BUNDLE_KEYS.KEY_TIME, time);
        SaveAnswersData.scheduleJob(persistableBundleCompat);
    }

    private void setStartExamListner() {
        RealmResults<StartUserExam> startUserExams = realm.where(StartUserExam.class).findAll();
        startUserExams.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<StartUserExam>>() {
            @Override
            public void onChange(RealmResults<StartUserExam> startUserExams, OrderedCollectionChangeSet changeSet) {
                if (startUserExams.size() > 0) {
                    userExam = startUserExams.get(0);
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    mPager.setAdapter(mAdapter);
                }
            }
        });
    }

    private void initStartUserExamAPI(final boolean startNew) {
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        StartTestBody mStartTestBody = new StartTestBody();
        mStartTestBody.setTest_id(item_type_id); //"super-super-test"
        mStartTestBody.setShouldStartNew(startNew);
        Call<List<StartUserExam>> StartTestDetails = apiInterface.getQuestionsList(getToken(), mStartTestBody);
        StartTestDetails.enqueue(new Callback<List<StartUserExam>>() {
            @Override
            public void onResponse(Call<List<StartUserExam>> call, Response<List<StartUserExam>> response) {
                final List<StartUserExam> userExamList = response.body();
                if (userExamList != null && !userExamList.isEmpty()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            realm.delete(StartUserExam.class);
                            realm.insertOrUpdate(userExamList.get(0));
                            if (startNew) {
                                realm.delete(SaveUserExamQuestionData.class);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<StartUserExam>> call, Throwable t) {
                System.out.println("onFailure");
                Utility.showMessage(t.getMessage());
            }
        });
    }

    private void getFinishExam() {
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


                                realm.delete(FinishUserExam.class);
                                realm.delete(SaveUserExamQuestionData.class);
                                for (int i = 0; i < mfinishUserExam.length; i++) {
                                    realm.insertOrUpdate(mfinishUserExam[i]);
                                }
                                Intent mIntent = new Intent(TestActivity1.this, DisplayTestResultActivity.class);
                                startActivity(mIntent);
                                finish();
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

  /*  @Override
    public void setAnswers(String key, String value) {
        if (key.equalsIgnoreCase("strOpt1")) {
            strOpt1 = value;
        } else if (key.equalsIgnoreCase("strOpt2")) {
            strOpt2 = value;
        } else if (key.equalsIgnoreCase("strOpt3")) {
            strOpt3 = value;
        } else if (key.equalsIgnoreCase("strOpt4")) {
            strOpt4 = value;
        }
    }*/


  /*  private void setFinishExamLister()
    {
      RealmResults<FinishUserExam> finishUserExam=  realm.where(FinishUserExam.class).findAll();
        finishUserExam.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<FinishUserExam>>() {
            @Override
            public void onChange(RealmResults<FinishUserExam> finishUserExams, OrderedCollectionChangeSet changeSet) {
                if(finishUserExams.size()>0)
                {
                    Intent mIntent=new Intent(TestActivity1.this,DisplayTestResultActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        });
    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private class DemoFragmentAdapter extends FragmentPagerAdapter {

        private int position = 0;

        public DemoFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public Fragment getItem(int position) {

            DisplayQuestionFragment fragment = DisplayQuestionFragment.newInstance(userExam.getAllQuestions().get(position));
            fragment.setPosition(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return userExam.getTotalQuestions();
        }
    }

}
