package app.mobile.examwarrior.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class TutorsResponse {
    @SerializedName("tutors")
    @Expose
    private List<Tutors> tutors;
    @SerializedName("_id")
    @Expose
    private String _id;

    public List<Tutors> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutors> tutors) {
        this.tutors = tutors;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}