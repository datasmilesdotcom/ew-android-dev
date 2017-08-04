package app.mobile.examwarrior.player;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
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
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.app_controller.AppController;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.model.VideoEntity;
import app.mobile.examwarrior.model.VideoEntityBody;
import app.mobile.examwarrior.prefs.AppPref;
import app.mobile.examwarrior.ui.fragments.SuggestionFragment;
import app.mobile.examwarrior.util.Utility;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPlayerFragment extends Fragment implements View.OnClickListener, ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener, AudioManager.OnAudioFocusChangeListener, MediaSource.Listener {

    public static final String TAG = PlayerActivity.class.getSimpleName();
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";
    public static final String ACTION_VIEW = "app.mobile.examwarrior.player.action.VIEW";
    public static final String KEY_MODULE_ITEM_ID = "app.mobile.examwarrior.player.action.moduleItem_id";
    public static final String EXTENSION_EXTRA = "extension";
    public static final String ACTION_VIEW_LIST =
            "app.mobile.examwarrior.player.action.VIEW_LIST";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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

    private VideoEntity videoInfo;
    private Call<VideoEntity> videoDetails;
    private String mParam1;
    private String mParam2;
    private Handler mainHandler;
    private Handler exceptionHandler;
    private EventLogger eventLogger;
    private SimpleExoPlayerView simpleExoPlayerView;
    private LinearLayout debugRootView, player_control_view;
    private Button retryButton;
    private TextView debugTextView;
    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    private VideoTrackSelectionHelper videoTrackSelectionHelper;
    private DebugTextViewHelper debugViewHelper;
    private boolean needRetrySource;
    private TrackGroupArray lastSeenTrackGroupArray;
    private boolean shouldAutoPlay;
    private AppCompatImageView btn_full_screen;
    private int resumeWindow;
    // Activity lifecycle
    private long resumePosition;
    private TelephonyManager mgr;
    private int CURRENT_RESIZE_MODE = 0;
    private ProgressBar progress;
    private AppCompatTextView video_title;
    private AudioManager mAudioManager;
    private ModuleItem item = null;
    private Spinner spinnerSpeeds;
    private AppCompatImageView btn_settings, btn_sub, btn_screen;
    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music
                releasePlayer();
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
                initializePlayer(videoInfo);
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    public VideoPlayerFragment() {
        // Required empty public constructor
    }

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoPlayerFragment.
     */
    public static VideoPlayerFragment newInstance(String param1, String param2) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.player_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        //Utility.showMessage(getResources().getBoolean(R.bool.is_landscape) ? "Land" : "Port");

        View rootView = view.findViewById(R.id.root);
        rootView.setOnClickListener(this);
        debugRootView = (LinearLayout) view.findViewById(R.id.controls_root);
        player_control_view = view.findViewById(R.id.player_control_view);
        retryButton = (Button) view.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
        debugTextView = (TextView) view.findViewById(R.id.debug_text_view);
        simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.player_view);
        simpleExoPlayerView.setControllerVisibilityListener(this);
        simpleExoPlayerView.requestFocus();
        initViews(view);
    }

    private void
    initViews(View view) {
        mgr = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        progress = (ProgressBar) view.findViewById(R.id.progress);
        if (progress != null) progress.setVisibility(View.VISIBLE);
        video_title = (AppCompatTextView) view.findViewById(R.id.title);
        btn_settings = (AppCompatImageView) view.findViewById(R.id.btn_settings);
        btn_full_screen = (AppCompatImageView) view.findViewById(R.id.btn_full_screen);
        btn_full_screen.setOnClickListener(this);
        AppCompatImageView btn_back = (AppCompatImageView) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        btn_sub = (AppCompatImageView) view.findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
        view.findViewById(R.id.exo_next).setOnClickListener(this);
        view.findViewById(R.id.exo_prev).setOnClickListener(this);
        view.findViewById(R.id.exo_prev).setOnClickListener(this);
        btn_screen = (AppCompatImageView) view.findViewById(R.id.btn_screen);
        btn_screen.setImageResource(RESIZE_MODE.get(CURRENT_RESIZE_MODE));
        btn_screen.setOnClickListener(this);
        final String[] speeds = getResources().getStringArray(R.array.speed_values);
        spinnerSpeeds = view.findViewById(R.id.player_speed);
        spinnerSpeeds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PlaybackParameters playbackParameters = new PlaybackParameters(Float.valueOf(speeds[position]), 1f);
                //player.setPlaybackParameters(Float.valueOf(speeds[position]));
                player.setPlaybackParameters(playbackParameters);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(videoInfo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer(videoInfo);
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
            initializePlayer(videoInfo);
        } else {
            showToast(R.string.storage_permission_denied);
            getActivity().finish();
        }
    }


    // OnClickListener methods

    /*@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Show the controls on any key event.
        simpleExoPlayerView.showController();
        // If the event was not handled then see if the player view can handle it as a media key event.
        return super.dispatchKeyEvent(event) || simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }*/

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings:
                try {
                    if (videoInfo == null) return;
                    MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        videoTrackSelectionHelper.showSelectionDialog(getActivity(), "Select",
                                trackSelector.getCurrentMappedTrackInfo(), C.TRACK_TYPE_DEFAULT, videoInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: " + e.getMessage());
                }

                break;
            case R.id.btn_full_screen:
                getActivity().setRequestedOrientation(getResources().getBoolean(R.bool.is_landscape) ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
            case R.id.retry_button:
                initializePlayer(videoInfo);
                break;
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;
            case R.id.btn_sub:
                try {
                    MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        trackSelectionHelper.showSelectionDialog(getActivity(), "Audio",
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
                toggleResizeMode();
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
    private void initializePlayer(VideoEntity videoInfo) {


        // edge case
        if (getActivity() == null) return;
        Intent intent = getActivity().getIntent();
        if (intent == null) return;
        boolean needNewPlayer = player == null;
        if (video_title != null && intent.getStringExtra(KEY_MODULE_ITEM_ID) != null)
            setVideoTitle(intent.getStringExtra(KEY_MODULE_ITEM_ID));
        if (videoInfo == null) {
            initVideoDetailsAPI("");
            return;
        }

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
                    ((AppController) getActivity().getApplication()).useExtensionRenderers()
                            ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getContext(),
                    drmSessionManager, extensionRendererMode);

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            /*TrackSelection.Factory factory = new FixedTrackSelection.Factory();*/

            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            trackSelectionHelper = new TrackSelectionHelper(trackSelector, videoTrackSelectionFactory);
            videoTrackSelectionHelper = new VideoTrackSelectionHelper(trackSelector, videoTrackSelectionFactory);
            lastSeenTrackGroupArray = null;

            DefaultAllocator defaultAllocator = new DefaultAllocator(true, 1024, 200);
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
            player.setPlayWhenReady(true);
            simpleExoPlayerView.getSubtitleView().setPadding(10, 10, 10, 2);
            CaptionStyleCompat captionStyleCompat = new CaptionStyleCompat(Color.WHITE, ContextCompat.getColor(getActivity(), R.color.subtitle_bkg), Color.TRANSPARENT, CaptionStyleCompat.EDGE_TYPE_NONE, Color.TRANSPARENT, null);
            simpleExoPlayerView.getSubtitleView().setApplyEmbeddedStyles(false);
            simpleExoPlayerView.getSubtitleView().setStyle(captionStyleCompat);

            //simpleExoPlayerView.getSubtitleView().setFractionalTextSize(0.09f, true);
            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
            debugViewHelper.start();
        }
        if (needNewPlayer || needRetrySource) {
            String action = intent.getAction();
            final List<MediaSource> allMediaSources = new ArrayList<>();
            Uri[] uris;
            String[] extensions;
            if (ACTION_VIEW.equals(action)) {

                uris = new Uri[]{Uri.parse("")};
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
            videoInfo.setVideo_urls(Collections.unmodifiableList(videoInfo.getVideo_urls()));
            uris = new Uri[videoInfo.getVideo_urls().size()];
            extensions = new String[videoInfo.getVideo_urls().size()];

            for (int i = 0; i < videoInfo.getVideo_urls().size(); i++) {
                uris[i] = Uri.parse(videoInfo.getVideo_urls().get(i).getVideo_url());
                extensions[i] = "";
            }


            if (Util.maybeRequestReadExternalStoragePermission(getActivity(), uris)) {
                // The player will be reinitialized if the permission is granted.
                return;
            }

            for (int i = 0; i < uris.length; i++) {
                // Build the video MediaSource.
                //MediaSource videoSource = new ExtractorMediaSource(videoUri, ...);
                allMediaSources.add(buildMediaSource(uris[i], extensions[i]));
            }

            MediaSource subtitleSource;
            //FixedTrackSelection fixedTrackSelection = new FixedTrackSelection();
            if (!Utility.isEmpty(videoInfo.getSubtitleUrl())) {
                // Build the subtitle MediaSource.
                subtitleSource = new SingleSampleMediaSource(Uri.parse(videoInfo.getSubtitleUrl()), mediaDataSourceFactory,
                        Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP, null, Format.NO_VALUE, Format.NO_VALUE, "se", null), 0);
                allMediaSources.add(subtitleSource);
            }

            //https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8
            MergingMediaSource mergedSource =
                    new MergingMediaSource(allMediaSources.toArray(new MediaSource[allMediaSources.size()]));

            /*MediaSource mediaSource = mediaSources.length == 1 ? mergedSource
                    : new ConcatenatingMediaSource(mergedSource);*/
            MediaSource playlistSource = new ConcatenatingMediaSource(mergedSource);


            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(playlistSource, !haveResumePosition, false);
            needRetrySource = false;
            updateButtonVisibilities();
        }
    }

    /**
     * Build Media Source
     *
     * @param uri
     * @param overrideExtension
     * @return
     */
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
            videoTrackSelectionHelper = null;
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
        return ((AppController) getActivity().getApplication())
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
        return ((AppController) getActivity().getApplication())
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
                if (progress != null) progress.setVisibility(View.INVISIBLE);
                player_control_view.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_BUFFERING:
                if (progress != null) progress.setVisibility(View.VISIBLE);
                player_control_view.setVisibility(View.INVISIBLE);
                break;
            case ExoPlayer.STATE_READY:
                if (progress != null) progress.setVisibility(View.INVISIBLE);
                player_control_view.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_ENDED:
                if (progress != null) progress.setVisibility(View.INVISIBLE);
                player_control_view.setVisibility(View.VISIBLE);
                //initializePlayer();
                showControls();
                break;
            default:
                if (progress != null) progress.setVisibility(View.INVISIBLE);
                player_control_view.setVisibility(View.VISIBLE);
                break;
        }
        updateButtonVisibilities();
    }

    @Override
    public void onPositionDiscontinuity() {
        if (needRetrySource) {
            // This will only occur if the user has performed a seek whilst in the error state. Update the
            // resume position so that if the user then retries, playback will resume from the position to
            // which they seeked.
            updateResumePosition();
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        // Do nothing.
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        switch (e.type) {
            case ExoPlaybackException.TYPE_SOURCE:
                try {
                    String errorUri = ((HttpDataSource.InvalidResponseCodeException) e.getCause()).dataSpec.uri.toString();
                    Log.e(TAG, "TYPE_SOURCE: " + errorUri);
                    if (videoInfo != null) {
                        if (!Utility.isEmpty(videoInfo.getSubtitleUrl()) && videoInfo.getSubtitleUrl().equals(errorUri)) {
                            videoInfo.setSubtitleUrl(null);
                            exceptionHandler = new Handler();
                            exceptionHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    needRetrySource = true;
                                    initializePlayer(videoInfo);
                                }
                            }, 500);

                            errorString = "Invalid subtitle format";
                        } else {
                            errorString = "Can not play this video";
                            //handleBehindLiveWindow(e);
                        }
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "onPlayerError: " + ex.getMessage());
                }
                break;

            case ExoPlaybackException.TYPE_RENDERER:
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
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
                handleBehindLiveWindow(e);
                break;

            case ExoPlaybackException.TYPE_UNEXPECTED:
                errorString = "Error while playing video";
                break;
        }
        /*if (e.type == ExoPlaybackException.TYPE_RENDERER) {

        }*/
        if (errorString != null) {
            showToast(errorString);
        }
        handleBehindLiveWindow(e);

    }

    /**
     * Handle LiveWindow exception but not for Subtitle error
     *
     * @param e
     */
    private void handleBehindLiveWindow(ExoPlaybackException e) {
        needRetrySource = true;
        if (isBehindLiveWindow(e)) {
            clearResumePosition();
            initializePlayer(videoInfo);
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
                    Log.e(TAG, "onTracksChanged: format.height " + format.bitrate + " " + trackGroups.get(i).indexOf(format));
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
        updateButtonVisibilities();
        //if (trackGroups != lastSeenTrackGroupArray) {
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo != null) {
            TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(0);
            Log.e(TAG, "trackGroupArray.indexOf: " + trackGroupArray.indexOf(trackGroup));
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO)
                    == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                showToast(R.string.error_unsupported_video);
            }
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO)
                    == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                showToast(R.string.error_unsupported_audio);
            }
        }
        lastSeenTrackGroupArray = trackGroups;
        //}
    }

    private void updateButtonVisibilities() {
        debugRootView.removeAllViews();

        retryButton.setVisibility(needRetrySource ? View.VISIBLE : View.GONE);
        debugRootView.addView(retryButton);

        if (player == null) {
            return;
        }

        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }

        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                Button button = new Button(getContext());
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
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (player != null) {
                    player.setPlayWhenReady(true);
                }
                //resumePlayer(); // Resume your media player here
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (player != null) {
                    player.setPlayWhenReady(false);
                }
                //pausePlayer();// Pause your media player here
                break;
        }
    }


    private void initVideoDetailsAPI(String videoID) {
        if (videoDetails != null) {
            return;
        }
        ApiInterface apiInterface = ServiceGenerator.createServiceWithCache(ApiInterface.class);
        String token = null;
        Realm realm = Realm.getDefaultInstance();
        try {
            User user = realm.where(User.class).findFirst();
            if (user != null) {
                token = user.getToken();
            }
        } catch (Exception e) {

        } finally {
            realm.close();
        }
        token = "JWT " + token;
        videoDetails = apiInterface.getVideoEntity(token, new VideoEntityBody(item.getItemTypeId()));
        //videoDetails = apiInterface.getVideoEntity(token, new VideoEntityBody("introduction-powercenter-1"));

        videoDetails.enqueue(new Callback<VideoEntity>() {
            @Override
            public void onResponse(Call<VideoEntity> call, Response<VideoEntity> response) {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        videoInfo = response.body();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        SuggestionFragment suggestionFragment = (SuggestionFragment) fragmentManager.findFragmentByTag(SuggestionFragment.TAG);
                        if (suggestionFragment != null) {
                            suggestionFragment.updateVideoContent(videoInfo);
                            initializePlayer(videoInfo);
                        }
                        break;
                    default:
                        Utility.showMessage("Failed in response");
                        break;
                }
            }

            @Override
            public void onFailure(Call<VideoEntity> call, Throwable t) {
                Utility.showMessage("Failed");
            }
        });


    }

    @Override
    public void onSourceInfoRefreshed(Timeline timeline, Object manifest) {
        Log.e(TAG, "onSourceInfoRefreshed: " + timeline.getWindowCount());
    }
}
