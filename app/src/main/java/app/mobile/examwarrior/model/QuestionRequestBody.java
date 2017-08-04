package app.mobile.examwarrior.model;

/**
 * Created by iaman on 22-07-2017.
 */

public class QuestionRequestBody {

    private boolean shouldFetchAll;
    private String topic_id;

    public boolean isShouldFetchAll() {
        return shouldFetchAll;
    }

    public void setShouldFetchAll(boolean shouldFetchAll) {
        this.shouldFetchAll = shouldFetchAll;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
