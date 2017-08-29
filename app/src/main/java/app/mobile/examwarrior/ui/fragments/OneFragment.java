package app.mobile.examwarrior.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.AllQuestion;
import app.mobile.examwarrior.database.StartUserExam;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class OneFragment extends Fragment {
    GetPosition mGetDPosition;
    Context mContext;
    private Realm realm;
    private ListView lstQues;

    public static OneFragment newInstance() {
        OneFragment fragment = new OneFragment();
        return fragment;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        realm = Realm.getDefaultInstance();
        mGetDPosition = (GetPosition) mContext;

        lstQues = view.findViewById(R.id.lstQues);

        RealmResults<StartUserExam> startUserExams = realm.where(StartUserExam.class).findAll();
        if (startUserExams.size() > 0) {
            SliderQuestionAdapter mSliderQuestionsAdapter = new SliderQuestionAdapter(startUserExams.get(0).getAllQuestions());
            lstQues.setAdapter(mSliderQuestionsAdapter);
        }
        return view;
    }

    public interface GetPosition {
        void getData(int i);
    }

    static class ViewHolderItem {
        TextView textViewItem;
    }

    public class SliderQuestionAdapter extends BaseAdapter {
        private RealmList<AllQuestion> allQuestions = null;

        SliderQuestionAdapter(RealmList<AllQuestion> allQuestions) {
            this.allQuestions = allQuestions;
        }

        @Override
        public int getCount() {
            return allQuestions.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            ViewHolderItem viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (getActivity()).getLayoutInflater();
                convertView = inflater.inflate(R.layout.question_list_row, viewGroup, false);
                viewHolder = new ViewHolderItem();
                viewHolder.textViewItem = convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderItem) convertView.getTag();
            }
            AllQuestion question = allQuestions.get(position);
            if (question != null) {
                viewHolder.textViewItem.setText((position + 1) + ". " + Html.fromHtml(question.getOriginalQuestion()));
                viewHolder.textViewItem.setTag(question.getQuestionId());
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGetDPosition.getData(position);
                }
            });

            return convertView;
        }
    }

}
