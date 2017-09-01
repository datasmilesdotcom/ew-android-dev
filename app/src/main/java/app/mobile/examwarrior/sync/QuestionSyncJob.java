package app.mobile.examwarrior.sync;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.app_controller.AppController;
import app.mobile.examwarrior.database.TestStats;
import app.mobile.examwarrior.demo.Fields;
import app.mobile.examwarrior.demo.SyncResponse;
import app.mobile.examwarrior.demo.SyncTable;
import app.mobile.examwarrior.sync.db.LocalSyncDB;
import app.mobile.examwarrior.util.Utility;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sandesh on 22/8/17, 1:17 PM.
 */

public class QuestionSyncJob extends Job {

    public static final String TAG = QuestionSyncJob.class.getSimpleName();
    private static JobRequest jobRequest;

    public QuestionSyncJob() {
        super();
    }

    /**
     * Schedule Question related jobs
     *
     * @param extras
     */
    public static void scheduleJob(@NonNull PersistableBundleCompat extras) {
        jobRequest = new JobRequest.Builder(SyncTag.QuestionSyncTAG)
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
            String targetId = Long.toString(extras.getLong(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID, 0));
            updateLocalDB(jobId, tableName, targetId);
        }

        Log.e(TAG, "scheduleJob: " + jobId);
    }


    private static void updateLocalDB(final int jobId, final String table_name, final String id) {
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

    @Override
    protected void onReschedule(int newJobId) {
        Log.e(TAG, "onReschedule: " + newJobId);
        super.onReschedule(newJobId);
    }

    @Override
    protected boolean isRequirementChargingMet() {
        return super.isRequirementChargingMet();
    }

    @Override
    protected boolean isRequirementDeviceIdleMet() {
        return super.isRequirementDeviceIdleMet();
    }

    @Override
    protected boolean isRequirementNetworkTypeMet() {
        Log.e(TAG, "isRequirementNetworkTypeMet: ");
        return super.isRequirementNetworkTypeMet();
    }

    @Override
    protected boolean isRequirementStorageNotLowMet() {
        return super.isRequirementStorageNotLowMet();
    }

    @Override
    protected boolean isRequirementBatteryNotLowMet() {
        return super.isRequirementBatteryNotLowMet();
    }


    private void syncTableToServer(Params params) {
        long targetId = 0L;
        if (params.getExtras().containsKey(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME) && params.getExtras().containsKey(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID)) {
            String tableName = params.getExtras().getString(SyncTag.BUNDLE_KEYS.KEY_TABLE_NAME, "");
            targetId = params.getExtras().getLong(SyncTag.BUNDLE_KEYS.KEY_TARGET_ID, 0);
        }
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        SyncTable syncTable = new SyncTable();
        syncTable.setJob_id(params.getId());
        syncTable.setPrimary_key("c1");
        syncTable.setTablename("ew_test_sync");
        syncTable.setUsr_id("Sandesh");

        final List<Fields> fields = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            TestStats data = realm.where(TestStats.class).equalTo("_id", targetId).findFirst();
            RealmResults<TestStats> d = realm.where(TestStats.class).findAll();
            Log.e(TAG, "syncTableToServer: " + d.size());
            if (data != null && data.isValid()) {
                Fields field = new Fields();
                // field.setC1(data.getChosen_answer() + data.getJob_id());
                field.setStatus(false);
                fields.add(field);
            }
        } catch (Exception e) {

        } finally {
            realm.close();
        }

        //    syncTable.setFields(fields);
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
                        //  Log.e(TAG, "onResponse: Success " + fields1.getC1() + " " + fields1.getStatus());
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
