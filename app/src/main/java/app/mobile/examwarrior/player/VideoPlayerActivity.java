package app.mobile.examwarrior.player;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.ui.fragments.SuggestionFragment;

public class VideoPlayerActivity extends AppCompatActivity {

    public static final String TAG = VideoPlayerActivity.class.getSimpleName();
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        if (savedInstanceState == null) {
            addFragment(R.id.course_player_view, VideoPlayerFragment.newInstance("", ""), VideoPlayerFragment.TAG);
            addFragment(R.id.related_videos, SuggestionFragment.newInstance("", ""), SuggestionFragment.TAG);

           /* String json = null;
            try {
                InputStream inputStream = getAssets().open("questions/Questions.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

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
                    .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
                    }.getType(), new RealmStringDeserializer())
                    .create();
            Realm realm = Realm.getDefaultInstance();

            try {
                final Questions[] main = gson.fromJson(json, Questions[].class);
                Log.e(TAG, "onCreate: " + main[0].getList().get(0));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Questions> data = realm.where(Questions.class).findAll();
                        if (data != null && !data.isEmpty()) {
                            for (RealmString questions : data.get(0).getList()) {
                                Log.e(TAG, "execute: " + questions);
                            }
                        } else {
                            for (Questions questions : main) {
                                realm.insertOrUpdate(questions);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            } finally {
                realm.close();
            }*/
        }
    }

    /**
     * Add fragment to the container
     *
     * @param containerViewId
     * @param fragment
     * @param fragmentTag
     */
    protected void addFragment(int containerViewId,
                               Fragment fragment,
                               String fragmentTag) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fragment_anim_enter_from_left,
        // R.anim.fragment_anim_exit_to_right,R.anim.fragment_anim_enter_from_right,
        // R.anim.fragment_anim_exit_to_left);
        fragmentTransaction.add(containerViewId, fragment, fragmentTag);
        //fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            findViewById(R.id.related_videos).setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            findViewById(R.id.related_videos).setVisibility(View.VISIBLE);
        }

        super.onConfigurationChanged(newConfig);
    }
}
