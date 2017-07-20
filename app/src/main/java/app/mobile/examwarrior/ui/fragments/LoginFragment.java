package app.mobile.examwarrior.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.model.LoginBody;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.ui.activity.HomeActivity;
import app.mobile.examwarrior.ui.activity.RegistrationActivity;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.util.validator.FormError;
import app.mobile.examwarrior.util.validator.FormField;
import app.mobile.examwarrior.util.validator.MagicForm;
import app.mobile.examwarrior.util.validator.ValidationMode;
import app.mobile.examwarrior.util.validator.ValidationNotEmpty;
import app.mobile.examwarrior.util.validator.ValidatorCallbacks;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AppCompatEditText inputEmail;
    private AppCompatEditText inputPassword;
    private AppCompatButton btnLogin;
    private AppCompatTextView linkForgetPassword;
    private AppCompatTextView linkSignup;
    private MagicForm magicForm;
    private RegistrationActivity registrationActivity;
    private Call<User> loginResponse;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get External storage name
     *
     * @return
     */
    public static String getFilename(String folderName, String imageName) {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), folderName);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            file.delete();
        }
        String uriSting = (file.getAbsolutePath() + "/" + imageName);
        return uriSting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(User.class);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        } finally {
            realm.close();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEmail = (AppCompatEditText) view.findViewById(R.id.input_email_);
        inputPassword = (AppCompatEditText) view.findViewById(R.id.input_password_);
        btnLogin = (AppCompatButton) view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        linkForgetPassword = (AppCompatTextView) view.findViewById(R.id.link_forget_password);
        linkForgetPassword.setOnClickListener(this);
        linkSignup = (AppCompatTextView) view.findViewById(R.id.link_signup);
        linkSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (getContext() == null) return;
        switch (v.getId()) {
            case R.id.btn_login:
                validateData();
                break;
            case R.id.link_forget_password:
                ((RegistrationActivity) getContext()).changePage(0);
                break;
            case R.id.link_signup:
                ((RegistrationActivity) getContext()).changePage(2);
                break;
        }
    }

    /**
     * Data validation login
     */
    private void validateData() {
        String nameRegex = "^[\\p{L}]++(?:[',.-][\\p{L}]++)*+$";
        magicForm = new MagicForm(ValidationMode.ON_FOCUS_CHANGE)
                // username validator
                .addField(new FormField(inputEmail)
                        .addValidation(new ValidationNotEmpty().setMessage("Enter username")))
                //password validator
                .addField(new FormField(inputPassword).addValidation(new ValidationNotEmpty().setMessage("Enter password")))
                .setListener(new ValidatorCallbacks() {
                    @Override
                    public void onSuccess() {
                        initLogin();
                    }

                    @Override
                    public void onFailed(List<FormError> errors) {

                    }
                });
        magicForm.validate();
    }

    /***
     * Login user
     */
    private void initLogin() {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        if (registrationActivity != null)
            registrationActivity.updateProgressDialog(registrationActivity, null, true);
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        loginResponse = apiInterface.userLogin(new LoginBody(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim()));
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

                                    getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
                                    getActivity().finish();

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
        //registrationActivity.startActivity(new Intent(registrationActivity, MainHomeActivity.class));

        /*DownloadManager dm = (DownloadManager) registrationActivity.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse("https://s3.ap-south-1.amazonaws.com/ew1videos/import-targets.mp4"));
        request.setDestinationInExternalFilesDir(registrationActivity, registrationActivity.getExternalFilesDir(null).getAbsolutePath()
                , "AManager.mp4");
        request.setVisibleInDownloadsUi(true);
        long enqueue = dm.enqueue(request);
        Cursor cursor = dm.query(new DownloadManager.Query().setFilterById(enqueue));
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            Log.e("TAG", "initLogin: " + status);
            int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int reason = cursor.getInt(columnReason);
            // your stuff here, recording the status and reason to some in memory map
        }*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.registrationActivity = (RegistrationActivity) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loginResponse != null) loginResponse.cancel();
    }
}
