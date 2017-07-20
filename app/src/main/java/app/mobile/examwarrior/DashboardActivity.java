package app.mobile.examwarrior;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import app.mobile.examwarrior.prefs.AppPref;
import app.mobile.examwarrior.ui.activity.HomeActivity;
import app.mobile.examwarrior.ui.app_intro.AppIntroActivity;
import app.mobile.examwarrior.util.Utility;

public class DashboardActivity extends AppCompatActivity {

    public static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            new TedPermission(DashboardActivity.this)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            redirect();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> arrayList) {
                            Utility.showMessage("Please grant permission to use this service.");
                        }
                    })
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE).check();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onCreate: " + e.getMessage());
        }
    }

    private void redirect() {
        if (AppPref.getInstance().isSplashShown()) {
            startActivity(new Intent(DashboardActivity.this, HomeActivity.class));
        } else {
            AppPref.getInstance().setSplashShown(true);
            startActivity(new Intent(DashboardActivity.this, AppIntroActivity.class));
        }

        this.finish();
    }
}
