package app.mobile.examwarrior.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatDelegate;


import java.util.Set;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.app_controller.AppController;


public class AppPref {

    // KEYS
    private static AppPref appPref;
    private static Context appContext;

    // class member
    private Editor editor;
    private SharedPreferences preferences;


    /**
     * Initialize object
     */
    @SuppressLint("CommitPrefEdits")
    private AppPref() {
        preferences = appContext.getSharedPreferences(AppController.getInstance().getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Singleton method to get object
     *
     * @return {@link AppPref}
     */
    public static AppPref getInstance() {
        AppPref.appContext = AppController.getAppContext();
        if (appPref != null) {
            return appPref;
        }

        appPref = new AppPref();
        return appPref;
    }

    public synchronized void setValue(String key, String value) {
        editor.putString(key, value).commit();
    }

    public synchronized String getValue(String key) {
        return preferences.getString(key, "");
    }

    public synchronized void expireSession(boolean status) {
    }

    public synchronized boolean isSessionExpire() {
        return false;
    }

    public int getNotificationId() {
        return 0;
    }

    public synchronized void clearValue(String key) {
        editor.remove(key).commit();
    }

    public synchronized void setIntValue(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public synchronized int getIntValue(String key) {
        return preferences.getInt(key, 0);
    }

    public Set<String> getAddress() {
        return preferences.getStringSet("address_key", null);
    }

    public void setAddress(Set<String> address) {
        editor.putStringSet("address_key", address);
        editor.commit();
    }

    /**
     * Is splash shown to user
     *
     * @return
     */
    public boolean isSplashShown() {
        return preferences.getBoolean("is_splash_screen_shown", false);
    }

    public void setSplashShown(boolean splashShown) {
        editor.putBoolean("is_splash_screen_shown", splashShown);
        editor.commit();
    }

    public int getCurrentDayNightMode() {
        return preferences.getInt("day_night_mode", AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    public void setCurrentDayNightMode(int currentDayNightMode) {
        editor.putInt("day_night_mode", currentDayNightMode);
        editor.commit();
    }

    public String getDebugUrl() {
        return preferences.getString("debug_ip_url", "http://52.66.140.39");
    }

    public void setDebugUrl(String splashShown) {
        editor.putString("debug_ip_url", splashShown);
        editor.commit();
    }

    public int getResizeMode() {
        return preferences.getInt("resize_mode", 0);
    }

    public void setResizeMode(int mode) {
        editor.putInt("resize_mode", mode);
        editor.commit();
    }

}
