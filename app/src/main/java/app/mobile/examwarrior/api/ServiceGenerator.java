package app.mobile.examwarrior.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.RealmPrimitiveModel.RealmStringAns;
import app.mobile.examwarrior.RealmPrimitiveModel.TagRealmListConverter;
import app.mobile.examwarrior.app_controller.AppController;
import app.mobile.examwarrior.util.Utility;
import io.realm.RealmList;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandesh on 13/1/17.
 */

public class ServiceGenerator {

    private static final String API_BASE_URL = BuildConfig.BASE_URL;
   // public static GsonBuilder gsonBuilder = new GsonBuilder();

    public static GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<RealmList<RealmStringAns>>() {}.getType(),
                    new TagRealmListConverter());
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // log rest calls
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            httpClient.addNetworkInterceptor(logging);
        }
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            httpClient.addNetworkInterceptor(logging);
        }
        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                return null;
            }

        });
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        // register the deserializer
       /* gsonBuilder.registerTypeAdapter(new TypeToken<RealmList<ChannelList>>() {
        }.getType(), new RealmStringDeserializer());*/

        Gson gson = gsonBuilder.create();
        retrofit = builder.client(httpClient.build()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithCache(Class<S> serviceClass) {
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClient.addNetworkInterceptor(logging);
        }
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (Utility.isNetworkAvailable()) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60 * 20).build();
                } else {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                }
                return chain.proceed(request);
            }
        });
        httpClient.cache(new okhttp3.Cache(new File(AppController.getAppContext().getCacheDir(), "http"), 10 * 1024 * 1024));
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        Gson gson = gsonBuilder.create();
        retrofit = builder.client(httpClient.build()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(serviceClass);
    }
}
