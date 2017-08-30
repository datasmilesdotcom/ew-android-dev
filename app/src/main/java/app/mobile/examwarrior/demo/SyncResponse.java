package app.mobile.examwarrior.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class SyncResponse {
    @SerializedName("usr_id")
    @Expose
    private String usr_id;
    @SerializedName("job_id")
    @Expose
    private Integer job_id;
    @SerializedName("tablename")
    @Expose
    private String tablename;
    @SerializedName("fields")
    @Expose
    private List<Fields> fields;
    @SerializedName("primary_key")
    @Expose
    private String primary_key;

    public String getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public List<Fields> getFields() {
        return fields;
    }

    public void setFields(List<Fields> fields) {
        this.fields = fields;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }
}