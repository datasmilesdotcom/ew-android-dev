package app.mobile.examwarrior.player;

/**
 * Created by sandesh on 3/5/17, 11:53 PM.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.R;
import app.mobile.examwarrior.app_controller.AppController;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.prefs.AppPref;
import app.mobile.examwarrior.util.Utility;
import io.realm.Realm;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class PlayerActivity extends Activity implements OnClickListener, ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener, AudioManager.OnAudioFocusChangeListener, MediaSource.Listener {

    public static final String TAG = PlayerActivity.class.getSimpleName();
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";

    public static final String ACTION_VIEW = "app.mobile.examwarrior.player.action.VIEW";
    public static final String KEY_MODULE_ITEM_ID = "app.mobile.examwarrior.player.action.moduleItem_id";
    public static final String KEY_MODULE_ID = "app.mobile.examwarrior.player.action.module_id";

    public static final String EXTENSION_EXTRA = "extension";

    public static final String ACTION_VIEW_LIST =
            "app.mobile.examwarrior.player.action.VIEW_LIST";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    private static final Map<Integer, Integer> RESIZE_MODE =
            Collections.unmodifiableMap(new HashMap<Integer, Integer>() {{
                put(AspectRatioFrameLayout.RESIZE_MODE_FILL, R.drawable.ic_center_focus_strong_black_24dp);
                put(AspectRatioFrameLayout.RESIZE_MODE_FIT, R.drawable.ic_fullscreen_black_24dp);
                put(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH, R.drawable.ic_center_focus_strong_black_24dp);
                put(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT, R.drawable.ic_zoom_out_map_black_24dp);
            }});
    /***********************************************************************************************
     * Local Members
     *//////////////////////////////////////////////////////////////////////////////////////////////
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private List<Uri> tracks = new ArrayList<>();
    private Handler mainHandler;
    private EventLogger eventLogger;
    private SimpleExoPlayerView simpleExoPlayerView;
    private LinearLayout debugRootView;
    private Button retryButton;
    private TextView debugTextView;
    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private SimpleExoPlayer player_preview;
    private DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    private DebugTextViewHelper debugViewHelper;
    private boolean needRetrySource;
    private TrackGroupArray lastSeenTrackGroupArray;
    private boolean shouldAutoPlay;
    private int resumeWindow;
    // Activity lifecycle
    private long resumePosition;
    private TelephonyManager mgr;
    private int CURRENT_RESIZE_MODE = 0;
    private ProgressBar progress;
    private AppCompatTextView video_title;
    private AudioManager mAudioManager;
    private ModuleItem item = null;
    private AppCompatImageView btn_settings, btn_sub, btn_screen;
    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music
                releasePlayer();
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
                initializePlayer();
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        shouldAutoPlay = true;
        if (savedInstanceState == null) {
            clearResumePosition();
        } else {
            resumeWindow = savedInstanceState.getInt("res", 0);
            String pos = savedInstanceState.getString("pos", "0");
            resumePosition = Long.parseLong(pos);
        }

        mediaDataSourceFactory = buildDataSourceFactory(true);
        mainHandler = new Handler();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        Utility.showMessage(getResources().getBoolean(R.bool.is_landscape) ? "Land" : "Port");

        setContentView(R.layout.player_activity);
        View rootView = findViewById(R.id.root);
        rootView.setOnClickListener(this);
        debugRootView = (LinearLayout) findViewById(R.id.controls_root);
        retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
        debugTextView = (TextView) findViewById(R.id.debug_text_view);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.setControllerVisibilityListener(this);
        simpleExoPlayerView.requestFocus();
        initViews();
    }

    private void initViews() {
        mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        progress = (ProgressBar) findViewById(R.id.progress);
        if (progress != null) progress.setVisibility(View.VISIBLE);
        video_title = (AppCompatTextView) findViewById(R.id.title);
        btn_settings = (AppCompatImageView) findViewById(R.id.btn_settings);
        AppCompatImageView btn_back = (AppCompatImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        btn_sub = (AppCompatImageView) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
        findViewById(R.id.exo_next).setOnClickListener(this);
        findViewById(R.id.exo_prev).setOnClickListener(this);
        findViewById(R.id.exo_prev).setOnClickListener(this);
        btn_screen = (AppCompatImageView) findViewById(R.id.btn_screen);
        btn_screen.setImageResource(RESIZE_MODE.get(CURRENT_RESIZE_MODE));
        btn_screen.setOnClickListener(this);
        AppCompatImageView btn_full_screen = (AppCompatImageView) findViewById(R.id.btn_full_screen);
        btn_full_screen.setOnClickListener(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean isChangingConfigurations() {
        return super.isChangingConfigurations();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e(TAG, "onConfigurationChanged: LAND");

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            Log.e(TAG, "onConfigurationChanged: Port");
        }

        super.onConfigurationChanged(newConfig);
    }

    // Activity input

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("res", resumeWindow);
        outState.putString("pos", Long.toString(resumePosition));
        super.onSaveInstanceState(outState);
    }

    // OnClickListener methods

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Show the controls on any key event.
        simpleExoPlayerView.showController();
        // If the event was not handled then see if the player view can handle it as a media key event.
        return super.dispatchKeyEvent(event) || simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings:

                //setRequestedOrientation(getResources().getBoolean(R.bool.is_landscape) ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                try {
                    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        trackSelectionHelper.showSelectionDialog(this, "Select",
                                trackSelector.getCurrentMappedTrackInfo(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: " + e.getMessage());
                }

                break;
            case R.id.retry_button:
                initializePlayer();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_sub:
                try {
                    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        trackSelectionHelper.showSelectionDialog(this, "Audio",
                                trackSelector.getCurrentMappedTrackInfo(), C.TRACK_TYPE_AUDIO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: " + e.getMessage());
                }
                break;
            case R.id.exo_prev:
                previous();
                break;
            case R.id.exo_next:
                next();
                break;

            case R.id.btn_screen:
                //toggleResizeMode();
                showAvailableOptions();
                break;
            case R.id.btn_full_screen:
                setRequestedOrientation(getResources().getBoolean(R.bool.is_landscape) ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
        }
    }

    /**
     * Toggle screen ratio mode
     */
    private void toggleResizeMode() {

        CURRENT_RESIZE_MODE = CURRENT_RESIZE_MODE + 1;
        if (CURRENT_RESIZE_MODE >= RESIZE_MODE.size()) {
            CURRENT_RESIZE_MODE = 0;
        }
        AppPref.getInstance().setResizeMode(CURRENT_RESIZE_MODE);
        simpleExoPlayerView.setResizeMode((Integer) RESIZE_MODE.keySet().toArray()[CURRENT_RESIZE_MODE]);
        btn_screen.setImageResource(RESIZE_MODE.get(CURRENT_RESIZE_MODE));
    }

    /***
     * Play previous track if any
     */
    private void previous() {
        Timeline currentTimeline = simpleExoPlayerView.getPlayer().getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = simpleExoPlayerView.getPlayer().getCurrentWindowIndex();

        Timeline.Window currentWindow = currentTimeline.getWindow(currentWindowIndex, new Timeline.Window());
        if (currentWindowIndex > 0 && (player.getCurrentPosition() <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS
                || (currentWindow.isDynamic && !currentWindow.isSeekable))) {
            player.seekTo(currentWindowIndex - 1, C.TIME_UNSET);
        } else {
            player.seekTo(0);
        }
    }

    /***
     * Play next track if any
     */
    private void next() {
        Timeline currentTimeline = simpleExoPlayerView.getPlayer().getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = simpleExoPlayerView.getPlayer().getCurrentWindowIndex();
        if (currentWindowIndex < currentTimeline.getWindowCount() - 1) {
            player.seekTo(currentWindowIndex + 1, C.TIME_UNSET);
        } else if (currentTimeline.getWindow(currentWindowIndex, new Timeline.Window(), false).isDynamic) {
            player.seekTo(currentWindowIndex, C.TIME_UNSET);
        }
    }

    /***
     * Set Current Video track title
     * @param moduleId
     */
    private void setVideoTitle(final String moduleId) {
        Realm realm = Realm.getDefaultInstance();

        try {
            item = realm.where(ModuleItem.class).equalTo("itemId", moduleId).findFirst();
            if (item != null && item.isValid()) {
                video_title.setText(item.getItemName());
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, "setVideoTitle: " + e.getMessage());
        } finally {
            realm.close();
        }
    }

    // Internal methods

    @Override
    public void onVisibilityChange(int visibility) {
        debugRootView.setVisibility(visibility);
    }

    /***
     * Initialize Player
     */
    private void initializePlayer() {
        Intent intent = getIntent();
        DefaultAllocator defaultAllocator = null;
        boolean needNewPlayer = player == null;
        if (video_title != null && intent.getStringExtra(KEY_MODULE_ITEM_ID) != null)
            setVideoTitle(intent.getStringExtra(KEY_MODULE_ITEM_ID));
        if (needNewPlayer) {
            boolean preferExtensionDecoders = intent.getBooleanExtra(PREFER_EXTENSION_DECODERS, false);
            UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA)
                    ? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;
            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
            if (drmSchemeUuid != null) {
                String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
                String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
                try {
                    drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl,
                            keyRequestPropertiesArray);
                } catch (UnsupportedDrmException e) {
                    int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported
                            : (e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
                            ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
                    showToast(errorStringId);
                    return;
                }
            }

            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                    ((AppController) getApplication()).useExtensionRenderers()
                            ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this,
                    drmSessionManager, extensionRendererMode);

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            TrackSelection.Factory factory = new FixedTrackSelection.Factory();

            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            trackSelectionHelper = new TrackSelectionHelper(trackSelector, videoTrackSelectionFactory);
            lastSeenTrackGroupArray = null;
            defaultAllocator = new DefaultAllocator(true, 1024, 200);
            LoadControl loadControl = new DefaultLoadControl(defaultAllocator // allocator
                    , 15000 // Min buffer duration
                    , 30000 // Max buffer duration
                    , 2500 // min buffer to start playback
                    , 5000 // min buffer to resume playback

            );


            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            player.addListener(this);
            eventLogger = new EventLogger(trackSelector);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
            player.setMetadataOutput(eventLogger);

            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
            debugViewHelper.start();
        }
        if (needNewPlayer || needRetrySource) {
            String action = intent.getAction();
            Uri[] uris;
            String[] extensions;
            if (ACTION_VIEW.equals(action)) {
                uris = new Uri[]{intent.getData()};
                extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA)};
            } else if (ACTION_VIEW_LIST.equals(action)) {
                String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
                uris = new Uri[uriStrings.length];
                for (int i = 0; i < uriStrings.length; i++) {
                    uris[i] = Uri.parse(uriStrings[i]);
                }
                extensions = intent.getStringArrayExtra(EXTENSION_LIST_EXTRA);
                if (extensions == null) {
                    extensions = new String[uriStrings.length];
                }
            } else {
                /*showToast(getString(R.string.unexpected_intent_action, action));
                return;*/
            }
            uris = new Uri[]{Uri.parse("http://139.59.68.158:1935/vod/smil:sample1.smil/playlist.m3u8")};
            extensions = new String[]{"", "", ""};
            if (Util.maybeRequestReadExternalStoragePermission(this, uris)) {
                // The player will be reinitialized if the permission is granted.
                return;
            }

            MediaSource[] mediaSources = new MediaSource[uris.length];
            for (int i = 0; i < uris.length; i++) {
                tracks.add(uris[i]);
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }

            MergingMediaSource mergedSource =
                    new MergingMediaSource(mediaSources);


            MediaSource playlistSource = mediaSources.length == 1 ? mediaSources[0]
                    : new ConcatenatingMediaSource(mergedSource);

            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(playlistSource, !haveResumePosition, false);
            needRetrySource = false;
            Log.e(TAG, "initializePlayer: " + player.getCurrentTrackGroups().length);
            updateButtonVisibilities();
        }
    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                        mainHandler, eventLogger);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(UUID uuid,

                                                                           String licenseUrl, String[] keyRequestPropertiesArray) throws UnsupportedDrmException {
        if (Util.SDK_INT < 18) {
            return null;
        }
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl,
                buildHttpDataSourceFactory(false));
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
                        keyRequestPropertiesArray[i + 1]);
            }
        }
        return new DefaultDrmSessionManager<>(uuid,
                FrameworkMediaDrm.newInstance(uuid), drmCallback, null, mainHandler, eventLogger);
    }

    private void releasePlayer() {
        if (player != null) {
            debugViewHelper.stop();
            debugViewHelper = null;
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            trackSelectionHelper = null;
            eventLogger = null;
        }
    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
                : C.TIME_UNSET;
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return ((AppController) getApplication())
                .buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    // ExoPlayer.EventListener implementation

    /**
     * Returns a new HttpDataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new HttpDataSource factory.
     */
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return ((AppController) getApplication())
                .buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            // ideal
            case ExoPlayer.STATE_IDLE:
                if (progress != null) progress.setVisibility(View.GONE);
                break;
            case ExoPlayer.STATE_BUFFERING:
                if (progress != null) progress.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_READY:
                if (progress != null) progress.setVisibility(View.GONE);
                break;
            case ExoPlayer.STATE_ENDED:
                if (progress != null) progress.setVisibility(View.GONE);
                initializePlayer();
                showControls();
                break;
            default:
                if (progress != null) progress.setVisibility(View.GONE);

                break;
        }
        updateButtonVisibilities();
    }

    @Override
    public void onPositionDiscontinuity() {
        Log.e(TAG, "onPositionDiscontinuity: " + player.getCurrentWindowIndex() + " *** " + player.getCurrentPeriodIndex());
        if (needRetrySource) {
            // This will only occur if the user has performed a seek whilst in the error state. Update the
            // resume position so that if the user then retries, playback will resume from the position to
            // which they seeked.
            updateResumePosition();
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.e(TAG, "onPlaybackParametersChanged: ");
        // Do nothing.
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
        Log.e(TAG, "onTimelineChanged: ");
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException =
                        (DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = getString(R.string.error_no_secure_decoder,
                                decoderInitializationException.mimeType);
                    } else {
                        errorString = getString(R.string.error_no_decoder,
                                decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = getString(R.string.error_instantiating_decoder,
                            decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            showToast(errorString);
        }
        needRetrySource = true;
        if (isBehindLiveWindow(e)) {
            clearResumePosition();
            initializePlayer();
        } else {
            updateResumePosition();
            updateButtonVisibilities();
            showControls();
        }
    }

    // User controls

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Log.e(TAG, "onTracksChanged: ");
        Format format = null;
        TrackGroup trackGroup = null;
        try {

            for (int i = 0; i < trackSelections.getAll().length; i++) {

                if (trackSelections.get(i).getSelectedFormat() != null && MimeTypes.isVideo(trackSelections.get(i).getSelectedFormat().sampleMimeType)) {
                    format = trackSelections.get(i).getSelectedFormat();
                    Log.e(TAG, "onTracksChanged: format.height " + format.height + " " + trackGroups.get(i).indexOf(format));
                    trackGroup = trackSelections.get(i).getTrackGroup();
                    //AdaptiveTrackSelection adaptiveTrackSelection = new AdaptiveTrackSelection(trackGroup, new int[]{Track.TYPE_vide}, BANDWIDTH_METER);
                    //Utility.showMessage("AdaptiveTrackSelection " + adaptiveTrackSelection.getSelectedIndex());
                    Log.e(TAG, "onTracksChanged: " + (trackGroup.getFormat(0).hashCode() == format.hashCode() ? "match " : "nomatch"));
                    break;
                }
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "onTracksChanged: " + e.getMessage());
        }

        /*MappingTrackSelector.SelectionOverride override = trackSelector.getSelectionOverride(0, trackGroups);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {
                if (override != null && override.groupIndex == i
                        && override.containsTrack(j)) {
                    Log.e("tracks", "SelectedTrack: " + i);
                }
            }

        }*/

        updateButtonVisibilities();
        //if (trackGroups != lastSeenTrackGroupArray) {
        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo != null && format != null) {
            TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(0);
            Log.e(TAG, "trackGroupArray.indexOf: " + trackGroupArray.indexOf(trackGroup));

            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_DEFAULT)
                    == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                showToast(R.string.error_unsupported_video);
            }
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO)
                    == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                showToast(R.string.error_unsupported_audio);
            }
        }
        lastSeenTrackGroupArray = trackGroups;
        // }
    }

    private void updateButtonVisibilities() {
        debugRootView.removeAllViews();

        retryButton.setVisibility(needRetrySource ? View.VISIBLE : View.GONE);
        debugRootView.addView(retryButton);

        if (player == null) {
            return;
        }

        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }

        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                Button button = new Button(this);
                int label;
                switch (player.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.audio;
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.text;
                        break;
                    default:
                        continue;
                }
                button.setText(label);
                button.setTag(i);
                button.setOnClickListener(this);
//                debugRootView.addView(button, debugRootView.getChildCount() - 1);
            }
        }
    }

    private void showControls() {
        debugRootView.setVisibility(View.VISIBLE);
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //resumePlayer(); // Resume your media player here
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                //pausePlayer();// Pause your media player here
                break;
        }
    }

    @Override
    public void onSourceInfoRefreshed(Timeline timeline, Object manifest) {

    }

    private void showAvailableOptions() {
        final HashMap<String, String> res = new HashMap<>();

        for (Uri urls :
                tracks) {
            res.put(urls.toString(), urls.toString());
        }

        if (res.size() < 0) {
            Utility.showMessage("This video is not available to download");
            return;
        }
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this);
        builder.setTitle("Save video offline");

        // add a list
        String[] animals = res.keySet().toArray(new String[res.size()]);
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String keyRes = (res.keySet().toArray())[which].toString();
                String videoUrl = res.get(keyRes);
                trackSelector.clearSelectionOverrides(0);
                trackSelector.setSelectionOverride(0, lastSeenTrackGroupArray
                        , new MappingTrackSelector.SelectionOverride(new FixedTrackSelection.Factory(), 0, which));

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
