package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.ui.fragments.DisplayQuestionFragment;

public class TestActivity extends AppCompatActivity {

    private android.support.v4.app.FragmentManager fragmentManager;
    int i =0;
    String[] que;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        final String path="file:///android_asset/";
        que = new String[]{"\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]","\\Δ FEC≅ \\ΔGBD","a{x^34} + 87x + 9 = 0","a{x^2} + 87x + 9 = 0","a{x^31} + 87x + 9 = 0","a{x^42} + 87x + 9 = 0","y-y_0=m(x-x_0)","\\cos^2θ+\\sin^2θ=1","∑↙{i=0}↖n i={n(n+1)}/2","{1+√5}/2=1+1/{1+1/{1+⋯}}","f'(x)=\\lim↙{h→0}{f(x+h)-f(x)}/h","\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]","\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]","(\\table \\cos θ, - \\sin θ; \\sin θ, \\cos θ)$ gives a rotation by","$v↖{→}⋅w↖{→} = vw\\cos θ$","$\\{x:x^2∈\\ℚ\\}$ has measure 0 in $\\ℝ$.","\\[1/7 = 0.\\ov 142857\\]","\\[√^n{a}√^n{b} = √^n{ab}\\]","\\[\\table a, =, b+c; , =, d\\]","\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]"};
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if (savedInstanceState == null) {
            addFragment(R.id.questions_container, DisplayQuestionFragment.newInstance(getString(path, que[i]), ""), "");
        }

        findViewById(R.id.appCompatImageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i+1;
                if(i >= que.length){
                    i=0;
                }
                replaceFragment(R.id.questions_container, DisplayQuestionFragment.newInstance(getString(path, que[i]), ""), "", "");
            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i-1;
                if(i <=0){
                    i=que.length-1;
                }
                replaceFragment(R.id.questions_container, DisplayQuestionFragment.newInstance(getString(path, que[i]), ""), "", "");
            }
        });
    }

    private String getString(String path, String equation){
        final String js = "<html><head>"
                + "<link rel='stylesheet' href='"+path+"jqmath-0.4.3.css'>"
                + "<script src='"+path+"jquery-1.4.3.min.js'></script>"
                + "<script src='"+path+"jqmath-etc-0.4.6.min.js'></script>"
                + "</head>Find the value of <body>"
                + "<script>var s ='$$" +equation+"$$';M.parseMath(s);document.write(s);</script></body>";
        return  js;
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

    /**
     * Replace fragment from container
     *
     * @param containerViewId
     * @param fragment
     * @param fragmentTag
     * @param backStackStateName
     */
    public void replaceFragment(int containerViewId,
                                Fragment fragment,
                                String fragmentTag,
                                String backStackStateName) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*fragmentTransaction.setCustomAnimations(R.anim.fragment_anim_enter_from_left,
                R.anim.fragment_anim_exit_to_right, R.anim.fragment_anim_enter_from_right,
                R.anim.fragment_anim_exit_to_left);*/
        fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
        fragmentTransaction.addToBackStack(backStackStateName);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

}
