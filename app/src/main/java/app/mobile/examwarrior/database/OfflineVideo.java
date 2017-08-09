package app.mobile.examwarrior.database;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sandesh on 5/8/17, 7:21 PM.
 */

public class OfflineVideo extends RealmObject {

    @PrimaryKey
    @Index
    private String videoId;
    private String courseId;
    private String moduleId;
    private String videoName;
    private String videoUrl;
    private String localPath;
    private String videoInfo;
    private boolean isDownloaded;

    public OfflineVideo() {
    }

    public OfflineVideo(String videoId, String courseId, String moduleId, String videoName, String videoUrl, String localPath, String videoInfo, boolean isDownloaded) {
        this.videoId = videoId;
        this.courseId = courseId;
        this.moduleId = moduleId;
        this.videoName = videoName;
        this.videoUrl = videoUrl;
        this.localPath = localPath;
        this.videoInfo = videoInfo;
        this.isDownloaded = isDownloaded;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
