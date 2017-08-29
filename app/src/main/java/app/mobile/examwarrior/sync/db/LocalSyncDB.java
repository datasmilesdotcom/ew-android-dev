package app.mobile.examwarrior.sync.db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sandesh on 22/8/17, 5:43 PM.
 */

public class LocalSyncDB extends RealmObject {

    @PrimaryKey
    @Index
    private int jobId;

    private String jobTag;

    private int status;

    private String targetId;

    private String targetTableName;

    private Date startDate;

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTag() {
        return jobTag;
    }

    public void setJobTag(String jobTag) {
        this.jobTag = jobTag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
