package app.mobile.examwarrior.demo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import app.mobile.examwarrior.model.RealmString;
import io.realm.RealmList;
import io.realm.internal.IOException;

/**
 * Created by sandesh on 27/8/17, 1:02 PM.
 */

public class RealmStringListTypeAdapter extends TypeAdapter<RealmList<RealmString>> {
    public static final TypeAdapter<RealmList<RealmString>> INSTANCE =
            new RealmStringListTypeAdapter().nullSafe();

    private RealmStringListTypeAdapter() {
    }

    @Override
    public void write(JsonWriter out, RealmList<RealmString> src) throws IOException, java.io.IOException {
        out.beginArray();
        for (RealmString realmString : src) {
            out.value(realmString.getValue());
        }
        out.endArray();
    }

    @Override
    public RealmList<RealmString> read(JsonReader in) throws IOException, java.io.IOException {
        RealmList<RealmString> realmStrings = new RealmList<>();
        in.beginArray();
        while (in.hasNext()) {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
            } else {
                RealmString realmString = new RealmString();
                realmString.val = in.nextString();
                realmStrings.add(realmString);
            }
        }
        in.endArray();
        return realmStrings;
    }
}
