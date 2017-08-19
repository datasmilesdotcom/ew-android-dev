package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.Questions;
import app.mobile.examwarrior.database.QuestionsList;
import app.mobile.examwarrior.ui.fragments.DisplayQuestionFragment;
import app.mobile.examwarrior.widget.LockableViewPager;
import io.realm.Realm;
import io.realm.RealmResults;

public class TestActivity1 extends AppCompatActivity {

    public static final String TAG = TestActivity1.class.getSimpleName();
    // String[] que;
    String path = "file:///android_asset/";
    long startTime = 0L;
    long updatedTime = 0L;
    long timeSwapBuff = 0L;
    long timeInMilliseconds = 0L;
    long startTotalTime = 0L;
    long updatedTotalTime = 0L;
    long timeTotalSwapBuff = 0L;
    long timeTotalInMilliseconds = 0L;
    RealmResults<Questions> lstQues;
    int pager_pos = -1;
    private android.support.v4.app.FragmentManager fragmentManager;
    private LockableViewPager mPager;
    private DemoFragmentAdapter mAdapter;
    private Realm realm;
    private TextView tvTotalTime, tvAnsTime;
    private Handler customHandler = new Handler();
    private Handler customHandlerTotal = new Handler();
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hour = mins / 60;
            mins = mins % 60;
            //int milliseconds = (int) (updatedTime % 1000);
            tvAnsTime.setText(String.format("%02d", hour) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };
    private Runnable updateTotalTimerThread = new Runnable() {

        public void run() {

            timeTotalInMilliseconds = SystemClock.uptimeMillis() - startTotalTime;
            updatedTotalTime = timeTotalSwapBuff + timeTotalInMilliseconds;
            int secs = (int) (updatedTotalTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hour = mins / 60;
            mins = mins % 60;
            //int milliseconds = (int) (updatedTime % 1000);
            tvTotalTime.setText(String.format("%02d", hour) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandlerTotal.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //   que = new String[]{"\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]", "\\Δ FEC≅ \\ΔGBD", "a{x^34} + 87x + 9 = 0", "a{x^2} + 87x + 9 = 0", "a{x^31} + 87x + 9 = 0", "a{x^42} + 87x + 9 = 0", "y-y_0=m(x-x_0)", "\\cos^2θ+\\sin^2θ=1", "∑↙{i=0}↖n i={n(n+1)}/2", "{1+√5}/2=1+1/{1+1/{1+⋯}}", "f'(x)=\\lim↙{h→0}{f(x+h)-f(x)}/h", "\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]", "\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]", "(\\table \\cos θ, - \\sin θ; \\sin θ, \\cos θ)$ gives a rotation by", "$v↖{→}⋅w↖{→} = vw\\cos θ$", "$\\{x:x^2∈\\ℚ\\}$ has measure 0 in $\\ℝ$.", "\\[1/7 = 0.\\ov 142857\\]", "\\[√^n{a}√^n{b} = √^n{ab}\\]", "\\[\\table a, =, b+c; , =, d\\]", "\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]"};

        realm = Realm.getDefaultInstance();
        putQuestionListIntoDatabaseFromAssets();
        lstQues = realm.where(Questions.class).findAll();
        mAdapter = new DemoFragmentAdapter(getSupportFragmentManager());
        mPager = (LockableViewPager) findViewById(R.id.pager);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        tvAnsTime = (TextView) findViewById(R.id.tvAnsTime);
        mPager.setAdapter(mAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("viewpager onPageScrolled :" + position);
                if (pager_pos != position) {
                    pager_pos = position;
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("viewpager onPageSelected :" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.left_nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        findViewById(R.id.right_nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("next :" + getItem(+1));
                int pos = getItem(+1);
                mPager.setCurrentItem(pos, true);

                if (pos != 20) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }

            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = getItem(-1);
                mPager.setCurrentItem(pos, true);
                if (pos != -1) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }
            }
        });

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        startTotalTime = SystemClock.uptimeMillis();
        customHandlerTotal.postDelayed(updateTotalTimerThread, 0);

    }

    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }

    private String getString(String path, String equation) {
        final String js = "<html><head>"
                + "<link rel='stylesheet' href='" + path + "jqmath-0.4.3.css'>"
                + "<script src='" + path + "jquery-1.4.3.min.js'></script>"
                + "<script src='" + path + "jqmath-etc-0.4.6.min.js'></script>"
                + "</head>Find the value of <body>"
                + "<script>var s ='$$" + equation + "$$';M.parseMath(s);document.write(s);</script></body>";
        return js;
    }

    private void putQuestionListIntoDatabaseFromAssets() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("questions/Questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            realm.beginTransaction();
            realm.createObjectFromJson(QuestionsList.class, json);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DemoFragmentAdapter extends FragmentPagerAdapter {

        public DemoFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
             return DisplayQuestionFragment.newInstance(getString(path, lstQues.get(position).getQuestion().toString()), "");
        }

        @Override
        public int getCount() {
            return lstQues.size();
        }
    }

}
