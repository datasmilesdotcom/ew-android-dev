package app.mobile.examwarrior.sync;

/**
 * Created by sandesh on 22/8/17, 1:29 PM.
 */

public interface SyncTag {

    // Local Sync status constants
    int UPDATED = 1;
    int INPROGRESS = 2;
    int IDEAL = 0;
    int RESCHEDULE = 3;
    // Sync operation tag
    String QuestionSyncTAG = "app.mobile.examwarrior.sync.question_sync_tag";


}