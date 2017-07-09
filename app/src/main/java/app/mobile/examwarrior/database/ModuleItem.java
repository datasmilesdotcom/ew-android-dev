package app.mobile.examwarrior.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ModuleItem extends RealmObject implements Parcelable {

    public ModuleItem() {
    }

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
    @SerializedName("itemVideo")
    @Expose
    private String itemVideo;
    @SerializedName("itemContainsSlide")
    @Expose
    private Boolean itemContainsSlide;
    @SerializedName("materialInfo")
    @Expose
    private String materialInfo;

    protected ModuleItem(Parcel in) {
        itemId = in.readString();
        itemName = in.readString();
        itemWeight = in.readDouble();
        itemDescription = in.readString();
        if (in.readByte() == 0) {
            itemNumber = null;
        } else {
            itemNumber = in.readInt();
        }
        itemType = in.readString();
        itemVideo = in.readString();
        byte tmpItemContainsSlide = in.readByte();
        itemContainsSlide = tmpItemContainsSlide == 0 ? null : tmpItemContainsSlide == 1;
        materialInfo = in.readString();
    }

    public static final Creator<ModuleItem> CREATOR = new Creator<ModuleItem>() {
        @Override
        public ModuleItem createFromParcel(Parcel in) {
            return new ModuleItem(in);
        }

        @Override
        public ModuleItem[] newArray(int size) {
            return new ModuleItem[size];
        }
    };

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

    public String getItemVideo() {
        return itemVideo;
    }

    public void setItemVideo(String itemVideo) {
        this.itemVideo = itemVideo;
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
        dest.writeString(itemId);
        dest.writeString(itemName);
        dest.writeDouble(itemWeight);
        dest.writeString(itemDescription);
        if (itemNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemNumber);
        }
        dest.writeString(itemType);
        dest.writeString(itemVideo);
        dest.writeByte((byte) (itemContainsSlide == null ? 0 : itemContainsSlide ? 1 : 2));
        dest.writeString(materialInfo);
    }
}
