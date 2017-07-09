package app.mobile.examwarrior.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import app.mobile.examwarrior.R;


public class CustomProgressDialogFragment extends DialogFragment {

    public static final String CLASSTAG = CustomProgressDialogFragment.class.getSimpleName();
    // class member
    private Dialog dialog;
    private String text;

    public static CustomProgressDialogFragment newInstance(String text, String param2) {
        CustomProgressDialogFragment fragment = new CustomProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putString("", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null) {
            text = getArguments().getString("text", "Please wait...");
        }
    }


    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.pleaseWait));
        dialog = progressDialog;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View root = inflater.inflate(R.layout.progress_dialog, null);
        if (savedInstanceState != null) {
            ((AppCompatTextView) root.findViewById(R.id.textView_progress)).setText(Utility.isEmpty(text) ? "Please wait..." : savedInstanceState.getString("text"));
        }
        //Customizing the dialog features
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        return dialog;

    }

    /**
     * Show Custom Dialog
     *
     * @param fragmentActivity
     */
    public void showCustomDialog(AppCompatActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(this, CLASSTAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    /**
     * Show Custom Dialog
     *
     * @param fragmentActivity
     */
    public void removeCustomDialog(AppCompatActivity fragmentActivity) {
        Dialog dialog = getDialog();
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(this);
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setOnDismissListener(null);
        super.onDestroyView();
    }

}