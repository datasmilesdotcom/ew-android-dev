package app.mobile.examwarrior.RealmPrimitiveModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by iaman on 22-07-2017.
 */

public class TagRealmListConverter implements JsonSerializer<RealmList<RealmStringAns>>,
        JsonDeserializer<RealmList<RealmStringAns>> {

    @Override
    public JsonElement serialize(RealmList<RealmStringAns> src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        for (RealmStringAns tag : src) {
            ja.add(context.serialize(tag));
        }
        return ja;
    }

    @Override
    public RealmList<RealmStringAns> deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context)
            throws JsonParseException {
        RealmList<RealmStringAns> tags = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja) {
            tags.add((RealmStringAns) context.deserialize(je, RealmStringAns.class));
        }
        return tags;
    }

}