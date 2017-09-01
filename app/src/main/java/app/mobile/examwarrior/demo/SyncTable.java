package app.mobile.examwarrior.demo;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class SyncTable {


    @SerializedName("fields")
    @Expose
    private JsonArray fields;
    @SerializedName("user_token")
    @Expose
    private String user_token;
    @SerializedName("primary_key")
    @Expose
    private String primary_key;
    @SerializedName("usr_id")
    @Expose
    private String usr_id;
    @SerializedName("job_id")
    @Expose
    private Integer job_id;
    @SerializedName("tablename")
    @Expose
    private String tablename;

    public JsonArray getFields() {
        return fields;
    }

    public void setFields(JsonArray fields) {
        this.fields = fields;
    }

    public String getPrimary_key() {
        return primary_key;
    }

 /*   public String getApp_update_dt() {
        return app_update_dt;
    }

    public void setApp_update_dt(String app_update_dt) {
        this.app_update_dt = app_update_dt;
    }

    @SerializedName("app_update_dt")
    @Expose
    private String app_update_dt;*/

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

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
}