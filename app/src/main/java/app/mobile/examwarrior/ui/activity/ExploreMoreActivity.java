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
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.explorer.ExploreCategoryAdapter;
import app.mobile.examwarrior.adapters.explorer.ExploreCategoryMoreAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreMoreActivity extends AppCompatActivity implements ExploreCardClickListener {

    public static final int moreVideoActivity = 101;
    private static final String TAG = "TAG";
    private RecyclerView listView;
    private ExploreCategoryMoreAdapter adapter;

    private Activity activity;

    private CircleProgressBar progressBar;
    private ArrayList<CourseMoreCategories> listOfPlayLists=new ArrayList<CourseMoreCategories>();

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

        listView = (RecyclerView) findViewById(R.id.allFeedsData);
        emptyRecords= (TextView) findViewById(R.id.emptyRecords);
        backButton=(ImageView )findViewById(R.id.imageview_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(linearLayoutManager);

        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);


        adapter = new ExploreCategoryMoreAdapter(activity, listOfPlayLists);

        listView.setAdapter(adapter);
        listView.setNestedScrollingEnabled(true);
        listView.startNestedScroll(RecyclerView.HORIZONTAL);

        progressBar.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        loadExploreData(categoryId);
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
        Call<List<CourseMoreCategories>> courseCategoriesCall = apiInterface.getExlporeMoreData(categoryId);
        courseCategoriesCall.enqueue(new Callback<List<CourseMoreCategories>>() {

            @Override
            public void onResponse(Call<List<CourseMoreCategories>> call, Response<List<CourseMoreCategories>> response) {if (response.isSuccessful()) {
                    listOfPlayLists.clear();
                    listOfPlayLists.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    if(response.body().isEmpty()){
                        emptyRecords.setVisibility(View.VISIBLE);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<CourseMoreCategories>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void getMoreCources(String playListId) {
        Utility.showMessage("Yet to implement");
    }

    @Override
    public void exploreCources(CourseCategories.McoursesBean itemsEntity) {
        Utility.showMessage("Yet to implement");
    }

    @Override
    public void exploreCourcesMore(CourseMoreCategories.CoursesBean itemsEntity) {

    }
}
