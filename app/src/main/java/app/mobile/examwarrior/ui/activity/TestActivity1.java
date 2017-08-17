package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.ui.fragments.DisplayQuestionFragment;
import app.mobile.examwarrior.widget.LockableViewPager;

public class TestActivity1 extends AppCompatActivity {

    String[] que;
    String path = "file:///android_asset/";
    private android.support.v4.app.FragmentManager fragmentManager;
    private LockableViewPager mPager;
    private DemoFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        que = new String[]{"\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]", "\\Δ FEC≅ \\ΔGBD", "a{x^34} + 87x + 9 = 0", "a{x^2} + 87x + 9 = 0", "a{x^31} + 87x + 9 = 0", "a{x^42} + 87x + 9 = 0", "y-y_0=m(x-x_0)", "\\cos^2θ+\\sin^2θ=1", "∑↙{i=0}↖n i={n(n+1)}/2", "{1+√5}/2=1+1/{1+1/{1+⋯}}", "f'(x)=\\lim↙{h→0}{f(x+h)-f(x)}/h", "\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]", "\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]", "(\\table \\cos θ, - \\sin θ; \\sin θ, \\cos θ)$ gives a rotation by", "$v↖{→}⋅w↖{→} = vw\\cos θ$", "$\\{x:x^2∈\\ℚ\\}$ has measure 0 in $\\ℝ$.", "\\[1/7 = 0.\\ov 142857\\]", "\\[√^n{a}√^n{b} = √^n{ab}\\]", "\\[\\table a, =, b+c; , =, d\\]", "\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]"};

        mAdapter = new DemoFragmentAdapter(getSupportFragmentManager());
        mPager = (LockableViewPager) findViewById(R.id.pager);

        mPager.setAdapter(mAdapter);
        findViewById(R.id.appCompatImageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(getItem(+1), true);
            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(getItem(-1), true);
            }
        });
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


    private class DemoFragmentAdapter extends FragmentPagerAdapter {

        public DemoFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DisplayQuestionFragment.newInstance(getString(path, que[position]), "");
        }

        @Override
        public int getCount() {
            return que.length;
        }
    }
}
