package app.mobile.examwarrior.ui.activity;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.PreCachingLayoutManager;
import app.mobile.examwarrior.adapters.QuestionsAdapter;
import app.mobile.examwarrior.ui.fragments.DisplayQuestionFragment;
import de.timfreiheit.mathjax.android.MathJaxView;

public class PracticeActivity extends AppCompatActivity {

    private android.support.v4.app.FragmentManager fragmentManager;
    WebView laTexView;
    String[] que;
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        AppCompatTextView text = (AppCompatTextView) findViewById(R.id.text___);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.setText(Html.fromHtml("<p>Examine the data in the LINE_ITEM table.&nbsp;</p><p><img class=\\\"fr-dib fr-draggable fr-fil\\\" src=\\\"/upload/postattachments/oracle12c-sorting-where-1_1480183690066.jpg\\\" style=\\\"width: 300px;\\\"></p><p>Evaluate this SQL statement:&nbsp;</p><p style=\\\"margin-left: 20px;\\\">SELECT product_id, quantity&nbsp;</p><p style=\\\"margin-left: 20px;\\\">FROM line_item&nbsp;</p><p style=\\\"margin-left: 20px;\\\">WHERE quantity BETWEEN 5 AND 30 ORDER BY order_id, line_item_id; </p><p>&nbsp;Which product_id value would be displayed last?</p>", Html.FROM_HTML_MODE_COMPACT));
        }else {
            text.setText(Html.fromHtml("<p>The account table contains these columns:</p><p><br></p><p>ACCOUNT_ID NUMBER(12)</p><p>NEW_BALANCE NUMBER(7,2)</p><p>PREV_BALANCE NUMBER(7,2)</p><p>FINANCE_CHARGE NUMBER(7,2)</p><p><br></p><p>You must create statements to be mailed to all account holders. Each customer&apos;s statement must include the account holder&apos;s previous balance and finance charge in this format:</p><p><br></p><p>Previous Balance: 5000 Finance Charge: 45</p><p><br></p><p>Which SELECT statement will produce these results?</p>"));
        }
        String question = "\\[\\begin{array}{l}\n" +
                "Fin\\,the\\,value\\,of\\,x\\,,\\,if:\\\\\n" +
                "\\left( {\\begin{array}{*{20}{c}}\n" +
                "{3x + y}&{ - y}\\\\\n" +
                "{2y - 3}&3\n" +
                "\\end{array}} \\right) = \\left( {\\begin{array}{*{20}{c}}\n" +
                "1&2\\\\\n" +
                "{ - 5}&3\n" +
                "\\end{array}} \\right)\n" +
                "\\end{array}\\]";


        String q2 =  "<p>Show that the vectors \\[\\vec a\\] , \\[\\vec b\\] and \\[\\vec c\\] are coplanar if \\[\\vec a\\] + \\[\\vec b\\] , \\[\\vec b\\]+ \\[\\vec c\\] and \\[\\vec c\\] + \\[\\vec a\\]are &nbsp;coplanar.&nbsp;</p>";
        String q3 = "\\[Hello\\,Jeevan\\,how\\,are\\,you?\\,I\\,am\\,ok..\\,but\\,sanesh\\,is\\,awesome\\,fin\\,the\\,value\\,of\\,equation\\,{x^2} + 5x + 6 = 0\\]";
        String q4 = "\\[\\begin{array}{l}\n" +
                "Solve\\,the\\,below\\,\\,Equation\\,{x^2}+5x+6=0\\\\\n" +
                "\\frac{{{{\\tan }^{ - 1}}(\\sin \\theta  + \\cos \\theta )}}{{{{\\cot }^2}\\theta }}\\\\\n" +
                "Values\\,of\\,x\\,,y = (1,2)\n" +
                "\\end{array}\\]";
        String q5 ="\\[\\begin{array}{l}\n" +
                "{\\rm{Solve the below Quadratic Equation}}\\,\\,:\\,\\,{x^2} + 5x + 6 = 0\\\\\n" +
                "\\\\\n" +
                "{\\rm{Values of x are 2 and 3}}\\\\\n" +
                "\\\\\n" +
                "{\\rm{Values of x are 4 and 1}}\\\\\n" +
                "\\\\\n" +
                "{\\rm{Values of x are 5 and 0}}\\\\\n" +
                "\\\\\n" +
                "\\frac{{{{\\tan }^{ - 1}}(\\sin \\theta  + \\cos \\theta )}}{{{{\\cot }^2}\\theta }}\\\\\n" +
                "\n" +
                "\\end{array}\\]";
        String q6 = "\\[Fin\\,the\\,value\\,of\\,\\int {{{\\tan }^{ - 1}}({x^2} + 56 + 6)} \\,when\\,x = {90^\\theta }\\]";
        String q7 = "Prove that:\n" +
                "\n" +
                "                                                  \\[\\left| \\begin{array}{l}\n" +
                "yz - {x^2}\\,\\,\\,zx - {y^2}\\,\\,\\,xy - {z^2}\\\\\n" +
                "zx - {y^2}\\,\\,\\,xy - {z^2}\\,\\,\\,yz - {x^2}\\\\\n" +
                "xy - {z^2}\\,\\,\\,yz - {x^2}\\,\\,\\,zx - {y^2}\n" +
                "\\end{array} \\right|\\]\n" +
                "\n" +
                "is divisible by \\[\\left( {x + y + z} \\right)\\], and hence find the quotient.\n" +
                "\n" +
                "                                                                OR\n" +
                "\n" +
                "Using elementary transformations, find the inverse of the matrix \\[A = \\left( {\\begin{array}{*{20}{c}}\n" +
                "8&4&3\\\\\n" +
                "2&1&1\\\\\n" +
                "1&2&2\n" +
                "\\end{array}} \\right)\\]\n" +
                "\n" +
                "and use it to solve the following system of linear equation:\n" +
                "\n" +
                "                                                                 \\[8x + 4y + 3z = 19\\]\n" +
                "\n" +
                "                                                                \\[2x + y + z = 5\\]\n" +
                "\n" +
                "                                                                \\[x + 2y + 2z = 7\\]";
        String q8 = "Using elementary transformations, find the inverse of the matrix \\[A = \\left( {\\begin{array}{*{20}{c}}\n" +
                "8&4&3\\\\\n" +
                "2&1&1\\\\\n" +
                "1&2&2\n" +
                "\\end{array}} \\right)\\]\n" +
                "\n" +
                "and use it to solve the following system of linear equation:\n" +
                "\n" +
                "                                                                 \\[8x + 4y + 3z = 19\\]\n" +
                "\n" +
                "                                                                \\[2x + y + z = 5\\]\n" +
                "\n" +
                "                                                                \\[x + 2y + 2z = 7\\]";
        String q9 = "Write the position vector of the point which divides the join of points with position  vectors \\[3\\mathop a\\limits^ \\to  \\, - \\,2\\mathop b\\limits^ \\to  \\,and\\,3\\mathop b\\limits^ \\to  \\] in the ratio2:1";
        String q10 = "Find the Vector and Cartesian equation of the line through the point \\[\\left( {1,\\,\\,2,\\,\\, - 4} \\right)\\] and  perpendicular to the two lines.\n" +
                "\n" +
                "\\[\\vec r = \\left( {8\\hat i - 19\\hat j + 10\\hat k} \\right) + \\lambda \\left( {3\\hat i - 16\\hat j + 7\\hat k} \\right)\\] and \n" +
                "\n" +
                "\\[\\vec r = \\left( {15\\hat i + 29\\hat j + 5\\hat k} \\right) + \\mu \\left( {3\\hat i + 8\\hat j - 5\\hat k} \\right)\\]\n";
        String q11 = "If \\[x = a\\sin \\,2t\\left( {1 + \\cos \\,2t} \\right)\\] and \\[y = b\\cos 2t\\left( {1 - \\cos \\,2t} \\right)\\] find the values of \\[\\frac{{dy}}{{dx}}\\] at \\[t = \\frac{\\pi }{4}\\] and \\[t = \\frac{\\pi }{3}\\].\n" +
                "\n" +
                "                                                               OR\n" +
                "\n" +
                "If \\[y = {x^x}\\], prove that \n" +
                "\n" +
                "                                          \\[\\frac{{{d^2}y}}{{d{x^2}}} - \\frac{1}{y}\\left( {\\frac{{dy}}{{dx}}} \\right) - \\frac{y}{x} = 0\\]\n";
        String q12 = "Find: \n" +
                "\n" +
                "                                                              \\[\\int {\\frac{{\\left( {3\\sin \\theta  - 2} \\right)\\cos \\theta }}{{5 - {{\\cos }^2}\\theta  - 4\\sin \\theta }}} d\\theta \\]\n" +
                "\n" +
                "                                                                                OR\n" +
                "\n" +
                "Evaluate:\n" +
                "\n" +
                "                                                                  \\[\\int\\limits_0^\\pi  {{e^{2x}}.\\sin \\left( {\\frac{\\pi }{4} + x} \\right)dx} \\]\n";
        String q13 = "\\ ";

        que = new String[]{"\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]","\\Δ FEC≅ \\ΔGBD","a{x^34} + 87x + 9 = 0","a{x^2} + 87x + 9 = 0","a{x^31} + 87x + 9 = 0","a{x^42} + 87x + 9 = 0","y-y_0=m(x-x_0)","\\cos^2θ+\\sin^2θ=1","∑↙{i=0}↖n i={n(n+1)}/2","{1+√5}/2=1+1/{1+1/{1+⋯}}","f'(x)=\\lim↙{h→0}{f(x+h)-f(x)}/h","\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]","\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]","(\\table \\cos θ, - \\sin θ; \\sin θ, \\cos θ)$ gives a rotation by","$v↖{→}⋅w↖{→} = vw\\cos θ$","$\\{x:x^2∈\\ℚ\\}$ has measure 0 in $\\ℝ$.","\\[1/7 = 0.\\ov 142857\\]","\\[√^n{a}√^n{b} = √^n{ab}\\]","\\[\\table a, =, b+c; , =, d\\]","\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]"};

