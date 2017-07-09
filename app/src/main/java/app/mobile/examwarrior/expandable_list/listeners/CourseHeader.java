package app.mobile.examwarrior.expandable_list.listeners;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.expandable_list.model.ExpandableGroup;

/**
 * Created by sandesh on 5/5/17, 2:21 AM.
 */

public class CourseHeader extends ExpandableGroup<ModuleItem> implements Parcelable {

    private String title;
    private List<ModuleItem> items;
    private String image;


    public CourseHeader(String title, String image, List<ModuleItem> items) {
        super(title, items);
        this.title = title;
        this.items = items;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<ModuleItem> getItems() {
        return items;
    }

    public void setItems(List<ModuleItem> items) {
        this.items = items;
    }
}
