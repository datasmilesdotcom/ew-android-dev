package app.mobile.examwarrior.sync;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;


/**
 * Created by sandesh on 22/8/17, 12:58 PM.
 */

public class SyncManager implements JobCreator {
    public static final String TAG__ = SyncManager.class.getSimpleName();

    @Override
    public Job create(String tag) {

        switch (tag) {
            case SyncTag.QuestionSyncTAG:
                return new QuestionSyncJob();
            default:
                return null;
        }
    }

}
