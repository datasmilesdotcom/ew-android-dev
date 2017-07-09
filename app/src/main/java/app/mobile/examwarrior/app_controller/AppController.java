package app.mobile.examwarrior.app_controller;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Util;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sandesh on 1/5/17, 5:07 PM.
 */

public class AppController extends Application {

    private static AppController sInstance;
    private static RealmConfiguration realmConfiguration;
    protected String userAgent;
    private Cache cache;

    /**
     * Gets the instance of AppController throughout the App
     *
     * @return AppController
     */
    public static AppController getInstance() {
        return sInstance;
    }

    /**
     * Get the context from AppController   throughout the App
     *
     * @return Context
     */
    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    /**
     * Gets the instance of AppController throughout the App(Sync)
     *
     * @return Application
     */
    public static synchronized AppController getInstanceSyn() {
        return sInstance;
    }

    public static RealmConfiguration realmConfiguration() {
        if (realmConfiguration != null) return realmConfiguration;
        realmConfiguration = new RealmConfiguration.Builder()
                .name(BuildConfig.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(BuildConfig.SCHEMA_VERSION)
                .build();
        return realmConfiguration;
    }

    @Override
    public void onCreate() {
        sInstance = this;
        userAgent = Util.getUserAgent(this, getString(R.string.app_name));

        Realm.init(this);
        realmConfiguration = realmConfiguration();
        Realm.setDefaultConfiguration(realmConfiguration);

        super.onCreate();
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        /*File file = new File("sample.mp4");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cache = new SimpleCache(file, new LeastRecentlyUsedCacheEvictor(1024 * 10));*/
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
       /* if (bandwidthMeter == null) {
            bandwidthMeter = new DefaultBandwidthMeter();
        }
        return new CacheDataSourceFactory(cache, buildHttpDataSourceFactory(bandwidthMeter));*/
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }

    public boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
    }

    @Override
    public void onLowMemory() {
        Glide.get(this).clearMemory();
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Glide.get(this).trimMemory(level);
        super.onTrimMemory(level);
    }
}
