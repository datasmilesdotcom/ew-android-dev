package app.mobile.examwarrior.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import io.realm.RealmList;

/**
 * Created by sandesh on 25/8/17, 11:49 AM.
 */

public class RealmStringDeserializer implements JsonDeserializer<RealmList<RealmString>> {


    @Override
    public RealmList<RealmString> deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RealmList<RealmString> realmStrings = new RealmList<>();
        JsonArray stringList = json.getAsJsonArray();

        for (JsonElement stringElement : stringList) {
            realmStrings.add(new RealmString(stringElement.getAsString()));
        }

        return realmStrings;
    }
}
