package app.mobile.examwarrior.model;

/**
 * Created by sandesh on 29/8/17, 6:42 PM.
 */

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * To use:
 * <pre>
 *     public class DataModel extends RealmObject
 *     {
 *         public static RealmUserModel createData(@NonNull Realm realm) {
 *             boolean wasInTransaction = realm.isInTransaction();
 *             if (!wasInTransaction) {
 *                 realm.beginTransaction();
 *             }
 *
 *             long id = RealmAutoIncrement.getNextIdFromModel(realm, DataModel.class, "id");
 *             RealmUserModel user = realm.createObject(DataModel.class, id);
 *
 *             if (!wasInTransaction) {
 *                 realm.commitTransaction();
 *             }
 *
 *             return user;
 *         }
 *
 *         {@literal @}PrimaryKey
 *         private long id;
 *         ...
 *     }
 *
 *     ...
 *     Realm realm = Realm.getDefaultInstance(); // <-- or however you get your instance
 *     ...
 *     DataModel data = DataModel.createData(realm);
 *     ...
 *     realm.close();
 *
 * </pre>
 */
public class RealmAutoIncrement {
    private static final Map<RealmConfiguration, Map<Class<? extends RealmModel>, AtomicLong>> sModelMap = new HashMap<>();
    public static long INVALID_ID = -1;

    public static long getLastIdFromModel(
            @NonNull
                    Realm realm,
            @NonNull
                    Class<? extends RealmModel> clazz,
            @NonNull
                    String fieldName) {
        Number lastId = realm.where(clazz).max(fieldName);
        return lastId != null ? lastId.intValue() : INVALID_ID + 1;
    }

    public static long getNextIdFromModel(
            @NonNull
                    Realm realm,
            @NonNull
                    Class<? extends RealmModel> clazz,
            @NonNull
                    String fieldName) {
        RealmConfiguration realmConfiguration = realm.getConfiguration();
        Map<Class<? extends RealmModel>, AtomicLong> modelMap = sModelMap.get(realmConfiguration);
        if (modelMap == null) {
            modelMap = new HashMap<>();
            sModelMap.put(realmConfiguration, modelMap);
        }

        AtomicLong modelId = modelMap.get(clazz);
        if (modelId == null) {
            modelId = new AtomicLong(getLastIdFromModel(realm, clazz, fieldName));
            modelMap.put(clazz, modelId);
        }

        return modelId.incrementAndGet();
    }
}