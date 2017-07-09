package app.mobile.examwarrior.demo;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.TextView;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.widget.CheckableLinearLayout;
import de.timfreiheit.mathjax.android.MathJaxConfig;
import de.timfreiheit.mathjax.android.MathJaxView;

public class QuestionPagerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static String[] que;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pager);

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

        final String path="file:///android_asset/";
        //que = new String[]{"\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]","\\Δ FEC≅ \\ΔGBD","a{x^34} + 87x + 9 = 0","a{x^2} + 87x + 9 = 0","a{x^31} + 87x + 9 = 0","a{x^42} + 87x + 9 = 0","y-y_0=m(x-x_0)","\\cos^2θ+\\sin^2θ=1","∑↙{i=0}↖n i={n(n+1)}/2","{1+√5}/2=1+1/{1+1/{1+⋯}}","f'(x)=\\lim↙{h→0}{f(x+h)-f(x)}/h","\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]","\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]","(\\table \\cos θ, - \\sin θ; \\sin θ, \\cos θ)$ gives a rotation by","$v↖{→}⋅w↖{→} = vw\\cos θ$","$\\{x:x^2∈\\ℚ\\}$ has measure 0 in $\\ℝ$.","\\[1/7 = 0.\\ov 142857\\]","\\[√^n{a}√^n{b} = √^n{ab}\\]","\\[\\table a, =, b+c; , =, d\\]","\\[\\text\"average speed\" = \\text\"distance traveled\" / \\text\"elapsed time\"\\]"};

        que = new String[]{question, q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12 };
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_pager, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_question_pager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            MathJaxView  laTexView = (MathJaxView) rootView.findViewById(R.id.laTexView);
            laTexView.setInputText(que[getArguments().getInt(ARG_SECTION_NUMBER,0)]);
            rootView.findViewById(R.id.checkbox).setOnClickListener(this);
            rootView.findViewById(R.id.checkbox1).setOnClickListener(this);
            rootView.findViewById(R.id.checkbox2).setOnClickListener(this);
            rootView.findViewById(R.id.checkbox3).setOnClickListener(this);
            rootView.findViewById(R.id.checkbox4).setOnClickListener(this);

            MathJaxConfig mathJaxConfig = new MathJaxConfig();
//            WebView laTexView = (WebView) rootView.findViewById(R.id.web_view);
//            laTexView.getSettings().setJavaScriptEnabled(true);
//            laTexView.setVerticalScrollBarEnabled(true);
//            laTexView.setHorizontalScrollBarEnabled(true);
//            laTexView.getSettings().setSupportZoom(true);
//            final String path="file:///android_asset/";
//
//            laTexView.loadDataWithBaseURL(path, getString(path, que[getArguments().getInt(ARG_SECTION_NUMBER,0)]),  "text/html",  "UTF-8", null);
            return rootView;
        }
        private String getString(String path, String equation){
            final String js = "<html><head>"
                    + "<link rel='stylesheet' href='"+path+"jqmath-0.4.3.css'>"
                    + "<script src='"+path+"jquery-1.4.3.min.js'></script>"
                    + "<script src='"+path+"jqmath-etc-0.4.6.min.js'></script>"
                    + "<html>\n" +
                    "<body>\n" +
                    "\n" +
                   "<p>Two large and 1 small pumps can fill a swimming pool in 4 hours. One large and 3 small pumps can also fill the same swimming pool in 4 hours. How many hours will it take 4 large and 4 small pumps to fill the swimming pool.(We assume that all large pumps are similar and all small pumps are also similar. </p>"+
                    "<img src=\"https://cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg\" alt=\"Smiley face\" width=\"400\" height=\"400\">\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>"
                    + "<script>var m ='$$" +equation+"$$';M.parseMath(m);document.write(m);</script>"
                    + "<script>var s ='$$" +equation+"$$';M.parseMath(s);document.write(s);</script> <p>Two large and 1 small pumps can fill a swimming pool in 4 hours. One large and 3 small pumps can also fill the same swimming pool in 4 hours. How many hours will it take 4 large and 4 small pumps to fill the swimming pool.(We assume that all large pumps are similar and all small pumps are also similar. </p></body>";
            return  js;
        }

        @Override
        public void onClick(View v) {

            if(v instanceof  CheckableLinearLayout){
                ((CheckableLinearLayout)v).toggle();

            }

        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position );
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return que.length;
        }
    }
}
