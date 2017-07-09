package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.CoursesListAdapter;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.Course;
import app.mobile.examwarrior.model.Type;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar courseLoading;
    private AppCompatTextView emptyTextView;
    private Realm realm;
    private RealmResults<Course> courseRealmResults;

    private OrderedRealmCollectionChangeListener<RealmResults<Course>> courseChangeListener = new OrderedRealmCollectionChangeListener<RealmResults<Course>>() {
        @Override
        public void onChange(RealmResults<Course> courses, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (courses.size() > 0) {
                courseLoading.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getCoursesList();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        courseLoading = (ProgressBar) findViewById(R.id.live_loading);
        emptyTextView = (AppCompatTextView) findViewById(R.id.empty_text_view);

        RecyclerView courses_list = (RecyclerView) findViewById(R.id.courses_list);
        courses_list.setLayoutManager(new LinearLayoutManager(this));
        courseRealmResults = realm.where(Course.class).findAllAsync();
        courseRealmResults.addChangeListener(courseChangeListener);
        CoursesListAdapter coursesListAdapter = new CoursesListAdapter(HomeActivity.this, courseRealmResults, true);
        courses_list.setAdapter(coursesListAdapter);
        getCoursesList();
    }

    private void getCoursesList() {
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<List<Course>> coursesList = apiInterface.getCourseList(new Type("it"));
        coursesList.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, final Response<List<Course>> response) {
                if (courseLoading.getVisibility() == View.VISIBLE)
                    courseLoading.setVisibility(View.GONE);
                if (response.body() != null && !response.body().isEmpty()) {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (Course course : response.body()) {
                                realm.insertOrUpdate(course);
                            }
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable throwable) {

                        }
                    });
                } else {
                    emptyTextView.setText(R.string.content_not_available);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        courseRealmResults.removeChangeListener(courseChangeListener);
        realm.close();
        super.onDestroy();
    }
}
