package app.mobile.examwarrior.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ModuleItem extends RealmObject implements Parcelable {

    public static final Creator<ModuleItem> CREATOR = new Creator<ModuleItem>() {
        @Override
        public ModuleItem createFromParcel(Parcel source) {
            return new ModuleItem(source);
        }

        @Override
        public ModuleItem[] newArray(int size) {
            return new ModuleItem[size];
        }
    };
    @SerializedName("itemId")
    @Expose
    @PrimaryKey
    private String itemId;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemWeight")
    @Expose
    private double itemWeight;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("itemNumber")
    @Expose
    private Integer itemNumber;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("itemContainsSlide")
    @Expose
    private Boolean itemContainsSlide;
    @SerializedName("materialInfo")
    @Expose
    private String materialInfo;
    @SerializedName("itemTypeId")
    @Expose
    private String itemTypeId;

    public ModuleItem() {
    }

    protected ModuleItem(Parcel in) {
        this.itemId = in.readString();
        this.itemName = in.readString();
        this.itemWeight = in.readDouble();
        this.itemDescription = in.readString();
        this.itemNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.itemType = in.readString();
        this.itemContainsSlide = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.materialInfo = in.readString();
        this.itemTypeId = in.readString();
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Boolean getItemContainsSlide() {
        return itemContainsSlide;
    }

    public void setItemContainsSlide(Boolean itemContainsSlide) {
        this.itemContainsSlide = itemContainsSlide;
    }

    public String getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(String materialInfo) {
        this.materialInfo = materialInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeDouble(this.itemWeight);
        dest.writeString(this.itemDescription);
        dest.writeValue(this.itemNumber);
        dest.writeString(this.itemType);
        dest.writeValue(this.itemContainsSlide);
        dest.writeString(this.materialInfo);
        dest.writeString(this.itemTypeId);
    }
}
