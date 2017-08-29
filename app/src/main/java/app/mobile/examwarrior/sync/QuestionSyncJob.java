package app.mobile.examwarrior.sync;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.mobile.examwarrior.sync.db.LocalSyncDB;
import io.realm.Realm;

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
    public static void scheduleJob(PersistableBundleCompat extras) {
        jobRequest = new JobRequest.Builder(SyncTag.QuestionSyncTAG)
                .setExecutionWindow(1_000L, 2_000L)
                .setExact(1000l)
                .setUpdateCurrent(false)
                .setPersisted(true)
                .setExtras(extras)
                .setRequirementsEnforced(false)
                .setRequiredNetworkType(JobRequest.NetworkType.ANY)
                .build();
        int jobId = jobRequest.schedule();
        updateLocalDB(jobId);
        Log.e(TAG, "scheduleJob: " + jobId);
    }


    private static void updateLocalDB(final int jobId) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    LocalSyncDB localSyncDB = realm.where(LocalSyncDB.class).equalTo("jobId", jobId).findFirst();
                    if (localSyncDB == null || !localSyncDB.isValid())
                        localSyncDB = new LocalSyncDB();
                    localSyncDB.setJobId(jobId);
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
        Log.e(TAG, "onRunJob: " + params.getId() + " " + params.getLastRun());
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
    protected ComponentName startWakefulService(@NonNull Intent intent) {
        return super.startWakefulService(intent);
    }
}
