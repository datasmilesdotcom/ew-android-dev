package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.FinishUserExam;
import io.realm.Realm;

/**
 * Created by Kunjan on 24-08-2017.
 */

public class DisplayTestResultActivity extends AppCompatActivity {


    private TextView tv_total_que, tv_review_que, tv_skipped_que, tv_attempted_ans, tv_correct_ans, tv_wrong_ans, tv_test_name;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result_test);
        initViews();
        setViews();
    }

    private void setViews() {
        List<FinishUserExam> mfinishUserExam = realm.where(FinishUserExam.class).findAll();

        tv_total_que.setText("" + mfinishUserExam.get(0).getTotalQuestions().size());
        tv_review_que.setText("" + mfinishUserExam.get(0).getReviewQue().size());
        tv_skipped_que.setText("" + mfinishUserExam.get(0).getSkippedQue().size());
        tv_attempted_ans.setText("" + mfinishUserExam.get(0).getAttemptedQue().size());
        tv_correct_ans.setText("" + mfinishUserExam.get(0).getCorrectAns().size());
        tv_wrong_ans.setText("" + mfinishUserExam.get(0).getWrongAns().size());
        tv_test_name.setText("" + mfinishUserExam.get(0).getTestName());
    }

    private void initViews() {
        try {
            realm = Realm.getDefaultInstance();
            tv_total_que = findViewById(R.id.tv_total_que);
            tv_review_que = findViewById(R.id.tv_review_que);
            tv_skipped_que = findViewById(R.id.tv_skipped_que);
            tv_attempted_ans = findViewById(R.id.tv_attempted_ans);
            tv_correct_ans = findViewById(R.id.tv_correct_ans);
            tv_wrong_ans = findViewById(R.id.tv_wrong_ans);
            tv_test_name = findViewById(R.id.tv_test_name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
