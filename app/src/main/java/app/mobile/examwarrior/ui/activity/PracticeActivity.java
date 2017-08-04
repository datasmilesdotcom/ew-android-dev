package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.questions.BannerPagerAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.AllQuestionsBean;
import app.mobile.examwarrior.database.Question;
import app.mobile.examwarrior.listener.NextClicklistener;
import app.mobile.examwarrior.model.QuestionRequestBody;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.widgets.SlidingTabLayout;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;

public class PracticeActivity extends AppCompatActivity implements NextClicklistener {

    private ViewPager viewPager;
    private SlidingTabLayout homeTabs;
    private BannerPagerAdapter bannerPagerAdapter;
    ArrayList<AllQuestionsBean> arraylist = new ArrayList<>();
    private CircleProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        homeTabs = (SlidingTabLayout) findViewById(R.id.home_tab_layout);

        viewPager = (ViewPager) findViewById(R.id.pager);
        bannerPagerAdapter = new BannerPagerAdapter(this, arraylist, this);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        homeTabs.setViewPager(viewPager);
        progressBar = (CircleProgressBar) findViewById(R.id.initial_feed_progress);
        progressBar.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        loadQuestions();
    }

    public void loadQuestions() {

        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);

        QuestionRequestBody questionRequestBody= new QuestionRequestBody();
        questionRequestBody.setShouldFetchAll(true);
        questionRequestBody.setTopic_id("sample-test-1");

        Call<Question> courseCategoriesCall = apiInterface.getQuestions(getToken(),questionRequestBody);
        courseCategoriesCall.enqueue(new Callback<Question>() {

            @Override
            public void onResponse(Call<Question> call, retrofit2.Response<Question> response) {
                if (response.isSuccessful()) {
                    arraylist.clear();
                    arraylist.addAll(response.body().getAllQuestions());
                    bannerPagerAdapter.notifyDataSetChanged();
                    homeTabs.setViewPager(viewPager);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onNextClick(int position) {
        if (arraylist.size() > position + 1)
            viewPager.setCurrentItem(position + 1);

    }

    @Override
    public void onBackClick(int position) {
        if (position >= 0)
            viewPager.setCurrentItem(position - 1);
    }


    private String getToken() {
        String token = null;
        Realm realm = Realm.getDefaultInstance();
        try {
            User user = realm.where(User.class).findFirst();
            if (user != null) {
                token = user.getToken();
            }
        } catch (Exception e) {

        } finally {
            realm.close();
        }
        token = "JWT " + token;
        return token;
    }
}
