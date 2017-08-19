package app.mobile.examwarrior.ui.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.fcannizzaro.jsoup.annotations.interfaces.ForEach;

import org.jsoup.nodes.Element;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.AllQuestion;

public class DisplayQuestionFragment extends Fragment {

    public static final String TAG = DisplayQuestionFragment.class.getSimpleName();

    AllQuestion Question;
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

    public static DisplayQuestionFragment newInstance(AllQuestion Question) {
        DisplayQuestionFragment fragment = new DisplayQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable("question",Question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Question = getArguments().getParcelable("question");
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
        optionOne = (AppCompatTextView) view.findViewById(R.id.option_one);
        optionTwo = (AppCompatTextView) view.findViewById(R.id.option_two);
        optionThree = (AppCompatTextView) view.findViewById(R.id.option_three);
        optionFour = (AppCompatTextView) view.findViewById(R.id.option_four);

        questionsText.getSettings().setJavaScriptEnabled(true);

        laTexView = (WebView) view.findViewById(R.id.laTexView);
        laTexView.getSettings().setJavaScriptEnabled(true);
        laTexView.getSettings().setSupportZoom(true);
        final String path = "file:///android_asset/";

        laTexView.loadDataWithBaseURL(path, Question.getOriginalQuestion(), "text/html", "UTF-8", null);
        optionOne.setText(Html.fromHtml(Question.getOptions().get(0).getOriginalText()));
        optionTwo.setText(Html.fromHtml(Question.getOptions().get(1).getOriginalText()));
        optionThree.setText(Html.fromHtml(Question.getOptions().get(2).getOriginalText()));
        optionFour.setText(Html.fromHtml(Question.getOptions().get(3).getOriginalText()));
    }
}
