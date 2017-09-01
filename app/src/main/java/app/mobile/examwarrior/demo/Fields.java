package app.mobile.examwarrior.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Fields {
    @SerializedName("app_table_pk")
    @Expose
    private String app_table_pk;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getApp_table_pk() {
        return app_table_pk;
    }

    public void setApp_table_pk(String app_table_pk) {
        this.app_table_pk = app_table_pk;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}