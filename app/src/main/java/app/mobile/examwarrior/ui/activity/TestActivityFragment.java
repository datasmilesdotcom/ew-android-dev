package app.mobile.examwarrior.ui.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.mobile.examwarrior.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TestActivityFragment extends Fragment {

    public TestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}
