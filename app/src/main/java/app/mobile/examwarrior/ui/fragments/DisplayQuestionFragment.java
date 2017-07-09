package app.mobile.examwarrior.ui.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.fcannizzaro.jsoup.annotations.interfaces.ForEach;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import app.mobile.examwarrior.R;
import de.timfreiheit.mathjax.android.MathJaxView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayQuestionFragment extends Fragment {

    public static final String TAG = DisplayQuestionFragment.class.getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private WebView questionsText;
    private LinearLayout options;
    private AppCompatTextView optionOne;
    private AppCompatTextView optionTwo;
    private AppCompatTextView optionThree;
    private AppCompatTextView optionFour;
    private WebView laTexView;

    public DisplayQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayQuestionFragment.
     */
    public static DisplayQuestionFragment newInstance(String param1, String param2) {
        DisplayQuestionFragment fragment = new DisplayQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_question, null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionsText = (WebView) view.findViewById(R.id.questions_text);
        options = (LinearLayout) view.findViewById(R.id.options);
        //optionOne = (AppCompatTextView) view.findViewById(R.id.option_one);
        optionTwo = (AppCompatTextView) view.findViewById(R.id.option_two);
        optionThree = (AppCompatTextView) view.findViewById(R.id.option_three);
        optionFour = (AppCompatTextView) view.findViewById(R.id.option_four);

        questionsText.getSettings().setJavaScriptEnabled(true);
        String question = "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Spectacular Mountain</h2>\n" +
                "<h3>Spectacular Mountain</h3>\n" +
                "<img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZ9lSBc0fJisamLRH6rh3MZPRgUCHDStGKt95lVyKzich1uVZK\" alt=\"Mountain View\" style=\"width:304px;height:228px;\">\n" +
                "<p><b>You query the database with this SQL statement:SELECT id_number, NVL(100 / quantity, 0)FROM product;Which SQL SELECT statement capabilities are performed by this query? </b> </p>\n" +
                "<p>This query performs the projection capability of a SELECT statement, because only specific columns are returned. Projection is performed when the SELECT clause contains a column or column list. Using an asterisk  in the SELECT clause would also be an example of projection, because you would be choosing all columns.All of the other options are incorrect. This query does not perform a selection because no restricting criteria are used. Restricting criteria would be placed in a WHERE clause, but this example does not contain a WHERE clause. For example, you could include WHERE manufacturer_id = 'NF10032' in the query to limit the display results to only those products with a manufacturer_id value of NF10032.This query does not perform a join because only one table is queried. The join capability is used when two or more tables are joined, or linked, in a WHERE clause and data is queried.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        question =    "<style id=\"MathJax_CHTML_styles\">.mjx-chtml {display: inline-block; line-height: 0; text-indent: 0; text-align: left; text-transform: none; font-style: normal; font-weight: normal; font-size: 100%; font-size-adjust: none; letter-spacing: normal; word-wrap: normal; word-spacing: normal; white-space: nowrap; float: none; direction: ltr; max-width: none; max-height: none; min-width: 0; min-height: 0; border: 0; margin: 0; padding: 1px 0}\n.MJXc-display {display: block; text-align: center; margin: 1em 0; padding: 0}\n.mjx-chtml[tabindex]:focus, body :focus .mjx-chtml[tabindex] {display: inline-table}\n.mjx-full-width {text-align: center; display: table-cell!important; width: 10000em}\n.mjx-math {display: inline-block; border-collapse: separate; border-spacing: 0}\n.mjx-math * {display: inline-block; -webkit-box-sizing: content-box!important; -moz-box-sizing: content-box!important; box-sizing: content-box!important; text-align: left}\n.mjx-numerator {display: block; text-align: center}\n.mjx-denominator {display: block; text-align: center}\n.MJXc-stacked {height: 0; position: relative}\n.MJXc-stacked > * {position: absolute}\n.MJXc-bevelled > * {display: inline-block}\n.mjx-stack {display: inline-block}\n.mjx-op {display: block}\n.mjx-under {display: table-cell}\n.mjx-over {display: block}\n.mjx-over > * {padding-left: 0px!important; padding-right: 0px!important}\n.mjx-under > * {padding-left: 0px!important; padding-right: 0px!important}\n.mjx-stack > .mjx-sup {display: block}\n.mjx-stack > .mjx-sub {display: block}\n.mjx-prestack > .mjx-presup {display: block}\n.mjx-prestack > .mjx-presub {display: block}\n.mjx-delim-h > .mjx-char {display: inline-block}\n.mjx-surd {vertical-align: top}\n.mjx-mphantom * {visibility: hidden}\n.mjx-merror {background-color: #FFFF88; color: #CC0000; border: 1px solid #CC0000; padding: 2px 3px; font-style: normal; font-size: 90%}\n.mjx-annotation-xml {line-height: normal}\n.mjx-menclose > svg {fill: none; stroke: currentColor}\n.mjx-mtr {display: table-row}\n.mjx-mlabeledtr {display: table-row}\n.mjx-mtd {display: table-cell; text-align: center}\n.mjx-label {display: table-row}\n.mjx-box {display: inline-block}\n.mjx-block {display: block}\n.mjx-span {display: inline}\n.mjx-char {display: block; white-space: pre}\n.mjx-itable {display: inline-table; width: auto}\n.mjx-row {display: table-row}\n.mjx-cell {display: table-cell}\n.mjx-table {display: table; width: 100%}\n.mjx-line {display: block; height: 0}\n.mjx-strut {width: 0; padding-top: 1em}\n.mjx-vsize {width: 0}\n.MJXc-space1 {margin-left: .167em}\n.MJXc-space2 {margin-left: .222em}\n.MJXc-space3 {margin-left: .278em}\n.mjx-ex-box-test {position: absolute; width: 1px; height: 60ex}\n.mjx-line-box-test {display: table!important}\n.mjx-line-box-test span {display: table-cell!important; width: 10000em!important; min-width: 0; max-width: none; padding: 0; border: 0; margin: 0}\n.MJXc-TeX-unknown-R {font-family: monospace; font-style: normal; font-weight: normal}\n.MJXc-TeX-unknown-I {font-family: monospace; font-style: italic; font-weight: normal}\n.MJXc-TeX-unknown-B {font-family: monospace; font-style: normal; font-weight: bold}\n.MJXc-TeX-unknown-BI {font-family: monospace; font-style: italic; font-weight: bold}\n.MJXc-TeX-ams-R {font-family: MJXc-TeX-ams-R,MJXc-TeX-ams-Rw}\n.MJXc-TeX-cal-B {font-family: MJXc-TeX-cal-B,MJXc-TeX-cal-Bx,MJXc-TeX-cal-Bw}\n.MJXc-TeX-frak-R {font-family: MJXc-TeX-frak-R,MJXc-TeX-frak-Rw}\n.MJXc-TeX-frak-B {font-family: MJXc-TeX-frak-B,MJXc-TeX-frak-Bx,MJXc-TeX-frak-Bw}\n.MJXc-TeX-math-BI {font-family: MJXc-TeX-math-BI,MJXc-TeX-math-BIx,MJXc-TeX-math-BIw}\n.MJXc-TeX-sans-R {font-family: MJXc-TeX-sans-R,MJXc-TeX-sans-Rw}\n.MJXc-TeX-sans-B {font-family: MJXc-TeX-sans-B,MJXc-TeX-sans-Bx,MJXc-TeX-sans-Bw}\n.MJXc-TeX-sans-I {font-family: MJXc-TeX-sans-I,MJXc-TeX-sans-Ix,MJXc-TeX-sans-Iw}\n.MJXc-TeX-script-R {font-family: MJXc-TeX-script-R,MJXc-TeX-script-Rw}\n.MJXc-TeX-type-R {font-family: MJXc-TeX-type-R,MJXc-TeX-type-Rw}\n.MJXc-TeX-cal-R {font-family: MJXc-TeX-cal-R,MJXc-TeX-cal-Rw}\n.MJXc-TeX-main-B {font-family: MJXc-TeX-main-B,MJXc-TeX-main-Bx,MJXc-TeX-main-Bw}\n.MJXc-TeX-main-I {font-family: MJXc-TeX-main-I,MJXc-TeX-main-Ix,MJXc-TeX-main-Iw}\n.MJXc-TeX-main-R {font-family: MJXc-TeX-main-R,MJXc-TeX-main-Rw}\n.MJXc-TeX-math-I {font-family: MJXc-TeX-math-I,MJXc-TeX-math-Ix,MJXc-TeX-math-Iw}\n.MJXc-TeX-size1-R {font-family: MJXc-TeX-size1-R,MJXc-TeX-size1-Rw}\n.MJXc-TeX-size2-R {font-family: MJXc-TeX-size2-R,MJXc-TeX-size2-Rw}\n.MJXc-TeX-size3-R {font-family: MJXc-TeX-size3-R,MJXc-TeX-size3-Rw}\n.MJXc-TeX-size4-R {font-family: MJXc-TeX-size4-R,MJXc-TeX-size4-Rw}\n@font-face {font-family: MJXc-TeX-ams-R; src: local('MathJax_AMS'), local('MathJax_AMS-Regular')}\n@font-face {font-family: MJXc-TeX-ams-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_AMS-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_AMS-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_AMS-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-cal-B; src: local('MathJax_Caligraphic Bold'), local('MathJax_Caligraphic-Bold')}\n@font-face {font-family: MJXc-TeX-cal-Bx; src: local('MathJax_Caligraphic'); font-weight: bold}\n@font-face {font-family: MJXc-TeX-cal-Bw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Caligraphic-Bold.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Caligraphic-Bold.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Caligraphic-Bold.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-frak-R; src: local('MathJax_Fraktur'), local('MathJax_Fraktur-Regular')}\n@font-face {font-family: MJXc-TeX-frak-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Fraktur-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Fraktur-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Fraktur-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-frak-B; src: local('MathJax_Fraktur Bold'), local('MathJax_Fraktur-Bold')}\n@font-face {font-family: MJXc-TeX-frak-Bx; src: local('MathJax_Fraktur'); font-weight: bold}\n@font-face {font-family: MJXc-TeX-frak-Bw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Fraktur-Bold.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Fraktur-Bold.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Fraktur-Bold.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-math-BI; src: local('MathJax_Math BoldItalic'), local('MathJax_Math-BoldItalic')}\n@font-face {font-family: MJXc-TeX-math-BIx; src: local('MathJax_Math'); font-weight: bold; font-style: italic}\n@font-face {font-family: MJXc-TeX-math-BIw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Math-BoldItalic.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Math-BoldItalic.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Math-BoldItalic.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-sans-R; src: local('MathJax_SansSerif'), local('MathJax_SansSerif-Regular')}\n@font-face {font-family: MJXc-TeX-sans-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_SansSerif-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_SansSerif-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_SansSerif-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-sans-B; src: local('MathJax_SansSerif Bold'), local('MathJax_SansSerif-Bold')}\n@font-face {font-family: MJXc-TeX-sans-Bx; src: local('MathJax_SansSerif'); font-weight: bold}\n@font-face {font-family: MJXc-TeX-sans-Bw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_SansSerif-Bold.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_SansSerif-Bold.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_SansSerif-Bold.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-sans-I; src: local('MathJax_SansSerif Italic'), local('MathJax_SansSerif-Italic')}\n@font-face {font-family: MJXc-TeX-sans-Ix; src: local('MathJax_SansSerif'); font-style: italic}\n@font-face {font-family: MJXc-TeX-sans-Iw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_SansSerif-Italic.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_SansSerif-Italic.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_SansSerif-Italic.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-script-R; src: local('MathJax_Script'), local('MathJax_Script-Regular')}\n@font-face {font-family: MJXc-TeX-script-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Script-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Script-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Script-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-type-R; src: local('MathJax_Typewriter'), local('MathJax_Typewriter-Regular')}\n@font-face {font-family: MJXc-TeX-type-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Typewriter-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Typewriter-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Typewriter-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-cal-R; src: local('MathJax_Caligraphic'), local('MathJax_Caligraphic-Regular')}\n@font-face {font-family: MJXc-TeX-cal-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Caligraphic-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Caligraphic-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Caligraphic-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-main-B; src: local('MathJax_Main Bold'), local('MathJax_Main-Bold')}\n@font-face {font-family: MJXc-TeX-main-Bx; src: local('MathJax_Main'); font-weight: bold}\n@font-face {font-family: MJXc-TeX-main-Bw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Main-Bold.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Main-Bold.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Main-Bold.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-main-I; src: local('MathJax_Main Italic'), local('MathJax_Main-Italic')}\n@font-face {font-family: MJXc-TeX-main-Ix; src: local('MathJax_Main'); font-style: italic}\n@font-face {font-family: MJXc-TeX-main-Iw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Main-Italic.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Main-Italic.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Main-Italic.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-main-R; src: local('MathJax_Main'), local('MathJax_Main-Regular')}\n@font-face {font-family: MJXc-TeX-main-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Main-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Main-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Main-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-math-I; src: local('MathJax_Math Italic'), local('MathJax_Math-Italic')}\n@font-face {font-family: MJXc-TeX-math-Ix; src: local('MathJax_Math'); font-style: italic}\n@font-face {font-family: MJXc-TeX-math-Iw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Math-Italic.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Math-Italic.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Math-Italic.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-size1-R; src: local('MathJax_Size1'), local('MathJax_Size1-Regular')}\n@font-face {font-family: MJXc-TeX-size1-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Size1-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Size1-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Size1-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-size2-R; src: local('MathJax_Size2'), local('MathJax_Size2-Regular')}\n@font-face {font-family: MJXc-TeX-size2-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Size2-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Size2-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Size2-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-size3-R; src: local('MathJax_Size3'), local('MathJax_Size3-Regular')}\n@font-face {font-family: MJXc-TeX-size3-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Size3-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Size3-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Size3-Regular.otf') format('opentype')}\n@font-face {font-family: MJXc-TeX-size4-R; src: local('MathJax_Size4'), local('MathJax_Size4-Regular')}\n@font-face {font-family: MJXc-TeX-size4-Rw; src /*1*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/eot/MathJax_Size4-Regular.eot'); src /*2*/: url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/woff/MathJax_Size4-Regular.woff') format('woff'), url('https://cdn.mathjax.org/mathjax/latest/fonts/HTML-CSS/TeX/otf/MathJax_Size4-Regular.otf') format('opentype')}\n</style><p>Find the value of ??, if:</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span class=\"mjx-chtml MJXc-display\" style=\"text-align: center;\"><span id=\"MathJax-Element-1-Frame\" class=\"mjx-chtml\" style=\"text-align: center;\"><span id=\"MJXc-Node-18738\" class=\"mjx-math\"><span id=\"MJXc-Node-18739\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18740\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18741\" class=\"mjx-mo\"><span class=\"mjx-char MJXc-TeX-size3-R\" style=\"padding-top: 1.256em; padding-bottom: 1.256em;\">(</span></span><span id=\"MJXc-Node-18742\" class=\"mjx-texatom\"><span id=\"MJXc-Node-18743\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18744\" class=\"mjx-mtable\" style=\"vertical-align: -0.98em; padding: 0px 0.167em;\"><span class=\"mjx-table\"><span id=\"MJXc-Node-18745\" class=\"mjx-mtr\" style=\"height: 1.23em;\"><span id=\"MJXc-Node-18746\" class=\"mjx-mtd\" style=\"padding: 0px 0.5em 0px 0px; width: 2.79em;\"><span id=\"MJXc-Node-18747\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18748\" class=\"mjx-texatom\"><span id=\"MJXc-Node-18749\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18750\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">3</span></span><span id=\"MJXc-Node-18751\" class=\"mjx-mi\"><span class=\"mjx-char MJXc-TeX-math-I\" style=\"padding-top: 0.225em; padding-bottom: 0.298em;\">x</span></span><span id=\"MJXc-Node-18752\" class=\"mjx-mo MJXc-space2\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.298em; padding-bottom: 0.446em;\">+</span></span><span id=\"MJXc-Node-18753\" class=\"mjx-mi MJXc-space2\"><span class=\"mjx-char MJXc-TeX-math-I\" style=\"padding-top: 0.225em; padding-bottom: 0.519em; padding-right: 0.006em;\">y</span></span></span></span><span class=\"mjx-strut\"></span></span></span><span id=\"MJXc-Node-18754\" class=\"mjx-mtd\" style=\"padding: 0px 0px 0px 0.5em; width: 1.274em;\"><span id=\"MJXc-Node-18755\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18756\" class=\"mjx-texatom\"><span id=\"MJXc-Node-18757\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18758\" class=\"mjx-mo\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.298em; padding-bottom: 0.446em;\">-</span></span><span id=\"MJXc-Node-18759\" class=\"mjx-mi\"><span class=\"mjx-char MJXc-TeX-math-I\" style=\"padding-top: 0.225em; padding-bottom: 0.519em; padding-right: 0.006em;\">y</span></span></span></span><span class=\"mjx-strut\"></span></span></span></span><span id=\"MJXc-Node-18760\" class=\"mjx-mtr\" style=\"height: 1.23em;\"><span id=\"MJXc-Node-18761\" class=\"mjx-mtd\" style=\"padding: 0.2em 0.5em 0px 0px;\"><span id=\"MJXc-Node-18762\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18763\" class=\"mjx-texatom\"><span id=\"MJXc-Node-18764\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18765\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">2</span></span><span id=\"MJXc-Node-18766\" class=\"mjx-mi\"><span class=\"mjx-char MJXc-TeX-math-I\" style=\"padding-top: 0.225em; padding-bottom: 0.519em; padding-right: 0.006em;\">y</span></span><span id=\"MJXc-Node-18767\" class=\"mjx-mo MJXc-space2\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.298em; padding-bottom: 0.446em;\">-</span></span><span id=\"MJXc-Node-18768\" class=\"mjx-mn MJXc-space2\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">3</span></span></span></span><span class=\"mjx-strut\"></span></span></span><span id=\"MJXc-Node-18769\" class=\"mjx-mtd\" style=\"padding: 0.2em 0px 0px 0.5em;\"><span id=\"MJXc-Node-18770\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18771\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">3</span></span><span class=\"mjx-strut\"></span></span></span></span></span></span></span></span><span id=\"MJXc-Node-18772\" class=\"mjx-mo\"><span class=\"mjx-char MJXc-TeX-size3-R\" style=\"padding-top: 1.256em; padding-bottom: 1.256em;\">)</span></span></span><span id=\"MJXc-Node-18773\" class=\"mjx-mo MJXc-space3\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.077em; padding-bottom: 0.298em;\">=</span></span><span id=\"MJXc-Node-18774\" class=\"mjx-mrow MJXc-space3\"><span id=\"MJXc-Node-18775\" class=\"mjx-mo\"><span class=\"mjx-char MJXc-TeX-size3-R\" style=\"padding-top: 1.256em; padding-bottom: 1.256em;\">(</span></span><span id=\"MJXc-Node-18776\" class=\"mjx-texatom\"><span id=\"MJXc-Node-18777\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18778\" class=\"mjx-mtable\" style=\"vertical-align: -0.95em; padding: 0px 0.167em;\"><span class=\"mjx-table\"><span id=\"MJXc-Node-18779\" class=\"mjx-mtr\" style=\"height: 1.2em;\"><span id=\"MJXc-Node-18780\" class=\"mjx-mtd\" style=\"padding: 0px 0.5em 0px 0px; width: 1.278em;\"><span id=\"MJXc-Node-18781\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18782\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">1</span></span><span class=\"mjx-strut\"></span></span></span><span id=\"MJXc-Node-18783\" class=\"mjx-mtd\" style=\"padding: 0px 0px 0px 0.5em; width: 0.5em;\"><span id=\"MJXc-Node-18784\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18785\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">2</span></span><span class=\"mjx-strut\"></span></span></span></span><span id=\"MJXc-Node-18786\" class=\"mjx-mtr\" style=\"height: 1.2em;\"><span id=\"MJXc-Node-18787\" class=\"mjx-mtd\" style=\"padding: 0.2em 0.5em 0px 0px;\"><span id=\"MJXc-Node-18788\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18789\" class=\"mjx-texatom\"><span id=\"MJXc-Node-18790\" class=\"mjx-mrow\"><span id=\"MJXc-Node-18791\" class=\"mjx-mo\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.298em; padding-bottom: 0.446em;\">-</span></span><span id=\"MJXc-Node-18792\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">5</span></span></span></span><span class=\"mjx-strut\"></span></span></span><span id=\"MJXc-Node-18793\" class=\"mjx-mtd\" style=\"padding: 0.2em 0px 0px 0.5em;\"><span id=\"MJXc-Node-18794\" class=\"mjx-mrow\" style=\"margin-top: -0.2em;\"><span id=\"MJXc-Node-18795\" class=\"mjx-mn\"><span class=\"mjx-char MJXc-TeX-main-R\" style=\"padding-top: 0.372em; padding-bottom: 0.372em;\">3</span></span><span class=\"mjx-strut\"></span></span></span></span></span></span></span></span><span id=\"MJXc-Node-18796\" class=\"mjx-mo\"><span class=\"mjx-char MJXc-TeX-size3-R\" style=\"padding-top: 1.256em; padding-bottom: 1.256em;\">)</span></span></span></span></span></span></span></p>";
//        questionsText.loadData(question, "text/html; charset=utf-8", "utf-8");
//        laTexView = (MathJaxView) view.findViewById(R.id.laTexView);
//        laTexView.setInputText("\\[\\begin{array}{l}\n" +
//                "Fin\\,the\\,value\\,of\\,x\\,,\\,if:\\\\\n" +
//                "\\left( {\\begin{array}{*{20}{c}}\n" +
//                "{3x + y}&{ - y}\\\\\n" +
//                "{2y - 3}&3\n" +
//                "\\end{array}} \\right) = \\left( {\\begin{array}{*{20}{c}}\n" +
//                "1&2\\\\\n" +
//                "{ - 5}&3\n" +
//                "\\end{array}} \\right)\n" +
//                "\\end{array}\\]");
        laTexView = (WebView) view.findViewById(R.id.laTexView);
        laTexView.getSettings().setJavaScriptEnabled(true);
        laTexView.getSettings().setSupportZoom(true);
        final String path="file:///android_asset/";

        laTexView.loadDataWithBaseURL(path, mParam1,  "text/html",  "UTF-8", null);

//        laTexView.setInputText(question);

//        Document data = Jsoup.parse(question);
//        data.select("p");
//        Element element = Jsoup.parse(question);
//        Elements elements = element.getElementsByTag("body");
//        for (int i = 0; i < elements.size(); i++) {
//            Log.e(TAG, "onViewCreated: "+ data.getElementsByTag(elements.get(i).tag().toString()));
//
//        }
//        for (Element element1 : elements) {
//            Log.e(TAG, "onViewCreated: " + data.select("h2").text());
//            //Log.e(TAG, "onViewCreated: " + element1.getElementsByTag(element1.tag().getName()));
//        }
        /*List<Question> question1 = (List<Question>) JP.fromList(element, Question.class);
        //Log.e(TAG, "onViewCreated: " + question1.getName());
        Log.e(TAG, "onViewCreated: " + elements.size());*/
    }

    @ForEach("p")
    void iterate(Element element, int index) {
        // [required] element
        // [optional] index
        // do something
    }



}
