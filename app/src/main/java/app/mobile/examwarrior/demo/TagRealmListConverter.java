package app.mobile.examwarrior.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import app.mobile.examwarrior.model.RealmString;
import io.realm.RealmList;

/**
 * Created by sandesh on 27/8/17, 12:49 PM.
 */

public class TagRealmListConverter implements JsonSerializer<RealmList<RealmString>>,
        JsonDeserializer<RealmList<RealmString>> {

    @Override
    public RealmList<RealmString> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RealmList<RealmString> tags = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja) {
            tags.add((RealmString) context.deserialize(je, RealmString.class));
        }
        return tags;
    }

    @Override
    public JsonElement serialize(RealmList<RealmString> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        for (RealmString tag : src) {
            ja.add(context.serialize(tag));
        }
        return ja;
    }
}
