package app.mobile.examwarrior.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ModuleDetail extends RealmObject {

    @SerializedName("moduleNumber")
    @Expose
    private Integer moduleNumber;
    @SerializedName("moduleId")
    @Expose
    @PrimaryKey
    private String moduleId;
    @SerializedName("moduleName")
    @Expose
    private String moduleName;
    @SerializedName("moduleDesc")
    @Expose
    private String moduleDesc;
    @SerializedName("moduleItems")
    @Expose
    private RealmList<ModuleItem> moduleItems = null;
    @SerializedName("moduleWeight")
    @Expose
    private double moduleWeight;

    @Ignore
    private int expandStatus = 0;

    public int getExpandStatus() {
        return expandStatus;
    }

    public void setExpandStatus(int expandStatus) {
        this.expandStatus = expandStatus;
    }

    public Integer getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(Integer moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public RealmList<ModuleItem> getModuleItems() {
        return moduleItems;
    }

    public void setModuleItems(RealmList<ModuleItem> moduleItems) {
        this.moduleItems = moduleItems;
    }

    public double getModuleWeight() {
        return moduleWeight;
    }

    public void setModuleWeight(double moduleWeight) {
        this.moduleWeight = moduleWeight;
    }
}
