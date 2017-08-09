package app.mobile.examwarrior.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.app_controller.AppController;


/**
 * Created by root on 5/7/14.
 */
public class Utility {

    public static final String TAG = Utility.class.getSimpleName();
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private final static long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;


    // for avoid creation of object
    private Utility() {
    }

    /**
     * Get path from Uri
     *
     * @param context
     * @param contentURI
     * @return uri
     */
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    /**
     * Show toast message to user
     *
     * @param msg
     */
    public static void showMessage(String msg) {
        if (AppController.getAppContext() != null && !TextUtils.isEmpty(msg))
            Toast.makeText(AppController.getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }

        str = str.trim();
        return TextUtils.isEmpty(str);
    }

    /**
     * Get the device's unique ID(IMEI) that is provided by
     * {@link TelephonyManager}
     *
     * @return
     */
    public static String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) AppController.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) return "";
        String strDeviceId = telephonyManager.getDeviceId();
        return strDeviceId;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return model;
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static long getDateToLong(Date date) {
        return Date.UTC(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
    }

    public static int getSignedDiffInDays(Date beginDate, Date endDate) {
        long beginMS = getDateToLong(beginDate);
        long endMS = getDateToLong(endDate);
        long diff = (endMS - beginMS) / (MILLISECS_PER_DAY);
        return (int) diff;
    }

    public static int getUnsignedDiffInDays(Date beginDate, Date endDate) {
        return Math.abs(getSignedDiffInDays(beginDate, endDate));
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    /**
     * Remove fragment with provided tag
     *
     * @param fragmentActivity
     * @param tag
     */
    public static void removeFragment(FragmentActivity fragmentActivity, String tag) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    /**
     * Hide soft input keyboard
     *
     * @param context
     * @param windowToken
     */
    public static void hideSoftKeyboard(Context context, IBinder windowToken) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show soft input keyboard
     *
     * @param context
     * @param windowToken
     */
    public static void showSoftKeyboard(Context context, IBinder windowToken) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInputFromInputMethod(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Internet connection availability.
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        if (AppController.getAppContext() == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) AppController.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // if no network is available networkInfo will be null, otherwise check if we are connected

        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * Launch GPS configure settings activity
     *
     * @param context
     */
    public static void launchConfigureGPSSettingActivity(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Show Application Version Name
     *
     * @param context
     * @return versionName
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create copy of provided parcelable object
     *
     * @param parcelable
     * @return {@link Parcelable}
     */
    public static Parcelable getClone(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }

        Parcel p = Parcel.obtain();
        p.writeValue(parcelable);
        p.setDataPosition(0);
        Parcelable cloneObj = (Parcelable) p.readValue(Parcelable.class.getClassLoader());
        p.recycle();
        return cloneObj;
    }

    /**
     * Get Date
     *
     * @return {@link String}
     */
    public static String getFormatedDate(Date date, String dateFormate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return dateFormat.format(calendar.getTime());
    }

    /**
     * Get formated number from plan number
     */
    public static String getFormatedNumber(String plan_number, String formate) {

        if (isEmpty(plan_number)) {
            return "";
        }

        StringBuilder s;

        s = new StringBuilder(plan_number);

        if (s.toString().startsWith("1")) {

            if (s.length() > 1) {
                s.insert(1, formate);
            }

            if (s.length() > 5) {
                s.insert(5, formate);
            }

            if (s.length() > 9) {
                s.insert(9, formate);
            }

        } else {

            if (s.length() > 3) {
                s.insert(3, formate);
            }

            if (s.length() > 7) {
                s.insert(7, formate);
            }
        }
        return s.toString();
    }

    /**
     * get plan number from formated number
     */
    public static String getPlanNumber(String formated_number, String formate) {
        if (isEmpty(formated_number)) {
            return "";
        }
        return (formated_number.replace(formate, "")).trim();
    }

    /**
     * perform Vibrate for 1 second
     *
     * @param context
     */
    public static void performVibrate(Context context) {
        // Vibrate for 1 second
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

    }

    /**
     * Check whether the email is a valid email address or not.
     * It verifies the email with regular expression.
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {

        if (isEmpty(email) || email.length() == 0) return false;

		/*String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
						+"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
						+"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
						+"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
						+"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
						+"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";*/
        //String regExpn = "/^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$/";

        Pattern patternObj = Patterns.EMAIL_ADDRESS;

        Matcher matcherObj = patternObj.matcher(email);

        if (matcherObj.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find name from provided url
     *
     * @param url
     * @return fileName
     */
    public static String getFileName(String url) {
        if (Utility.isEmpty(url)) {
            return "";
        }

        int index = url.lastIndexOf("/");
        if (index == -1) {
            return "";
        }

        return url.substring(index + 1);
    }

    /**
     * Check web url is valid or not
     *
     * @param webUrl
     * @return {@link Boolean}
     */
    public static boolean isValidWebUrl(String webUrl) {

        if (isEmpty(webUrl) || webUrl.length() == 0) return false;

        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(webUrl);


        if (m.matches()) {
            return true;
        }

        return false;

    }

    /**
     * Check start date is less than end date
     *
     * @return status
     */
    public static boolean isFirstDateLessThanSecond(String firstDate, String secondDate, String pattarn) {
        if (firstDate != null && secondDate != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattarn);

                Date date1 = sdf.parse(firstDate);
                Date date2 = sdf.parse(secondDate);

                int compResult = date1.compareTo(date2);

                // equal = 0 or greater > 0
                if (compResult < 0) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Convert Bitmap image to Base64
     *
     * @param image
     * @return String
     */
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    /**
     * Convert String to Bitmap Image
     *
     * @param input
     * @return Bitmap
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    /**
     * Get base URL
     *
     * @param context
     * @param url
     * @return
     */
    public static final String BuildRequestUrl(Context context, String url) {
        StringBuilder stringBuilder = new StringBuilder();
        String BaseUrl = BuildConfig.BASE_URL;
        stringBuilder.append(BaseUrl);
        stringBuilder.append(url);
        return stringBuilder.toString().trim();
    }


    public static String getEscapeString(String str) {
        try {
            str = URLEncoder.encode(str, "UTF8");
        } catch (Exception e) {

        }
        return str;

    }

    public static String getDecodeString(String str) {
        try {
            str = URLDecoder.decode(str, "UTF8");
        } catch (Exception e) {

        }
        return str;

    }

    public static String getSizeFromKb(long sizeInKb) {
        String hrSize = "";
        double m = sizeInKb / 1024.0;
        double g = sizeInKb / 1048576.0;
        double t = sizeInKb / 1073741824.0;
        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1) {
            hrSize = dec.format(t).concat("TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat("GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat("MB");
        } else {
            hrSize = dec.format(sizeInKb).concat("KB");
        }

        return hrSize;
    }

}
