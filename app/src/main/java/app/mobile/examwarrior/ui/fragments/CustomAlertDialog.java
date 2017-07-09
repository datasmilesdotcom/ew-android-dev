package app.mobile.examwarrior.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sandesh on 10/2/17.
 */

public class CustomAlertDialog extends DialogFragment {

    public static final String CLASSTAG = CustomAlertDialog.class.getSimpleName();
    public static final String REQUEST_CODE = "app.mobile.examwarrior.dialog.request_code";
    public static final String DIALOG_TITLE = "app.mobile.examwarrior.dialog.title";
    public static final String DIALOG_MESSAGE = "dialog.message";
    public static final String POSITIVE_BUTTON_TEXT = "dialog.positive.button.text";
    public static final String NEGATIVE_BUTTON_TEXT = "dialog.negative.button.text";
    public static final String DIALOG_SHOW_NIGATIVE_BUTTON = "app.mobile.examwarrior.dialog.nigativebutton";
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    // class member
    private Dialog dialog;
    private String title;
    private String message;
    private String postitiveText = "OK";
    private String negativeText = "CANCEL";
    private boolean showNegativeButton = true;
    private int requestCode = -1;

    /**
     * CustomAlertDialog
     *
     * @param title_
     * @param message_
     * @param postivebuttontext
     * @param negativebuttontext
     * @param request_code
     * @param showNegativeButton_
     * @return
     */
    public static CustomAlertDialog newInstance(String title_, String message_, String postivebuttontext, String negativebuttontext, int request_code, boolean showNegativeButton_) {
        CustomAlertDialog fragment = new CustomAlertDialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title_);
        args.putString(DIALOG_MESSAGE, message_);
        args.putString(POSITIVE_BUTTON_TEXT, postivebuttontext);
        args.putString(NEGATIVE_BUTTON_TEXT, negativebuttontext);
        args.putInt(REQUEST_CODE, request_code);
        args.putBoolean(DIALOG_SHOW_NIGATIVE_BUTTON, showNegativeButton_);
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            title = getArguments().getString(DIALOG_TITLE, "Alert");
            message = getArguments().getString(DIALOG_MESSAGE, "Please wait...");
            postitiveText = getArguments().getString(POSITIVE_BUTTON_TEXT, "OK");
            negativeText = getArguments().getString(NEGATIVE_BUTTON_TEXT, "CANCEL");
            requestCode = getArguments().getInt(REQUEST_CODE, -1);
            showNegativeButton = getArguments().getBoolean(DIALOG_SHOW_NIGATIVE_BUTTON, true);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Customizing the dialog features
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);

        // Add the buttons
        builder.setPositiveButton(postitiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // Send the positive button event back to the host activity
                if (mListener != null) mListener.onDialogPositiveClick(CustomAlertDialog.this);
            }
        });
        if (showNegativeButton) {
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    // Send the negative button event back to the host activity
                    if (mListener != null) mListener.onDialogNegativeClick(CustomAlertDialog.this);
                }
            });
        }
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        return dialog;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    /**
     * Show Custom Dialog
     *
     * @param fragmentActivity
     */
    public void showCustomDialog(AppCompatActivity fragmentActivity) {
        android.support.v4.app.FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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

            android.support.v4.app.FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);
    }
}