//        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        linearLayoutManager.setItemPrefetchEnabled(true);
//        linearLayoutManager.setInitialPrefetchItemCount(10);
//        recyclerview.setLayoutManager(linearLayoutManager);
//        QuestionsAdapter questionsAdapter = new QuestionsAdapter(this, que);
//
//
//
//        LinearSnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerview);
//        recyclerview.setOnFlingListener(snapHelper);
//        recyclerview.addItemDecoration(new VerticalOffsetDecoration(this));
//        recyclerview.setItemViewCacheSize(20);
//        recyclerview.setAdapter(questionsAdapter);
//        if (savedInstanceState == null) {
//            addFragment(R.id.questions_container, DisplayQuestionFragment.newInstance("", ""), "");
//        }

        laTexView = (WebView) findViewById(R.id.laTexView);
        laTexView.getSettings().setJavaScriptEnabled(true);
        laTexView.getSettings().setSupportZoom(true);
        //laTexView.setInputText(que[i]);


        final String path="file:///android_asset/";

        laTexView.loadDataWithBaseURL(path, getString(path, que[i]),  "text/html",  "UTF-8", null);

        findViewById(R.id.exo_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i+1;
                if(i >= que.length){
                    i=0;
                }

                laTexView.loadDataWithBaseURL(path, getString(path, que[i]),  "text/html",  "UTF-8", null);

                //laTexView.setInputText(que[i]);
                //replaceFragment(R.id.questions_container, DisplayQuestionFragment.newInstance("", ""), "","");
            }
        });

        findViewById(R.id.exo_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i-1;
                if(i <=0){
                    i=que.length-1;
                }

                laTexView.loadDataWithBaseURL(path, getString(path,que[i]),  "text/html",  "UTF-8", null);

                //laTexView.setInputText(que[i]);
                //replaceFragment(R.id.questions_container, DisplayQuestionFragment.newInstance("", ""), "","");
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
    public class VerticalOffsetDecoration extends RecyclerView.ItemDecoration {
        private Activity context;

        public VerticalOffsetDecoration(Activity context) {
            this.context = context;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int total = parent.getAdapter().getItemCount();


            // 5
            if (position != 0 && position != total - 1)
                return;

            // 6
            Display display = context.getWindowManager().getDefaultDisplay();
            Point displaySize = new Point();
            display.getSize(displaySize);
            int displayWidth = displaySize.x;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            float viewWidth = params.width;

            // 7
            int offset = (int)(displayWidth - viewWidth) / 2 ;

            // 8
            if (position == 0)
                outRect.left = offset - params.getMarginStart();

            if (position == total - 1)
                outRect.right = offset- params.getMarginEnd();
        }
    }

    @JavascriptInterface
    private void click(){

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
