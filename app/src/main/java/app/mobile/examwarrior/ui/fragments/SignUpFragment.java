package app.mobile.examwarrior.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.HttpURLConnection;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.model.LoginBody;
import app.mobile.examwarrior.model.RegistrationResponse;
import app.mobile.examwarrior.model.SignUpBody;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.ui.activity.RegistrationActivity;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.util.validator.FormError;
import app.mobile.examwarrior.util.validator.FormField;
import app.mobile.examwarrior.util.validator.MagicForm;
import app.mobile.examwarrior.util.validator.Validation;
import app.mobile.examwarrior.util.validator.ValidationMode;
import app.mobile.examwarrior.util.validator.ValidationNotEmpty;
import app.mobile.examwarrior.util.validator.ValidatorCallbacks;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, OTPDialogFragment.OnFragmentInteractionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AppCompatEditText inputEmail;
    private AppCompatEditText inputPassword;
    private AppCompatEditText inputConfirmPassword;
    private AppCompatButton btnRegister;
    private AppCompatTextView linkSignin;
    private RegistrationActivity registrationActivity;
    private MagicForm magicForm;
    private AppCompatEditText inputUserId;
    private AppCompatEditText inputDisplayName;
    private AppCompatEditText inputMobileNumber;
    private Uri mCropImageUri = null;
    private AppCompatImageView profileImage;
    private Call<RegistrationResponse> registration;
    private AppCompatSpinner user_role;
    private Call<User> loginResponse;
    private AlertDialog alertDialogInstance;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        magicForm = new MagicForm(ValidationMode.ON_FOCUS_CHANGE);
        inputEmail = (AppCompatEditText) view.findViewById(R.id.input_email);
        inputPassword = (AppCompatEditText) view.findViewById(R.id.input_password);
        inputConfirmPassword = (AppCompatEditText) view.findViewById(R.id.input_confirm_password);
        btnRegister = (AppCompatButton) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        linkSignin = (AppCompatTextView) view.findViewById(R.id.link_signin);
        linkSignin.setOnClickListener(this);
        inputUserId = (AppCompatEditText) view.findViewById(R.id.input_user_id);
        inputDisplayName = (AppCompatEditText) view.findViewById(R.id.input_display_name);
        inputMobileNumber = (AppCompatEditText) view.findViewById(R.id.input_mobile_number);
        profileImage = (AppCompatImageView) view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);
        user_role = (AppCompatSpinner) view.findViewById(R.id.user_role);
    }

    @Override
    public void onClick(View v) {
        if (getContext() == null) return;
        switch (v.getId()) {
            case R.id.link_signin:
                ((RegistrationActivity) getContext()).changePage(1);
                break;
            case R.id.btn_register:
                // popActivationCodeDialog();
                validateRegistrationForm();
                break;
            case R.id.profile_image:
                break;
        }
    }

    /**
     * Form Validation
     */
    private void validateRegistrationForm() {
        magicForm = new MagicForm(ValidationMode.ON_FOCUS_CHANGE)
                // username validator
                .addField(new FormField(inputEmail)
                        .addValidation(new ValidationNotEmpty().setMessage("Enter username")))
                //password validator
                .addField(new FormField(inputPassword).addValidation(new ValidationNotEmpty().setMessage("Enter password")))
                .addField(new FormField(inputConfirmPassword).addValidation(new ValidationNotEmpty().setMessage("Confirm your password")).addValidation(new Validation() {
                    @Override
                    public boolean isValid(View view) {
                        return view instanceof AppCompatEditText && inputConfirmPassword.getText().toString().trim().equalsIgnoreCase(inputPassword.getText().toString().trim());
                    }
                }.setMessage("Password doesn't match")))
                .setListener(new ValidatorCallbacks() {
                    @Override
                    public void onSuccess() {
                        initUserRegistration();
                    }

                    @Override
                    public void onFailed(List<FormError> errors) {

                    }
                });
        magicForm.validate();
    }

    /**
     * Init registration API
     */
    private void initUserRegistration() {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        if (registrationActivity != null)
            registrationActivity.updateProgressDialog(registrationActivity, null, true);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        registration = apiInterface.userRegistration(new SignUpBody(inputUserId.getText().toString().trim()
                , inputDisplayName.getText().toString().trim()
                , inputEmail.getText().toString().trim()
                , inputMobileNumber.getText().toString().trim()
                , user_role.getSelectedItem().toString()
                , inputPassword.getText().toString()));
        registration.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        popActivationCodeDialog();
                        break;
                    default:
                        if (response.body() != null) Utility.showMessage(response.body().getErr());
                        break;
                }

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Utility.showMessage("Error while registration");
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
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
        if (registration != null) registration.cancel();
    }

    @Override
    public void onFragmentInteraction(String otp) {
        initLogin(otp);
    }

    /***
     * Login user
     */
    private void initLogin(String otp) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        if (registrationActivity != null)
            registrationActivity.updateProgressDialog(registrationActivity, null, true);
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        loginResponse = apiInterface.userLogin(new LoginBody(inputUserId.getText().toString().trim(), otp));
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
                                    realm.insertOrUpdate(response.body());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    Utility.showMessage("Navigate to home screen.");
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable throwable) {

                                }
                            });
                        } catch (Exception e) {

                        } finally {
                            realm.close();
                        }
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        Utility.showMessage("Username or password is wrong");
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (registrationActivity != null)
                    registrationActivity.updateProgressDialog(registrationActivity, null, false);
                Utility.showMessage("Error");
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

}
