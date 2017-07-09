package app.mobile.examwarrior.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.util.Utility;

/**
 * Activities that contain this fragment must implement the
 * {@link OTPDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OTPDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OTPDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = OTPDialogFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String userId;
    private String mParam2;
    private AppCompatEditText otp;
    private AppCompatButton verifyOtp;

    private OnFragmentInteractionListener mListener;

    public OTPDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OTPDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OTPDialogFragment newInstance(String param1, String param2) {
        OTPDialogFragment fragment = new OTPDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Dialog);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otpdialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        otp = (AppCompatEditText) view.findViewById(R.id.otp);
        verifyOtp = (AppCompatButton) view.findViewById(R.id.verify_otp);
        verifyOtp.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Less dimmed background; see http://stackoverflow.com/q/13822842/56285
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.2f; // dim only a little bit
        window.setAttributes(params);

        // Transparent background; see http://stackoverflow.com/q/15007272/56285
        // (Needed to make dialog's alpha shadow look good)
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_otp:
                String otpText = otp.getText().toString().trim();
                if (Utility.isEmpty(otpText)) {
                    Utility.showMessage("Please enter OTP");
                    return;
                }
                if (mListener != null) {
                    mListener.onFragmentInteraction(otpText);
                    dismiss();
                }
                break;
        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String otp);
    }
}
