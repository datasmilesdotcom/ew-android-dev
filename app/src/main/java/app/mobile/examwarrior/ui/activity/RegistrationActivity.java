package app.mobile.examwarrior.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.RegistrationPagerAdapter;
import app.mobile.examwarrior.util.CustomProgressDialogFragment;
import app.mobile.examwarrior.widget.LockableViewPager;

public class RegistrationActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public final static String EXTRA_RECT = "com.example.circularreveal.CustomActivity2";
    private static final int PAGE_COUNT = 3;
    private LockableViewPager registration_viewpager;
    private CustomProgressDialogFragment progressDialogFragment;
    private RegistrationPagerAdapter registrationPagerAdapter;
    //private Rect rect;
    //private RevealFrameLayout layout;
    private ConstraintLayout constraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registration_viewpager = (LockableViewPager) findViewById(R.id.registration_viewpager);
        registration_viewpager.setSwipeable(false);
        registrationPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager(), PAGE_COUNT);
        registration_viewpager.setAdapter(registrationPagerAdapter);
        if (savedInstanceState == null) {
            registration_viewpager.setCurrentItem(1);
        }
        constraint = (ConstraintLayout) findViewById(R.id.constraint);
        //layout = (RevealFrameLayout) findViewById(R.id.circularrevealframelayout_reg);

        registration_viewpager.addOnPageChangeListener(this);
        //rect = getIntent().getParcelableExtra(EXTRA_RECT);
       /* constraint.post(new Runnable() {
            @Override
            public void run() {
                layout.animate(constraint, rect.centerX(), rect.centerY()).start();
            }
        });*/
    }

   /* @Override
    public void onBackPressed() {
        Animator animator = layout.animate(constraint, rect.centerX(), rect.centerY(), true);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                constraint.setVisibility(View.INVISIBLE);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        animator.start();
    }*/

    @Override
    public void onBackPressed() {
        if (registration_viewpager.getCurrentItem() == 1) {
            finish();
            super.onBackPressed();
        } else {
            registration_viewpager.setCurrentItem(1);
        }
    }

    public void changePage(int index) {
        if (registration_viewpager != null) registration_viewpager.setCurrentItem(index);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * progress dialog update status implementation
     *
     * @param status
     */
    public void updateProgressDialog(AppCompatActivity fragmentActivity, String text, boolean status) {

        if (fragmentActivity.isFinishing()) {
            return;
        }
        if (progressDialogFragment == null) {
            progressDialogFragment = CustomProgressDialogFragment.newInstance(text, "");
        }

        if (status) {
            progressDialogFragment.removeCustomDialog(fragmentActivity);
            progressDialogFragment.showCustomDialog(fragmentActivity);
            progressDialogFragment.setCancelable(false);
        } else {
            progressDialogFragment.removeCustomDialog(fragmentActivity);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(0, resultCode, data);
    }
}
