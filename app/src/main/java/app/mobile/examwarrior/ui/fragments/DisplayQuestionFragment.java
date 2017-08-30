package app.mobile.examwarrior.ui.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.AllQuestion;
import app.mobile.examwarrior.database.SaveUserExamQuestionData;
import io.realm.Realm;
import io.realm.RealmResults;


public class DisplayQuestionFragment extends Fragment {

    public static final String TAG = DisplayQuestionFragment.class.getSimpleName();

    AllQuestion Question;
    private WebView questionsText;
    private AppCompatTextView optionOne;
    private AppCompatTextView optionTwo;
    private AppCompatTextView optionThree;
    private AppCompatTextView optionFour;
    private CheckBox chkOpt1, chkOpt2, chkOpt3, chkOpt4;
    private String strOpt1 = "", strOpt2 = "", strOpt3 = "", strOpt4 = "";
    private LinearLayout ll_ans1, ll_ans2, ll_ans3, ll_ans4;
    private Realm realm;
    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;

    public DisplayQuestionFragment() {
        // Required empty public constructor
    }

    public static DisplayQuestionFragment newInstance(AllQuestion Question) {
        DisplayQuestionFragment fragment = new DisplayQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable("question", Question);

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

    private void initViews(View view) {
        mSharedPreferences = getActivity().getSharedPreferences("display_question", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
        realm = Realm.getDefaultInstance();


        questionsText = view.findViewById(R.id.questions_text);
        //options = (LinearLayout) view.findViewById(R.id.options);
        optionOne = view.findViewById(R.id.option_one);
        optionTwo = view.findViewById(R.id.option_two);
        optionThree = view.findViewById(R.id.option_three);
        optionFour = view.findViewById(R.id.option_four);
        chkOpt1 = view.findViewById(R.id.chkOpt1);
        chkOpt2 = view.findViewById(R.id.chkOpt2);
        chkOpt3 = view.findViewById(R.id.chkOpt3);
        chkOpt4 = view.findViewById(R.id.chkOpt4);
        ll_ans1 = view.findViewById(R.id.ll_ans1);
        ll_ans2 = view.findViewById(R.id.ll_ans2);
        ll_ans3 = view.findViewById(R.id.ll_ans3);
        ll_ans4 = view.findViewById(R.id.ll_ans4);

        questionsText.getSettings().setJavaScriptEnabled(true);
        questionsText.getSettings().setSupportZoom(true);
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

        initViews(view);

        RealmResults<SaveUserExamQuestionData> saveUserExamQuestionDataRealmResults = realm.where(SaveUserExamQuestionData.class).findAll();
        if (saveUserExamQuestionDataRealmResults.size() > 0) {
            for (int i = 0; i < saveUserExamQuestionDataRealmResults.size(); i++) {
                if (saveUserExamQuestionDataRealmResults.get(i).getQuestionId().equalsIgnoreCase(Question.getQuestionId())) {
                    try {
                        JSONObject mJsonObject = new JSONObject(saveUserExamQuestionDataRealmResults.get(i).getQuestionData());
                        String isSkipped = mJsonObject.getString("isSkipped");
                        String markForReview = mJsonObject.getString("markForReview");
                        String timeSpent = mJsonObject.getString("timeSpent");
                        JSONArray mJsonArray = mJsonObject.getJSONArray("userAnswers");

                        for (int j = 0; j < mJsonArray.length(); j++) {
                            String str = mJsonArray.get(j).toString();

                            for (int k = 0; k < Question.getOptions().size(); k++) {
                                if (str.equalsIgnoreCase(Question.getOptions().get(k).getId())) {
                                    if (k == 0) {
                                        ll_ans1.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                    } else if (k == 1) {
                                        ll_ans2.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                    } else if (k == 2) {
                                        ll_ans3.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                    } else if (k == 3) {
                                        ll_ans4.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        final String path = "file:///android_asset/";
        questionsText.loadDataWithBaseURL(path, Question.getOriginalQuestion(), "text/html", "UTF-8", null);
        optionOne.setText(Html.fromHtml(Question.getOptions().get(0).getOriginalText()));
        optionTwo.setText(Html.fromHtml(Question.getOptions().get(1).getOriginalText()));
        optionThree.setText(Html.fromHtml(Question.getOptions().get(2).getOriginalText()));
        optionFour.setText(Html.fromHtml(Question.getOptions().get(3).getOriginalText()));
        mEditor.commit();


        setListener();

    }

    private void setListener() {

        ll_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strOpt1.equals("")) {
                    strOpt1 = Question.getOptions().get(0).getId();
                    ll_ans1.setBackgroundColor(getResources().getColor(R.color.blue_light));
                } else {
                    strOpt1 = "";
                    ll_ans1.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                mEditor.putString("strOpt1", strOpt1);
                mEditor.commit();
            }
        });

        ll_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strOpt2.equals("")) {
                    strOpt2 = Question.getOptions().get(1).getId();
                    ll_ans2.setBackgroundColor(getResources().getColor(R.color.blue_light));
                } else {
                    strOpt2 = "";
                    ll_ans2.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                mEditor.putString("strOpt4", strOpt4);
                mEditor.commit();
            }
        });

        ll_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strOpt3.equals("")) {
                    strOpt3 = Question.getOptions().get(2).getId();
                    ll_ans3.setBackgroundColor(getResources().getColor(R.color.blue_light));
                } else {
                    strOpt3 = "";
                    ll_ans3.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                mEditor.putString("strOpt3", strOpt3);
                mEditor.commit();
            }
        });

        ll_ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strOpt4.equals("")) {
                    strOpt4 = Question.getOptions().get(3).getId();
                    ll_ans4.setBackgroundColor(getResources().getColor(R.color.blue_light));
                } else {
                    strOpt4 = "";
                    ll_ans4.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                mEditor.putString("strOpt4", strOpt4);
                mEditor.commit();
            }
        });

/*        chkOpt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strOpt1 = Question.getOptions().get(0).getId();
                } else {
                    strOpt1 = "";
                }
                mEditor.putString("strOpt1", strOpt1);
                mEditor.commit();
            }
        });

        chkOpt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strOpt2 = Question.getOptions().get(1).getId();
                } else {
                    strOpt2 = "";
                }
                mEditor.putString("strOpt2", strOpt2);
                mEditor.commit();
            }
        });

        chkOpt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strOpt3 = Question.getOptions().get(2).getId();
                } else {
                    strOpt3 = "";
                }
                mEditor.putString("strOpt3", strOpt3);
                mEditor.commit();
            }
        });

        chkOpt4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strOpt4 = Question.getOptions().get(3).getId();
                } else {
                    strOpt4 = "";
                }
                mEditor.putString("strOpt4", strOpt4);
                mEditor.commit();
            }
        });*/
    }
}
