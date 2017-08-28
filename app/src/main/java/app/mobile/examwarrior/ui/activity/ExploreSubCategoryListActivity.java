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

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.Tutors;
import app.mobile.examwarrior.model.TutorsResponse;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.util.decoration.RecyclerViewDecoration;
import app.mobile.examwarrior.widget.CustomFontTextView;
import retrofit2.Call;
import retrofit2.Callback;

public class ExploreSubCategoryListActivity extends AppCompatActivity implements ExploreCardClickListener {

    public static final String KEY_CATEGORY = "app.mobile.examwarrior.CATEGORY";
    public static final String KEY_CATEGORY_ID = "app.mobile.examwarrior.CATEGORY_ID";
    public static final String TUTOR_CATEGORY = "Tutors";
    public static final String BOOKS_CATEGORY = "books";
    private RecyclerView listView;
    private ExploreSubCatListAdapter adapter;
    private Activity activity;
    private CircleProgressBar progressBar;
    private ArrayList<Object> listOfPlayLists = new ArrayList<>();
    private ImageView backButton;
    private CustomFontTextView action_bar_title;
    private String course_category = null;
    private String course_category_id = null;
    private int itemType = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_sub_category_list_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(toolbar);
        action_bar_title = (CustomFontTextView) findViewById(R.id.action_bar_title);
        activity = this;

        Intent intent = getIntent();
        if (intent != null) {
            course_category = intent.getStringExtra(KEY_CATEGORY);
            course_category_id = intent.getStringExtra(KEY_CATEGORY_ID);
        }
        CourseCategories.McoursesBean subcatogry = (CourseCategories.McoursesBean) intent.getSerializableExtra("CategoryId");
        action_bar_title.setText(subcatogry.getMcoursename().toUpperCase());
        listView = (RecyclerView) findViewById(R.id.sub_category_list);
        backButton = (ImageView) findViewById(R.id.imageview_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(linearLayoutManager);

        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);

        switch (course_category) {
            case TUTOR_CATEGORY:
                itemType = ExploreSubCatListAdapter.ITEM_TUTOR;
                loadTutorData(course_category_id, subcatogry.getMcourseid());
                break;
            case BOOKS_CATEGORY:
                itemType = ExploreSubCatListAdapter.ITEM_BOOK;

                break;
            default:
                itemType = ExploreSubCatListAdapter.ITEM_COURSES;
                loadExploreData(course_category_id, subcatogry.getMcourseid());
                break;

        }
        adapter = new ExploreSubCatListAdapter(activity, listOfPlayLists, itemType);
        listView.addItemDecoration(new RecyclerViewDecoration(6));
        listView.setAdapter(adapter);


        progressBar.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void loadExploreData(String catId, String subCatId) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }

        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<CourseMoreCategories> courseCategoriesCall = apiInterface.getExlporeSubCategoryData(catId, subCatId);
        courseCategoriesCall.enqueue(new Callback<CourseMoreCategories>() {

            @Override
            public void onResponse(Call<CourseMoreCategories> call, retrofit2.Response<CourseMoreCategories> response) {
                if (response.isSuccessful()) {
                    listOfPlayLists.clear();
                    listOfPlayLists.addAll(response.body().getCourses());
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CourseMoreCategories> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void loadTutorData(String catId, String subCatId) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }

        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<List<TutorsResponse>> courseCategoriesCall = apiInterface.getTutorData(catId, subCatId);
        courseCategoriesCall.enqueue(new Callback<List<TutorsResponse>>() {

            @Override
            public void onResponse(Call<List<TutorsResponse>> call, retrofit2.Response<List<TutorsResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    listOfPlayLists.clear();
                    for (TutorsResponse tutors : response.body()) {
                        listOfPlayLists.addAll(tutors.getTutors());
                    }

                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<TutorsResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void getMoreCources(int position, String courseId) {

    }

    @Override
    public void exploreCources(int position, CourseCategories.McoursesBean itemsEntity) {

    }


    @Override
    public void exploreCourcesMore(CourseMoreCategories.CoursesBean itemsEntity) {
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        intent.putExtra(CourseDetailsActivity.KEY_COURSE_ID, itemsEntity.getCourseId());
        startActivity(intent);
    }

    @Override
    public void onTutorItemClicked(int position, Tutors tutors) {
        Utility.showMessage(tutors.getFst_nm());
    }
}
