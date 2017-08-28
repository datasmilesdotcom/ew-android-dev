package app.mobile.examwarrior.demo;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

import io.realm.RealmObject;

/**
 * Created by sandesh on 27/8/17, 4:10 PM.
 */

public class Utils {
    public static Gson getGson() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson;
    }


    public static String serialize(Object object) {
        return getGson().toJson(object);
    }

    public static Object deserialize(String objectString, Type type) {
        return getGson().fromJson(objectString, type);
    }


    public static class ArrayToStringTypeAdapter extends TypeAdapter<String> {

        @Override
        public void write(JsonWriter out, String value) throws IOException {
            out.value(String.valueOf(value));
        }

        @Override
        public String read(JsonReader in) throws IOException {
            String data = "";
            boolean isArray = (in.peek() == JsonToken.BEGIN_ARRAY) ? true : false;

            if (isArray) {
                in.beginArray();
                while (in.hasNext()) {
                    data = data + in.nextString() + ",";
                }
                if (data.endsWith(",")) {
                    data = data.substring(0, data.length() - 1);
                }
                in.endArray();
            } else {
                data = in.nextString();
            }

            return data;
        }
    }

    ;

}
