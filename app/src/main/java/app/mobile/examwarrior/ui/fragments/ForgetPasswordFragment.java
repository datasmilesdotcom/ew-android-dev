package app.mobile.examwarrior.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.model.ChangePasswordRequestBody;
import app.mobile.examwarrior.model.ForgetPasswordBody;
import app.mobile.examwarrior.model.ForgetPasswordResponse;
import app.mobile.examwarrior.model.LoginBody;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.ui.activity.RegistrationActivity;
import app.mobile.examwarrior.util.Utility;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AppCompatEditText inputUserId;
    private AppCompatButton btnVerify;
    private AppCompatEditText inputPassword;
    private AppCompatEditText inputConfirmPassword;
    private AppCompatButton btnChangePassword;
    private AlertDialog alertDialogInstance;
    private String userId;
    private String token;

    private RegistrationActivity registrationActivity;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFragment.
     */
    public static ForgetPasswordFragment newInstance(String param1, String param2) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputUserId = (AppCompatEditText) view.findViewById(R.id.input_user_id);
        btnVerify = (AppCompatButton) view.findViewById(R.id.btn_verify);
        inputPassword = (AppCompatEditText) view.findViewById(R.id.input_password);
        inputConfirmPassword = (AppCompatEditText) view.findViewById(R.id.input_confirm_password);
        btnChangePassword = (AppCompatButton) view.findViewById(R.id.btn_change_password);
        btnChangePassword.setOnClickListener(ForgetPasswordFragment.this);
        btnVerify.setOnClickListener(ForgetPasswordFragment.this);
        enableChangePassField(false);
        enableUseridFields(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_verify:
                iniForgetPasswordAPI(inputUserId.getText().toString().trim());
                break;
            case R.id.btn_change_password:
                if (!inputPassword.getText().toString().trim().equalsIgnoreCase(inputConfirmPassword.getText().toString().trim())) {
                    Utility.showMessage("Password doesn't match");
                    return;
                }
                Realm realm = Realm.getDefaultInstance();
                try {
                    enableChangePassField(false);
                    enableUseridFields(false);
                    User user = realm.where(User.class).findFirst();
                    setBtnChangePassword(user.getUsrId(), inputPassword.getText().toString().trim(), user.getToken());
                } catch (Exception e) {
                    Log.e("TAG", "onClick: " + e.getMessage());
                } finally {
                    realm.close();
                }

                break;
        }
    }

    /**
     * Check for Valid userid
     *
     * @param userId
     */
    private void iniForgetPasswordAPI(String userId) {
        if (Utility.isEmpty(userId)) {
            enableChangePassField(false);
            enableUseridFields(true);
            Utility.showMessage("Enter userid");
            return;
        }
        if (!Utility.isNetworkAvailable()) {
            enableChangePassField(false);
            enableUseridFields(true);
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        enableChangePassField(false);
        enableUseridFields(false);
        if (registrationActivity != null)
            registrationActivity.updateProgressDialog(registrationActivity, null, true);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ForgetPasswordResponse> forgetPassword = apiInterface.forgetPassword(new ForgetPasswordBody(userId));
        forgetPassword.enqueue(new Callback<ForgetPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                try {
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_OK:
                            if (response.body().getStatus()) {
                                enableChangePassField(false);
                                enableUseridFields(false);
                                popActivationCodeDialog();
                            } else {
                                enableChangePassField(false);
                                enableUseridFields(true);
                                Utility.showMessage(response.body().getErr());
                            }
                            break;
                        default:
                            enableChangePassField(false);
                            enableUseridFields(true);
                            Utility.showMessage(response.body().getErr());
                            break;
                    }

                } catch (Exception e) {
                    enableChangePassField(false);
                    enableUseridFields(true);
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                enableChangePassField(false);
                enableUseridFields(true);
                Utility.showMessage("Error");
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
            }
        });
    }


    private void setBtnChangePassword(String userid, String password, String token) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "JWT" + token);
        if (registrationActivity != null)
            registrationActivity.updateProgressDialog(registrationActivity, null, true);
        Call<Response> changePassword = apiInterface.changePassword(map, new ChangePasswordRequestBody(userid, password));
        changePassword.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        if (registrationActivity != null)
                            registrationActivity.changePage(1);
                        break;
                    default:
                        Utility.showMessage("Error while changing password.");
                        enableChangePassField(true);
                        enableUseridFields(true);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                enableChangePassField(true);
                enableUseridFields(true);
            }
        });
    }

    // Show popup for OTP
    private void popActivationCodeDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(registrationActivity);
        alertDialog.setTitle("Account Verification");
        alertDialog.setMessage("Enter OTP");

        LayoutInflater layoutInflater = LayoutInflater.from(registrationActivity);
        View view = layoutInflater.inflate(R.layout.enter_otp, null);
        final AppCompatEditText input = (AppCompatEditText) view.findViewById(R.id.otp_input);
        ;
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Verify",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.create();
        alertDialogInstance = alertDialog.show();
        Button theButton = alertDialogInstance.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utility.isEmpty(input.getText().toString().trim())) {
                    initLogin(input.getText().toString().trim());
                    alertDialogInstance.dismiss();
                } else {
                    Utility.showMessage("Please enter OTP");
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.registrationActivity = (RegistrationActivity) context;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //   if (registration != null) registration.cancel();
    }

    /***
     * Login user
     */
    private void initLogin(String otp) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        enableUseridFields(false);
        enableChangePassField(false);
        if (registrationActivity != null)
            registrationActivity.updateProgressDialog(registrationActivity, null, true);
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        Call<User> loginResponse = apiInterface.userLogin(new LoginBody(inputUserId.getText().toString().trim(), otp));
        loginResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        Utility.showMessage("Registration Successful");
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.delete(User.class);
                                    realm.insertOrUpdate(response.body());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    //Enable for change password
                                    enableUseridFields(false);
                                    enableChangePassField(true);

                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable throwable) {
                                    enableUseridFields(true);
                                    enableChangePassField(false);
                                }
                            });
                        } catch (Exception e) {
                            enableUseridFields(true);
                            enableChangePassField(false);
                        } finally {
                            realm.close();
                        }
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        Utility.showMessage("Username is wrong");
                        enableUseridFields(true);
                        enableChangePassField(false);
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                enableUseridFields(true);
                enableChangePassField(false);
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                Utility.showMessage("Error");
            }
        });
    }

    private void enableUseridFields(boolean enable) {
        inputUserId.setEnabled(enable);
        btnVerify.setEnabled(enable);
    }

    private void enableChangePassField(boolean enable) {
        inputPassword.setEnabled(enable);
        inputConfirmPassword.setEnabled(enable);
        btnChangePassword.setEnabled(enable);
    }
}
