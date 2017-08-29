package app.mobile.examwarrior.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.ImageAdapter;
import io.realm.Realm;


public class TwoFragment extends Fragment {

    GetPositionTwo mGetDPosition;
    Context mContext;
    private Realm realm;

    public static TwoFragment newInstance() {
        TwoFragment fragment = new TwoFragment();
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

        View view = inflater.inflate(R.layout.fragment_two, null);
        realm = Realm.getDefaultInstance();
        mGetDPosition = (GetPositionTwo) mContext;
        GridView gridView = view.findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mGetDPosition.getDataTwo(i);
            }
        });

        return view;
    }

    public interface GetPositionTwo {
        void getDataTwo(int i);
    }

}
