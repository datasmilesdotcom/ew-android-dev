package app.mobile.examwarrior.ui.app_intro;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import app.mobile.examwarrior.R;

public class AppIntroActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TOTAL_PAGES = 6;
    private static final int ACTUAL_PAGES_COUNT = 3;
    private int[] mPagesColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

        mPagesColors = new int[]{
                ContextCompat.getColor(this, android.R.color.darker_gray),
                ContextCompat.getColor(this, android.R.color.holo_green_dark),
                ContextCompat.getColor(this, android.R.color.holo_red_dark),
                ContextCompat.getColor(this, android.R.color.holo_blue_dark),
                ContextCompat.getColor(this, android.R.color.holo_purple),
                ContextCompat.getColor(this, android.R.color.holo_orange_dark),
        };
        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }
    }

    public void replaceTutorialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AppIntroContainer())
                .commit();
    }

    @Override
    public void onClick(View v) {

    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // hide status bar
        );
    }

    @Override
    protected void onResume() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    hideSystemUI();
                }
            }
        }, 500);

        super.onResume();
    }
}
