package app.mobile.examwarrior.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.explorer.ExploreCategoryMoreAdapter;
import app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseMore;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.MoreTutorsResponse;
import app.mobile.examwarrior.model.Tutors;
import app.mobile.examwarrior.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.mobile.examwarrior.ui.activity.ExploreSubCategoryListActivity.BOOKS_CATEGORY;

public class ExploreMoreActivity extends AppCompatActivity implements ExploreCardClickListener {

    public static final int moreVideoActivity = 101;
    private static final String KEY_CATEGORY = "app.mobile.examwarrior.CATEGORY";
    private static final String KEY_CATEGORY_ID = "app.mobile.examwarrior.CATEGORY_ID";
    private static final String TUTOR_CATEGORY = "Tutors";
    private static final String TAG = "TAG";
    private RecyclerView listView;
    private ExploreCategoryMoreAdapter adapter;
    private int itemType = 3;
    private Activity activity;

    private CircleProgressBar progressBar;
    private ArrayList<Object> listOfPlayLists = new ArrayList<Object>();
    private String course_category = null;
    private String course_category_id = null;
    private ImageView backButton;
    private TextView emptyRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(toolbar);

        activity = this;


        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("CategoryId");

        if (intent != null) {
            course_category = intent.getStringExtra(KEY_CATEGORY);
            course_category_id = intent.getStringExtra(KEY_CATEGORY_ID);
        }
        listView = (RecyclerView) findViewById(R.id.allFeedsData);
        emptyRecords = (TextView) findViewById(R.id.emptyRecords);
        backButton = (ImageView) findViewById(R.id.imageview_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(linearLayoutManager);

        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);
        switch (course_category) {
            case TUTOR_CATEGORY:
                itemType = ExploreSubCatListAdapter.ITEM_TUTOR;
                loadTutorData(categoryId);
                break;
            case BOOKS_CATEGORY:
                itemType = ExploreSubCatListAdapter.ITEM_BOOK;

                break;
            default:
                itemType = ExploreSubCatListAdapter.ITEM_COURSES;
                loadExploreData(categoryId);
                break;

        }


        adapter = new ExploreCategoryMoreAdapter(activity, listOfPlayLists, itemType);

        listView.setAdapter(adapter);
        listView.setNestedScrollingEnabled(true);
        listView.startNestedScroll(RecyclerView.HORIZONTAL);

        progressBar.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadExploreData(String categoryId) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }

        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<CourseMore> courseCategoriesCall = apiInterface.getExlporeMoreData(categoryId);
        courseCategoriesCall.enqueue(new Callback<CourseMore>() {

            @Override
            public void onResponse(Call<CourseMore> call, Response<CourseMore> response) {
                if (response.isSuccessful()) {
                    listOfPlayLists.clear();
                    listOfPlayLists.addAll(response.body().getMcourses());
                    adapter.notifyDataSetChanged();
                    if (response.body().getMcourses().isEmpty()) {
                        emptyRecords.setVisibility(View.VISIBLE);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CourseMore> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void loadTutorData(String categoryId) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }

        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<MoreTutorsResponse> courseCategoriesCall = apiInterface.getExploreMoreTutorData(categoryId);
        courseCategoriesCall.enqueue(new Callback<MoreTutorsResponse>() {

            @Override
            public void onResponse(Call<MoreTutorsResponse> call, Response<MoreTutorsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listOfPlayLists.clear();
                    listOfPlayLists.addAll(response.body().getMcourses());
                    adapter.notifyDataSetChanged();
                    if (response.body().getMcourses().isEmpty()) {
                        emptyRecords.setVisibility(View.VISIBLE);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MoreTutorsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    //No need to implement them here
    @Override
    public void getMoreCources(int position, String playListId) {
        Utility.showMessage("Yet to implement");
    }

    @Override
    public void exploreCources(int position, CourseCategories.McoursesBean itemsEntity) {

    }

    //No need to implement them here


    @Override
    public void exploreCourcesMore(CourseMoreCategories.CoursesBean itemsEntity) {
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        intent.putExtra(CourseDetailsActivity.KEY_COURSE_ID, itemsEntity.getCourseId());
        startActivity(intent);
    }

    @Override
    public void onTutorItemClicked(int position, Tutors tutors) {

    }


}
