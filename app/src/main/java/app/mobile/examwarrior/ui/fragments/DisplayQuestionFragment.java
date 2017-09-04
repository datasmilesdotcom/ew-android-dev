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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.AllQuestion;
import app.mobile.examwarrior.database.SaveUserExamQuestionData;
import app.mobile.examwarrior.listener.UserAnswers;
import io.realm.Realm;
import io.realm.RealmResults;
import katex.hourglass.in.mathlib.MathView;

public class DisplayQuestionFragment extends Fragment {

    public static final String TAG = DisplayQuestionFragment.class.getSimpleName();

    AllQuestion Question;
    TextView tvAnsTime;
    MathView questions_text1, optionOne1, optionTwo1, optionThree1, optionFour1;
    UserAnswers mUserAnswers;
    private WebView questionsText;
    private AppCompatTextView optionOne;
    private AppCompatTextView optionTwo;
    private AppCompatTextView optionThree;
    private AppCompatTextView optionFour;
    private String strOpt1 = "", strOpt2 = "", strOpt3 = "", strOpt4 = "";
    private LinearLayout ll_ans1, ll_ans2, ll_ans3, ll_ans4;
    private Realm realm;
    private int position;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Question = getArguments().getParcelable("question");
        }
    }


    private void initViews(View view) {

        mUserAnswers = new UserAnswers();
        realm = Realm.getDefaultInstance();
        tvAnsTime = view.findViewById(R.id.tvAnsTime);
        questionsText = view.findViewById(R.id.questions_text);
        questionsText.setVisibility(View.GONE);
        questions_text1 = view.findViewById(R.id.questions_text1);

        optionOne1 = view.findViewById(R.id.option_one1);
        optionTwo1 = view.findViewById(R.id.option_two1);
        optionThree1 = view.findViewById(R.id.option_three1);
        optionFour1 = view.findViewById(R.id.option_four1);

        optionOne = view.findViewById(R.id.option_one);
        optionTwo = view.findViewById(R.id.option_two);
        optionThree = view.findViewById(R.id.option_three);
        optionFour = view.findViewById(R.id.option_four);

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
        RealmResults<SaveUserExamQuestionData> results = realm.where(SaveUserExamQuestionData.class).findAll();
        if (results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("get question_id : " + results.get(i).getQuestionId());
                if (results.get(i).getQuestionId().equalsIgnoreCase(Question.getQuestionId())) {
                    try {
                        System.out.println("compare question_id : " + results.get(i).getQuestionId());
                        JSONObject mJsonObject = new JSONObject(results.get(i).getQuestionData());
                        JSONArray mJsonArray = mJsonObject.getJSONArray("userAnswers");

                        for (int j = 0; j < mJsonArray.length(); j++) {
                            String str = mJsonArray.get(j).toString();

                            for (int k = 0; k < Question.getOptions().size(); k++) {
                                if (str.equalsIgnoreCase(Question.getOptions().get(k).getId())) {
                                    if (k == 0) {
                                        ll_ans1.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                        insertRecords("strOpt1", str);
                                    } else if (k == 1) {
                                        ll_ans2.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                        insertRecords("strOpt2", str);
                                    } else if (k == 2) {
                                        ll_ans3.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                        insertRecords("strOpt3", str);
                                    } else if (k == 3) {
                                        insertRecords("strOpt4", str);
                                        ll_ans4.setBackgroundColor(getResources().getColor(R.color.blue_light));
                                    }
                                }
                            }
                        }
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        final String path = "file:///android_asset/";
        questionsText.loadDataWithBaseURL(path, Question.getOriginalQuestion(), "text/html", "UTF-8", null);
        questions_text1.setDisplayText(Question.getOriginalQuestion());
        Log.v("DisplayQuestion :", "" + String.valueOf(Html.fromHtml(Question.getOptions().get(0).getOriginalText())));
        optionOne1.setDisplayText(String.valueOf(Html.fromHtml(Question.getOptions().get(0).getOriginalText())));
        optionTwo1.setDisplayText(Question.getOptions().get(1).getOriginalText());
        optionThree1.setDisplayText(Question.getOptions().get(2).getOriginalText());
        optionFour1.setDisplayText(Question.getOptions().get(3).getOriginalText());

        optionOne.setText(Html.fromHtml(Question.getOptions().get(0).getOriginalText()));
        optionTwo.setText(Html.fromHtml(Question.getOptions().get(1).getOriginalText()));
        optionThree.setText(Html.fromHtml(Question.getOptions().get(2).getOriginalText()));
        optionFour.setText(Html.fromHtml(Question.getOptions().get(3).getOriginalText()));

        setListener();
    }

    private void getOptionOne() {
        if (strOpt1.equals("")) {
            strOpt1 = Question.getOptions().get(0).getId();
            ll_ans1.setBackgroundColor(getResources().getColor(R.color.blue_light));
        } else {
            strOpt1 = "";
            ll_ans1.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        insertRecords("strOpt1", strOpt1);
    }

    private void getOptionTwo() {
        if (strOpt2.equals("")) {
            strOpt2 = Question.getOptions().get(1).getId();
            ll_ans2.setBackgroundColor(getResources().getColor(R.color.blue_light));
        } else {
            strOpt2 = "";
            ll_ans2.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        insertRecords("strOpt2", strOpt2);
    }

    private void getOptionThree() {
        if (strOpt3.equals("")) {
            strOpt3 = Question.getOptions().get(2).getId();
            ll_ans3.setBackgroundColor(getResources().getColor(R.color.blue_light));
        } else {
            strOpt3 = "";
            ll_ans3.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        insertRecords("strOpt3", strOpt3);
    }

    private void getOptionFour() {
        if (strOpt4.equals("")) {
            strOpt4 = Question.getOptions().get(3).getId();
            ll_ans4.setBackgroundColor(getResources().getColor(R.color.blue_light));
        } else {
            strOpt4 = "";
            ll_ans4.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        insertRecords("strOpt4", strOpt4);
    }

    private void insertRecords(final String key, final String value) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                mUserAnswers.setPosition(getPosition());
                if (key.equalsIgnoreCase("strOpt1")) {
                    mUserAnswers.setStrOption1(value);
                } else if (key.equalsIgnoreCase("strOpt2")) {
                    mUserAnswers.setStrOption2(value);
                } else if (key.equalsIgnoreCase("strOpt3")) {
                    mUserAnswers.setStrOption3(value);
                } else if (key.equalsIgnoreCase("strOpt4")) {
                    mUserAnswers.setStrOption4(value);
                }
                realm.insertOrUpdate(mUserAnswers);
            }
        });
    }

    private void setListener() {

        optionOne1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
        optionTwo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });

        optionThree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });

        optionFour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });

        ll_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOptionOne();
            }
        });

        ll_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOptionTwo();
            }
        });

        ll_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOptionThree();
            }
        });

        ll_ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOptionFour();
            }
        });

    }

}
