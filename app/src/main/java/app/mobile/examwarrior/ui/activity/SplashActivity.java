package app.mobile.examwarrior.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.prefs.AppPref;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {
    final long WAITING_TIME = 1000 * 3;
    final long NOTIFY_TIME = 1000 * 3;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.logo_splash);


        StartAnimations(appCompatImageView);
        //StartAnimations(appName);


        TextView fullscreen_content = (TextView) findViewById(R.id.fullscreen_content);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(

                //ObjectAnimator.ofFloat(myView, "rotationY", 0, 180),
                //ObjectAnimator.ofFloat(myView, "rotation", 0, -90),
                ObjectAnimator.ofFloat(fullscreen_content, "scaleX", 0, 0.25f, 0.55f, 0.75f, 1f),
                ObjectAnimator.ofFloat(fullscreen_content, "scaleY", -20, 1f),
                ObjectAnimator.ofFloat(fullscreen_content, "alpha", 0, 0.25f, 1)
        );
        animSet.setDuration(5000).start();
        countDownTimer = new CountDownTimer(WAITING_TIME, NOTIFY_TIME) {

            @Override
            public void onTick(long millisUntilFinished) {
            }


            @Override
            public void onFinish() {
                AppPref.getInstance().setSplashShown(true);
                Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                SplashActivity.this.finish();
            }

        };
        countDownTimer.start();

    }

    /**
     * Start splash screen animation
     */
    private void StartAnimations(View myView) {
        AnimatorSet animSet = new AnimatorSet();
        myView.setPivotX(0f);
        myView.setPivotY(0f);
        animSet.playTogether(

                //ObjectAnimator.ofFloat(myView, "rotationY", 0, 180),
                //ObjectAnimator.ofFloat(myView, "rotation", 0, -90),
                ObjectAnimator.ofFloat(myView, "scaleX", 0, 0.25f, 0.55f, 0.75f, 1f),
                ObjectAnimator.ofFloat(myView, "scaleY", 0, 0.25f, 0.55f, 0.75f, 1f),
                ObjectAnimator.ofFloat(myView, "alpha", 0, 0.25f, 1)
        );
        animSet.setDuration(5000).start();
       /* ViewCompat.animate(view)
                .translationY(-250)
                .setStartDelay(1000)
                .setDuration(3000).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();
       *//* android.view.animation.Animation anim = AnimationUtils.loadAnimation(this, R.anim.blink);
        anim.reset();
        anim.setInterpolator(new LinearInterpolator());
        view.setAnimation(anim);*//*
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator()); //add this
        fadeIn.setDuration(3000);
        view.startAnimation(fadeIn);
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

}
