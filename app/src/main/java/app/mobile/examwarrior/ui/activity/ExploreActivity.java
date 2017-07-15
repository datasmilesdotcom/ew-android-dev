package app.mobile.examwarrior.ui.activity;

import android.app.Activity;
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
import app.mobile.examwarrior.adapters.explorer.MultiplePlayListVideoRecycleViewAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;

public class ExploreActivity extends AppCompatActivity implements ExploreCardClickListener {

    public static final int moreVideoActivity = 101;
    private static final String TAG = "TAG";
    private RecyclerView listView;
    private MultiplePlayListVideoRecycleViewAdapter adapter;

    private Activity activity;

    private CircleProgressBar progressBar;
    private ArrayList<CourseCategories> listOfPlayLists=new ArrayList<CourseCategories>();

    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(toolbar);

        activity = this;


        listView = (RecyclerView) findViewById(R.id.allFeedsData);
        backButton=(ImageView )findViewById(R.id.imageview_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(linearLayoutManager);

        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);


        adapter = new MultiplePlayListVideoRecycleViewAdapter(activity, listOfPlayLists);

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


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadExploreData() {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }

        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<List<CourseCategories>> courseCategoriesCall = apiInterface.getExlporeData();
        courseCategoriesCall.enqueue(new Callback<List<CourseCategories>>() {

            @Override
            public void onResponse(Call<List<CourseCategories>> call, retrofit2.Response<List<CourseCategories>> response) {if (response.isSuccessful()) {
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


    @Override
    public void getMoreCources(String playListId) {
        Utility.showMessage("Yet to implement");
    }

    @Override
    public void exploreCources(CourseCategories.McoursesBean itemsEntity) {
        Utility.showMessage("Yet to implement");
    }
}
