package app.mobile.examwarrior.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.explorer.CarouselItem;
import app.mobile.examwarrior.adapters.explorer.ExploreCarouselCardItem;
import app.mobile.examwarrior.adapters.explorer.ExploreCategoryAdapter;
import app.mobile.examwarrior.adapters.explorer.ExploreHeaderItem;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.FinishExamRequestBody;
import app.mobile.examwarrior.model.Questions;
import app.mobile.examwarrior.model.RealmString;
import app.mobile.examwarrior.model.RealmStringDeserializer;
import app.mobile.examwarrior.model.Tutors;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.util.decoration.CarouselItemDecoration;
import app.mobile.examwarrior.util.decoration.HeaderItemDecoration;
import app.mobile.examwarrior.util.decoration.RecyclerViewDecoration;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity implements ExploreCardClickListener {

    public static final int moreVideoActivity = 101;
    public static final String TAG = ExploreActivity.class.getSimpleName();
    private RecyclerView listView;
    private ExploreCategoryAdapter adapter;

    private int color;
    private int betweenPadding;

    private GroupAdapter<ViewHolder> groupAdapter;
    private Activity activity;

    private CircleProgressBar progressBar;
    private ArrayList<CourseCategories> listOfPlayLists = new ArrayList<CourseCategories>();

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(toolbar);
        color = ContextCompat.getColor(this, R.color.white);
        betweenPadding = getResources().getDimensionPixelSize(R.dimen.padding_small);
        activity = this;
        listView = (RecyclerView) findViewById(R.id.allFeedsData);
        listView.addItemDecoration(new RecyclerViewDecoration(5));
        backButton = (ImageView) findViewById(R.id.imageview_back);
        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(linearLayoutManager);
        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);
        adapter = new ExploreCategoryAdapter(activity, listOfPlayLists);
        listView.setAdapter(adapter);
        listView.setNestedScrollingEnabled(true);
        listView.startNestedScroll(RecyclerView.HORIZONTAL);

        progressBar.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        loadExploreData();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void addGroups() {
        groupAdapter = new GroupAdapter<>();
        groupAdapter.setSpanCount(12);

        GridLayoutManager layoutManager = new GridLayoutManager(this, groupAdapter.getSpanCount());
        layoutManager.setSpanSizeLookup(groupAdapter.getSpanSizeLookup());

        listView.setLayoutManager(layoutManager);

        listView.addItemDecoration(new HeaderItemDecoration(Color.TRANSPARENT, betweenPadding));

        listView.setAdapter(groupAdapter);

        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();

        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
        bindListToAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadExploreData() {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        final Call<List<CourseCategories>> courseCategoriesCall = apiInterface.getExlporeData();
        courseCategoriesCall.enqueue(new Callback<List<CourseCategories>>() {

            @Override
            public void onResponse(Call<List<CourseCategories>> call, retrofit2.Response<List<CourseCategories>> response) {
                if (response.isSuccessful()) {
                    listOfPlayLists.clear();
                    listOfPlayLists.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<CourseCategories>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void finishExam() {

        /*String json = null;
        try {
            InputStream inputStream = getAssets().open("questions/Questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }*/

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
       /* Realm realm = Realm.getDefaultInstance();

        try {
            final Questions[] main = gson.fromJson(json, Questions[].class);
            Log.e(TAG, "onCreate: " + main[0].getList().get(0));
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Questions> data = realm.where(Questions.class).findAll();
                    if (data != null && !data.isEmpty()) {
                        for (RealmString questions : data.get(0).getList()) {
                            Log.e(TAG, "execute: " + questions);
                        }
                    } else {
                        for (Questions questions : main) {
                            realm.insertOrUpdate(questions);
                            Log.e(TAG, "execute: inserted ");
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        } finally {
            realm.close();
        }*/
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        final Call<ResponseBody> courseCategoriesCall = apiInterface.finishExam("JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJrdW5qYW4iLCJpYXQiOjE1MDM2NTcyMDEsImV4cCI6MTUwNDI2MjAwMX0.BYN0pJbMg_AckioJrzzGUw-T6711QgzSM1ltF4B4XJo", new FinishExamRequestBody("super-q-test-id"));
        courseCategoriesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final Questions[] main = gson.fromJson(response.body().string(), Questions[].class);
                    for (int i = 0; i < main.length; i++) {
                        Log.e(TAG, "onResponse: " + main[i].getTestName());
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void getMoreCources(int position, String playListId) {
        Intent exploreMore = new Intent(this, ExploreMoreActivity.class);
        if (listOfPlayLists.get(position) != null) {
            exploreMore.putExtra(ExploreSubCategoryListActivity.KEY_CATEGORY, listOfPlayLists.get(position).getCourse_cat());
            exploreMore.putExtra(ExploreSubCategoryListActivity.KEY_CATEGORY_ID, listOfPlayLists.get(position).getCourse_cat_id());
        }
        exploreMore.putExtra("CategoryId", playListId);
        startActivity(exploreMore);

    }

    @Override
    public void exploreCources(int position, CourseCategories.McoursesBean itemsEntity) {
        Intent exploreMore = new Intent(this, ExploreSubCategoryListActivity.class);
        if (listOfPlayLists.get(position) != null) {
            exploreMore.putExtra(ExploreSubCategoryListActivity.KEY_CATEGORY, listOfPlayLists.get(position).getCourse_cat());
            exploreMore.putExtra(ExploreSubCategoryListActivity.KEY_CATEGORY_ID, listOfPlayLists.get(position).getCourse_cat_id());
        }
        exploreMore.putExtra("CategoryId", itemsEntity);
        startActivity(exploreMore);
    }

    @Override
    public void exploreCourcesMore(CourseMoreCategories.CoursesBean itemsEntity) {

    }

    @Override
    public void onTutorItemClicked(int position, Tutors tutors) {

    }


    private void bindListToAdapter() {

        Section carouselSection = new Section(new ExploreHeaderItem("Title", "No"));
        CarouselItem carouselItem = makeCarouselItem();
        carouselSection.add(carouselItem);
        groupAdapter.add(carouselSection);
    }

    private CarouselItem makeCarouselItem() {

        CarouselItemDecoration carouselDecoration = new CarouselItemDecoration(Color.TRANSPARENT, betweenPadding);
        GroupAdapter<ViewHolder> carouselAdapter = new GroupAdapter<>();
        for (int i = 0; i < 30; i++) {
            carouselAdapter.add(new ExploreCarouselCardItem(0));
        }
        CarouselItem carouselItem = new CarouselItem(carouselDecoration);
        carouselItem.setAdapter(carouselAdapter);
        return carouselItem;
    }

}
