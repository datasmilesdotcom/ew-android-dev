package app.mobile.examwarrior.demo;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.player.VideoPlayerFragment;

public class DemoActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @NonNull
    private static Rect createRect(@NonNull ViewGroup parent, @NonNull View view) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        parent.offsetDescendantRectToMyCoords(view, rect);

        return rect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            addFragment(R.id.sample, VideoPlayerFragment.newInstance("", ""), "TAG");
            addFragment(R.id.sample_, VideoPlayerFragment.newInstance("", ""), "TAG");
        }
        /*RevealFrameLayout revealFrameLayout = (RevealFrameLayout) findViewById(R.id.frame);
        revealFrameLayout.clearAnimation();*/
        /*final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoActivity.this, RegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(RegistrationActivity.EXTRA_RECT, createRect((ViewGroup) fab.getParent(), fab));
                startActivity(intent);
            }
        });*/
    }

    /**
     * Add fragment to the container
     *
     * @param containerViewId
     * @param fragment
     * @param fragmentTag
     */
    protected void addFragment(int containerViewId,
                               Fragment fragment,
                               String fragmentTag) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fragment_anim_enter_from_left,
        // R.anim.fragment_anim_exit_to_right,R.anim.fragment_anim_enter_from_right,
        // R.anim.fragment_anim_exit_to_left);
        fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
        //fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.sample_).setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            findViewById(R.id.sample_).setVisibility(View.VISIBLE);
        }

        super.onConfigurationChanged(newConfig);
    }
}
