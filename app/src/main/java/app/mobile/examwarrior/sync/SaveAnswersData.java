package app.mobile.examwarrior.sync;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.app_controller.AppController;
import app.mobile.examwarrior.database.SaveUserExamQuestionData;
import app.mobile.examwarrior.demo.Fields;
import app.mobile.examwarrior.demo.SyncResponse;
import app.mobile.examwarrior.demo.SyncTable;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.sync.db.LocalSyncDB;
import app.mobile.examwarrior.util.Utility;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kunjan on 30-08-2017.
 */

public class SaveAnswersData extends Job {
    public static final String TAG = SaveAnswersData.class.getSimpleName();
    private static JobRequest jobRequest;

    public static void scheduleJob(@NonNull PersistableBundleCompat extras) {
        jobRequest = new JobRequest.Builder(SyncTag.AnswersSyncTAG)
                .setExecutionWindow(1_000L, 5_000L)
                .setUpdateCurrent(false)
                .setExtras(extras)
                .setRequirementsEnforced(true)
                .setRequiresBatteryNotLow(false)
                .setRequiresDeviceIdle(false)
                .setRequiresStorageNotLow(false)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build();
        int jobId = jobRequest.schedule();
        if (extras.containsKey(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME) && extras.containsKey(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID)) {
            String tableName = extras.getString(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME, "");
            String targetId = extras.getString(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID, "");
            String user_id = extras.getString(SyncTag.BUNDLE_KEYS.KEY_USR_ID, "");
            String time = extras.getString(SyncTag.BUNDLE_KEYS.KEY_TIME, "");
            updateLocalDB(jobId, tableName, targetId, user_id, time);
        }

        Log.e(TAG, "scheduleJob: " + jobId);
    }

    public static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private static void updateLocalDB(final int jobId, final String table_name, final String id, String user_id, String time) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    LocalSyncDB localSyncDB = realm.where(LocalSyncDB.class).equalTo("jobId", jobId).findFirst();
                    if (localSyncDB == null || !localSyncDB.isValid())
                        localSyncDB = new LocalSyncDB();
                    localSyncDB.setJobId(jobId);
                    localSyncDB.setTargetId(id);
                    localSyncDB.setTargetTableName(table_name);
                    localSyncDB.setStartDate(new Date(System.currentTimeMillis()));
                    localSyncDB.setStatus(SyncTag.IDEAL);
                    localSyncDB.setJobTag(TAG);
                    realm.insertOrUpdate(localSyncDB);
                }
            });
        } catch (Exception e) {

        } finally {
            realm.close();
        }
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
        Log.e(TAG, "onRunJob: " + params.getId() + " " + simpleDateFormat.format(new Date(System.currentTimeMillis())));
        syncTableToServer(params);
        return Result.SUCCESS;
    }

    private void syncTableToServer(Params params) {
        String targetId = "";
        String tableName = "";
        if (params.getExtras().containsKey(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME) && params.getExtras().containsKey(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID)) {
            tableName = params.getExtras().getString(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME, "");
            targetId = params.getExtras().getString(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID, "");
        }
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        SyncTable syncTable = new SyncTable();
        syncTable.setJob_id(params.getId());
        syncTable.setPrimary_key(targetId);
        syncTable.setTablename(tableName);

        // syncTable.setApp_update_dt(String.valueOf(System.currentTimeMillis()));
        Realm realm = Realm.getDefaultInstance();
        try {
            User user = realm.where(User.class).findFirst();
            syncTable.setUsr_id(user.getUsrId());
            syncTable.setUser_token(user.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<SaveUserExamQuestionData> data = realm.copyFromRealm(realm.where(SaveUserExamQuestionData.class).findAll());

        Gson gson = new Gson();
        JsonArray array = gson.toJsonTree(data).getAsJsonArray();
        /*for (SaveUserExamQuestionData userExamQuestionData : data) {
            array.add(gson.toJsonTree(userExamQuestionData).getAsJsonObject());
        }*/
        syncTable.setFields(array);
        // Log.e("",""+gson.toJson(syncTable)) ;

        Call<SyncResponse> syncCall = apiInterface.syncTable(Integer.toString(params.getId()), syncTable);
        syncCall.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                Log.e(TAG, "onResponse: " + response.code());
                if (call.request() != null) {
                    for (String headername : call.request().headers().names()) {
                        String jobIdFromHeader = call.request().header("JobID");
                        if (!Utility.isEmpty(jobIdFromHeader)) {
                            updateLocalSyncStatus(Integer.parseInt(jobIdFromHeader), SyncTag.UPDATED);
                        }
                    }
                }
                if (response.isSuccessful()) {
                    for (Fields fields1 :
                            response.body().getFields()) {
                        Log.e(TAG, "onResponse: Success " + fields1.getApp_table_pk() + " " + fields1.getStatus());
                    }
                } else {
                    //AppController.getJobManagerInstance().getJobRequest(Integer.parseInt(call.request().header("JobID"))).schedule();
                    Set<JobRequest> data = AppController.getJobManagerInstance().getAllJobRequestsForTag(QuestionSyncJob.TAG);

                    for (JobRequest jobRequest : data) {
                        Log.e(TAG, "onResponse: " + jobRequest.getJobId());
                    }
                }
            }

            @Override
            public void onFailure(Call<SyncResponse> call, Throwable t) {
                if (call.request() != null) {
                    for (String headername : call.request().headers().names()) {
                        AppController.getJobManagerInstance().getJobRequest(Integer.parseInt(headername)).schedule();
                        Log.e(TAG, "onFailure header : " + call.request().header(headername));
                        String jobIdFromHeader = call.request().header("JobID");
                        if (!Utility.isEmpty(jobIdFromHeader)) {
                            updateLocalSyncStatus(Integer.parseInt(jobIdFromHeader), SyncTag.RESCHEDULE);
                        }

                    }
                }
                if (t instanceof IOException) {
                    Log.e(TAG, "onFailure: NetworkFail");
                    //Add your code for displaying no network connection error

                }
            }
        });

    }

    private void updateLocalSyncStatus(final int jobId, final int status) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    LocalSyncDB localSyncDB = realm.where(LocalSyncDB.class).equalTo("jobId", jobId).findFirst();
                    if (localSyncDB == null || !localSyncDB.isValid())
                        localSyncDB = new LocalSyncDB();
                    localSyncDB.setStatus(status);
                    realm.insertOrUpdate(localSyncDB);
                }
            });
        } catch (Exception e) {

        } finally {
            realm.close();
        }
    }
}
