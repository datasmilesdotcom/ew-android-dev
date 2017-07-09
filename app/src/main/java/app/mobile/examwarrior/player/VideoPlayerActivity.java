package app.mobile.examwarrior.player;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.ui.fragments.SuggestionFragment;

public class VideoPlayerActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        if (savedInstanceState == null) {
            addFragment(R.id.course_player_view, VideoPlayerFragment.newInstance("", ""), VideoPlayerFragment.TAG);
            addFragment(R.id.related_videos, SuggestionFragment.newInstance("", ""), SuggestionFragment.TAG);
        }
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
        fragmentTransaction.add(containerViewId, fragment, fragmentTag);
        //fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            findViewById(R.id.related_videos).setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            findViewById(R.id.related_videos).setVisibility(View.VISIBLE);
        }

        super.onConfigurationChanged(newConfig);
    }
}
