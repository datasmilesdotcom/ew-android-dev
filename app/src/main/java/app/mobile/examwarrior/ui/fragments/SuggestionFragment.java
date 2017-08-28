package app.mobile.examwarrior.ui.fragments;


import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ProgressBar;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.database.OfflineVideo;
import app.mobile.examwarrior.demo.ToggleListener;
import app.mobile.examwarrior.model.RelatedCourse;
import app.mobile.examwarrior.model.RelatedTopicsVideo;
import app.mobile.examwarrior.model.RelatedVideo;
import app.mobile.examwarrior.model.RelatedVideos;
import app.mobile.examwarrior.model.RelatedVideosBody;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.model.VideoEntity;
import app.mobile.examwarrior.model.VoteRequestBody;
import app.mobile.examwarrior.model.VoteVideoResponse;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.util.decoration.HeaderItemDecoration;
import app.mobile.examwarrior.util.decoration.InsetItemDecoration;
import app.mobile.examwarrior.util.decoration.ItemOffsetDecoration;
import app.mobile.view_holder.RelatedCoursesHeader;
import app.mobile.view_holder.RelatedCoursesViewHolder;
import app.mobile.view_holder.RelatedSuggestionHeader;
import app.mobile.view_holder.RelatedSuggestionViewHolder;
import app.mobile.view_holder.RelatedVideosHeader;
import app.mobile.view_holder.RelatedVideosViewHolder;
import app.mobile.view_holder.ShowMoreViewHolder;
import app.mobile.view_holder.VideoContentDetailsAdapter;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.DOWNLOAD_SERVICE;
import static app.mobile.examwarrior.player.VideoPlayerFragment.KEY_MODULE_ITEM_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionFragment extends Fragment implements ToggleListener, ShowMoreViewHolder.ShowMoreClickListener, VideoContentDetailsAdapter.onItemListener {
    public static final String TAG = SuggestionFragment.class.getSimpleName();

    private static final String FOLDER_NAME = "/EW_Video_Storage";
    private static final int INITIAL_GROUP_POSITION = 0;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int STATUS_UPVOTED = 1;
    private static final int STATUS_DOWNVOTED = 1;
    private static final int STATUS_NOT_UPVOTE = 0;
    private static final int STATUS_NOT_DOWNVOTE = 0;
    private static final int REQUEST_STORAGE = 1;
    private ModuleItem data;
    private ExpandableGroup relatedVideosGroup;
    private ExpandableGroup relatedCoursesGroup;
    private ExpandableGroup relatedTopicVideosGroup;
    private GridLayoutManager layoutManager;
    private RelatedVideosViewHolder relatedVideosViewHolder;
    private RelatedCoursesViewHolder relatedCoursesViewHolder;
    private RelatedVideosHeader relatedVideosHeader;
    private RelatedCoursesHeader relatedCoursesHeader;
    private RelatedSuggestionHeader relatedTopicVideosHeader;
    private RelatedSuggestionViewHolder relatedTopicVideosViewHolder;
    private ShowMoreViewHolder relatedShowMoreViewHolder, relatedCourseShowMoreViewHolder, relatedTopicVideosShowMoreViewHolder;
    private String mParam1;
    private String mParam2;
    private GroupAdapter<ViewHolder> suggestionListAdapter;
    private VideoContentDetailsAdapter videoContentDetailsAdapter;
    private Handler handler = new Handler();
    private VideoEntity videoEntity;
    private Call<VoteVideoResponse> upVoteCall;
    private Call<VoteVideoResponse> downVoteCall;
    private long enqueue;
    private DownloadManager downloadManager;
    private BroadcastReceiver receiver;
    private Context context;
    private RecyclerView recyclerView;
    private AppCompatTextView error_text;
    private ProgressBar content_progress;

    public SuggestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestionFragment.
     */
    public static SuggestionFragment newInstance(String param1, String param2) {
        SuggestionFragment fragment = new SuggestionFragment();
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
        return inflater.inflate(R.layout.fragment_suggestion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        error_text = view.findViewById(R.id.error_text);
        content_progress = view.findViewById(R.id.content_progress);
        error_text.setVisibility(View.GONE);
        int betweenPadding = getResources().getDimensionPixelSize(R.dimen.padding_small);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new HeaderItemDecoration(Color.WHITE, betweenPadding));
        recyclerView.addItemDecoration(new InsetItemDecoration(Color.WHITE, betweenPadding));
        betweenPadding = getResources().getDimensionPixelSize(R.dimen.padding_video_header);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(betweenPadding, R.layout.video_header));


        //recyclerView.setAdapter(videoAdapter);
    }

    private void refreshDataFromPlayer() {
        /************************************************************
         / Suggestion list adapter
         /*************************************************************/
        //edge case
        if (recyclerView == null) return;
        suggestionListAdapter = new GroupAdapter<>();
        layoutManager = new GridLayoutManager(getActivity(), suggestionListAdapter.getSpanCount());
        layoutManager.setSpanSizeLookup(suggestionListAdapter.getSpanSizeLookup());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(suggestionListAdapter);
        data = getItemDetails(getActivity().getIntent().getStringExtra(KEY_MODULE_ITEM_ID));

        // Video content details header at first position
        //videoContentDetailsAdapter = new VideoContentDetailsAdapter(data, null);
        //suggestionListAdapter.add(0, videoContentDetailsAdapter);

        // Related videos header and content
        relatedVideosHeader = new RelatedVideosHeader("Related Videos", "Description goes here.");
        relatedVideosHeader.setToggleListener(this);
        relatedVideosGroup = new ExpandableGroup(relatedVideosHeader);
        suggestionListAdapter.add(suggestionListAdapter.getItemCount(), relatedVideosGroup);

        // Related courses header and content
        relatedCoursesHeader = new RelatedCoursesHeader("Related Courses", "Description goes here.");
        relatedCoursesHeader.setToggleListener(this);
        relatedCoursesGroup = new ExpandableGroup(relatedCoursesHeader);


        suggestionListAdapter.add(suggestionListAdapter.getItemCount(), relatedCoursesGroup);

        // Related header and content

        relatedTopicVideosHeader = new RelatedSuggestionHeader("Related Topics Videos", "Description goes here.");
        relatedTopicVideosHeader.setToggleListener(this);
        relatedTopicVideosGroup = new ExpandableGroup(relatedTopicVideosHeader);


        suggestionListAdapter.add(suggestionListAdapter.getItemCount(), relatedTopicVideosGroup);

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        //apiInterface.getRelatedVideos("token",new RelatedVideosBody(data.getItemVideo(), getActivity().getIntent().getStringExtra(KEY_COURSE_ID)));
        String token = getToken();
        Call<RelatedVideos> call = apiInterface.getRelatedVideos(token, new RelatedVideosBody("creating-map-with-sorter-transformation-1", "informatica-powercenter-basics"));
        call.enqueue(new Callback<RelatedVideos>() {
            @Override
            public void onResponse(Call<RelatedVideos> call, Response<RelatedVideos> response) {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        RelatedVideos relatedVideos = response.body();
                        for (RelatedVideo relatedVideo : relatedVideos.getRelatedVideos()) {
                            relatedVideosViewHolder = new RelatedVideosViewHolder(relatedVideo);
                            relatedVideosGroup.add(relatedVideosViewHolder);
                        }
                        relatedShowMoreViewHolder = new ShowMoreViewHolder();
                        relatedShowMoreViewHolder.setShowMoreClickListener(SuggestionFragment.this);
                        relatedVideosGroup.add(relatedShowMoreViewHolder);

                        for (RelatedCourse relatedCourse : relatedVideos.getRelatedCourses()) {
                            relatedCoursesViewHolder = new RelatedCoursesViewHolder(relatedCourse);
                            relatedCoursesGroup.add(relatedCoursesViewHolder);
                        }
                        relatedCourseShowMoreViewHolder = new ShowMoreViewHolder();
                        relatedCourseShowMoreViewHolder.setShowMoreClickListener(SuggestionFragment.this);
                        relatedCoursesGroup.add(relatedCourseShowMoreViewHolder);

                        for (RelatedTopicsVideo relatedTopicsVideo : relatedVideos.getRelatedTopicsVideos()) {
                            relatedTopicVideosViewHolder = new RelatedSuggestionViewHolder(relatedTopicsVideo);
                            relatedTopicVideosGroup.add(relatedTopicVideosViewHolder);
                        }
                        relatedTopicVideosShowMoreViewHolder = new ShowMoreViewHolder();
                        relatedTopicVideosShowMoreViewHolder.setShowMoreClickListener(SuggestionFragment.this);
                        relatedTopicVideosGroup.add(relatedTopicVideosShowMoreViewHolder);

                        break;
                }
            }

            @Override
            public void onFailure(Call<RelatedVideos> call, Throwable t) {

            }
        });
    }

    /***
     * Set Current Video track title
     * @param moduleId
     */
    private ModuleItem getItemDetails(final String moduleId) {
        Realm realm = Realm.getDefaultInstance();

        try {
            ModuleItem item = realm.where(ModuleItem.class).equalTo("itemId", moduleId).findFirst();
            return item;
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, "setVideoTitle: " + e.getMessage());
        } finally {
            realm.close();
        }
        return null;
    }

    @Override
    public void onToggle(ViewHolder viewHolder, int position, boolean isExpanded) {
        try {
            if (suggestionListAdapter.getItem(viewHolder) instanceof RelatedVideosHeader) {
                if (relatedCoursesGroup.isExpanded()) relatedCoursesGroup.onToggleExpanded();
                if (relatedTopicVideosGroup.isExpanded())
                    relatedTopicVideosGroup.onToggleExpanded();
            } else if (suggestionListAdapter.getItem(viewHolder) instanceof RelatedCoursesHeader) {
                if (relatedVideosGroup.isExpanded()) relatedVideosGroup.onToggleExpanded();
                if (relatedTopicVideosGroup.isExpanded())
                    relatedTopicVideosGroup.onToggleExpanded();
            } else if (suggestionListAdapter.getItem(viewHolder) instanceof RelatedSuggestionHeader) {
                if (relatedVideosGroup.isExpanded()) relatedVideosGroup.onToggleExpanded();
                if (relatedCoursesGroup.isExpanded()) relatedCoursesGroup.onToggleExpanded();
            }
        } catch (Exception e) {
            Log.e(TAG, "onToggle: " + e.getCause());
        }

    }

    @Override
    public void onShowMoreClickListener(ViewHolder viewHolder, int position, int page) {
        // Get group from chile item view
        ExpandableGroup expandableGroup = (ExpandableGroup) suggestionListAdapter.getGroup(suggestionListAdapter.getItem(viewHolder));
        if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedVideosHeader) {
            /*for (int i = 0; i < 5; i++) {
                //relatedVideosGroup.add(relatedVideosGroup.getChildCount() - 1, new RelatedVideosViewHolder());
            }*/
        } else if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedCoursesHeader) {
            /*for (int i = 0; i < 5; i++) {
                //relatedCoursesGroup.add(relatedCoursesGroup.getChildCount() - 1, new RelatedCoursesViewHolder());
            }*/
        } else if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedSuggestionHeader) {
            /*for (int i = 0; i < 5; i++) {
                //relatedTopicVideosGroup.add(relatedTopicVideosGroup.getChildCount() - 1, new RelatedSuggestionViewHolder());
            }*/
        }
    }

    /**
     * Update video details when received response
     *
     * @param body
     */
    public void updateVideoContent(VideoEntity body, boolean isLoading, boolean isError) {
        content_progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        error_text.setVisibility(isError ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(View.GONE);
        if (isLoading || isError || body == null) return;
        recyclerView.setVisibility(View.VISIBLE);
        refreshDataFromPlayer();
        this.videoEntity = body;
        videoContentDetailsAdapter = new VideoContentDetailsAdapter(data, body);
        videoContentDetailsAdapter.setOnItemListener(SuggestionFragment.this);
        suggestionListAdapter.add(0, videoContentDetailsAdapter);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OfflineVideo offloneVideo = getOfflineVideo(videoEntity.getVdoId());
                if (offloneVideo != null && offloneVideo.isValid()) {
                    List<Boolean> booleans = new ArrayList<>();
                    booleans.add(true);
                    videoContentDetailsAdapter.notifyChanged(booleans);
                }
            }
        }, 500);

    }

    @Override
    public void onUpVote(final ViewHolder item, int position, final VideoEntity videoEntity) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (videoEntity.getUpv() == STATUS_NOT_UPVOTE && videoEntity.getDwn() == STATUS_NOT_DOWNVOTE) {
                    // count by 1
                    videoEntity.setUpv(STATUS_UPVOTED);
                    videoEntity.setDwn(STATUS_NOT_DOWNVOTE);
                    if (Integer.parseInt(videoEntity.getUpvCnt()) >= 0)
                        videoEntity.setUpvCnt(Integer.toString(Integer.parseInt(videoEntity.getUpvCnt()) + 1));
                    videoContentDetailsAdapter.setVideoEntity(videoEntity);
                    videoContentDetailsAdapter.notifyChanged("UPVOTE");
                    initUpVoteAPI(0, 1, data.getItemTypeId());
                } else if (videoEntity.getUpv() == STATUS_UPVOTED) {
                    // if liked ==> dislike
                    videoEntity.setUpv(STATUS_NOT_UPVOTE);
                    videoEntity.setDwn(STATUS_NOT_DOWNVOTE);
                    if (Integer.parseInt(videoEntity.getUpvCnt()) >= 0)
                        videoEntity.setUpvCnt(Integer.toString(Integer.parseInt(videoEntity.getUpvCnt()) - 1));
                    videoContentDetailsAdapter.setVideoEntity(videoEntity);
                    videoContentDetailsAdapter.notifyChanged("UPVOTE");
                    initUpVoteAPI(0, -1, data.getItemTypeId());
                } else if (videoEntity.getDwn() == STATUS_DOWNVOTED) {
                    // count -1
                    videoEntity.setUpv(STATUS_UPVOTED);
                    videoEntity.setDwn(STATUS_NOT_DOWNVOTE);
                    if (Integer.parseInt(videoEntity.getUpvCnt()) >= 0)
                        videoEntity.setUpvCnt(Integer.toString(Integer.parseInt(videoEntity.getUpvCnt()) + 1));
                    if (Integer.parseInt(videoEntity.getDwnCnt()) >= 0)
                        videoEntity.setDwnCnt(Integer.toString(Integer.parseInt(videoEntity.getDwnCnt()) - 1));
                    videoContentDetailsAdapter.setVideoEntity(videoEntity);
                    videoContentDetailsAdapter.notifyChanged("UPVOTE");
                    initUpVoteAPI(-1, 1, data.getItemTypeId());
                }


            }
        }, 500);

    }

    @Override
    public void onDownVote(ViewHolder item, int position, final VideoEntity videoEntity) {
        if (!Utility.isNetworkAvailable()) {
            Utility.showMessage(getString(R.string.network_connectivity_error_message));
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoEntity.getUpv() == STATUS_NOT_UPVOTE && videoEntity.getDwn() == STATUS_NOT_DOWNVOTE) {
                    // count by 1
                    videoEntity.setUpv(STATUS_NOT_UPVOTE);
                    videoEntity.setDwn(STATUS_DOWNVOTED);
                    if (Integer.parseInt(videoEntity.getDwnCnt()) >= 0)
                        videoEntity.setDwnCnt(Integer.toString(Integer.parseInt(videoEntity.getDwnCnt()) + 1));
                    videoContentDetailsAdapter.setVideoEntity(videoEntity);
                    videoContentDetailsAdapter.notifyChanged("UPVOTE");
                    initDownVoteAPI(1, 0, data.getItemTypeId());
                } else if (videoEntity.getUpv() == STATUS_UPVOTED) {
                    // count -1
                    videoEntity.setUpv(STATUS_NOT_UPVOTE);
                    videoEntity.setDwn(STATUS_DOWNVOTED);
                    if (Integer.parseInt(videoEntity.getUpvCnt()) >= 0)
                        videoEntity.setUpvCnt(Integer.toString(Integer.parseInt(videoEntity.getUpvCnt()) - 1));
                    if (Integer.parseInt(videoEntity.getDwnCnt()) >= 0)
                        videoEntity.setDwnCnt(Integer.toString(Integer.parseInt(videoEntity.getDwnCnt()) + 1));
                    videoContentDetailsAdapter.setVideoEntity(videoEntity);
                    videoContentDetailsAdapter.notifyChanged("UPVOTE");
                    initDownVoteAPI(1, -1, data.getItemTypeId());
                } else if (videoEntity.getDwn() == STATUS_DOWNVOTED) {
                    // if liked ==> dislike
                    videoEntity.setUpv(STATUS_NOT_UPVOTE);
                    videoEntity.setDwn(STATUS_NOT_DOWNVOTE);
                    if (Integer.parseInt(videoEntity.getDwnCnt()) >= 0)
                        videoEntity.setDwnCnt(Integer.toString(Integer.parseInt(videoEntity.getDwnCnt()) - 1));
                    videoContentDetailsAdapter.setVideoEntity(videoEntity);
                    videoContentDetailsAdapter.notifyChanged("UPVOTE");
                    initDownVoteAPI(-1, 0, data.getItemTypeId());
                }

            }
        }, 500);
    }

    /**
     * Check available space
     *
     * @return
     */
    public boolean isSizeAvailable(long size) {
        long SourceSize = 0L;
        long DestinationSize = 0L;
        SourceSize = (size) / (1024 * 1024);

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        long megAvailable = bytesAvailable / (1024 * 1024);
        DestinationSize = megAvailable;
        return SourceSize < DestinationSize;
    }

    @Override
    public void onDownloadVideo(ViewHolder item, int position, VideoEntity videoEntity) {

        //Get download file name
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {
            requestStoragePermission();
        } else {
            showAvailableOptions();
            //isSizeAvailable(videoEntity.getUrl());
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        } else {
            // TODO: 27/7/17 dont calculate size for now
            //isSizeAvailable(videoEntity.getUrl());
            showAvailableOptions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //downLoadVideo(videoEntity.getUrl());
            } else {
                Utility.showMessage("You now can not download the file");
            }
        }
    }

    /**
     * Upvote API
     *
     * @param downCount
     * @param upCount
     * @param videoId
     */

    private void initUpVoteAPI(int downCount, int upCount, String videoId) {

        if (upVoteCall != null && !upVoteCall.isExecuted() && !upVoteCall.isCanceled()) {
            upVoteCall.cancel();
        }
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        upVoteCall = apiInterface.upVoteVideo(getToken(), new VoteRequestBody(downCount, upCount, videoId));
        upVoteCall.enqueue(new Callback<VoteVideoResponse>() {
            @Override
            public void onResponse(Call<VoteVideoResponse> call, Response<VoteVideoResponse> response) {
                VoteVideoResponse voteVideoResponse = response.body();
                if (voteVideoResponse != null && !Utility.isEmpty(voteVideoResponse.getVdoId())) {
                    Utility.showMessage("Liked video");
                }
            }

            @Override
            public void onFailure(Call<VoteVideoResponse> call, Throwable t) {

            }
        });
    }

    /**
     * Down vote API
     *
     * @param downCount
     * @param upCount
     * @param videoId
     */
    private void initDownVoteAPI(int downCount, int upCount, String videoId) {
        if (downVoteCall != null && !downVoteCall.isExecuted() && !downVoteCall.isCanceled()) {
            downVoteCall.cancel();
        }
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        downVoteCall = apiInterface.upVoteVideo(getToken(), new VoteRequestBody(downCount, upCount, videoId));
        downVoteCall.enqueue(new Callback<VoteVideoResponse>() {
            @Override
            public void onResponse(Call<VoteVideoResponse> call, Response<VoteVideoResponse> response) {
                VoteVideoResponse voteVideoResponse = response.body();
                if (voteVideoResponse != null && !Utility.isEmpty(voteVideoResponse.getVdoId())) {
                    Utility.showMessage("Disliked video");
                }
            }

            @Override
            public void onFailure(Call<VoteVideoResponse> call, Throwable t) {

            }
        });
    }

    /**
     * Get Auth Token
     *
     * @return
     */
    private String getToken() {
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
        return token;
    }

    /**
     * Download video from url
     *
     * @param DOWNLOAD_URL
     */
    private void downLoadVideo(String DOWNLOAD_URL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        File file = new File(Environment.getExternalStorageDirectory().getPath() + FOLDER_NAME);
        if (!file.exists()) file.mkdir();
        downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOAD_URL));
        String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(DOWNLOAD_URL);
        String name = URLUtil.guessFileName(DOWNLOAD_URL, null, fileExtenstion);
        //Save file to destination folder
        request.setDestinationInExternalPublicDir(FOLDER_NAME, name);
        enqueue = downloadManager.enqueue(request);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e(TAG, "onPause: " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.COLUMN_STATUS);
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(receiver, intentFilter);

    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.e(TAG, "onReceive: " + action);
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(enqueue);
                        Cursor cursor = downloadManager.query(query);
                        if (cursor.moveToFirst()) {
                            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                                String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                Utility.showMessage("Download completed");
                            }

                        }
                    }
                }
            };
        }

    }


    private boolean fileExist(String fileName) {
        File myFile = new File(Environment.getExternalStorageDirectory().getPath() + FOLDER_NAME + File.separator + fileName);
        return myFile.exists();
    }

    private String filePath(String fileName) {
        File myFile = new File(Environment.getExternalStorageDirectory().getPath() + FOLDER_NAME + File.separator + fileName);
        return myFile.getAbsolutePath();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * Show available download options in resolution
     */
    private void showAvailableOptions() {

        if (videoEntity.getVideo_urls() != null && videoEntity.getVideo_urls().size() < 0) {
            Utility.showMessage("This video is not available to download");
            return;
        }
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Save video offline");

        // add a list
        String[] downloadInfo = new String[videoEntity.getVideo_urls().size()];
        for (int i = 0; i < videoEntity.getVideo_urls().size(); i++) {
            downloadInfo[i] = String.format("%sp size %s", videoEntity.getVideo_urls().get(i).getRes(), Utility.getSizeFromKb(videoEntity.getVideo_urls().get(i).getVideoFileSize()));
        }
        builder.setItems(downloadInfo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long fileSize = videoEntity.getVideo_urls().get(which).getVideoFileSize();
                String videoUrl = videoEntity.getVideo_urls().get(which).getDownloadUrl();
                if (Utility.isEmpty(videoUrl)) {
                    Utility.showMessage("Not valid url");
                    return;
                }
                String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(videoUrl);
                String name = URLUtil.guessFileName(videoUrl, null, fileExtenstion);
                if (fileExist(name)) {
                    Utility.showMessage("File already saved");
                } else {
                    if (isSizeAvailable(fileSize)) {
                        Utility.showMessage("Downloading video");
                        downLoadVideo(videoUrl);
                        saveOfflineVideo(videoEntity.getVdoId(), name, videoUrl, filePath(name));
                    } else {
                        Utility.showMessage("No space available to download this video");
                    }

                }
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

    /**
     * Save offline video
     *
     * @param videoId
     * @param videoUrl
     * @param videoLocalPath
     */
    private void saveOfflineVideo(final String videoId, final String videoName, final String videoUrl, final String videoLocalPath) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    OfflineVideo offlineVideo = new OfflineVideo();
                    offlineVideo.setVideoId(videoId);
                    offlineVideo.setVideoName(videoName);
                    offlineVideo.setVideoUrl(videoUrl);
                    offlineVideo.setLocalPath(videoLocalPath);
                    realm.insertOrUpdate(offlineVideo);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {

                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {

                }
            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, "saveOfflineVideo: " + e.getMessage());
        } finally {
            if (realm != null) realm.close();
        }
    }

    /**
     * Get offline video if available
     *
     * @param videoId
     * @return
     */
    private OfflineVideo getOfflineVideo(String videoId) {
        OfflineVideo offlineVideo = null;
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            offlineVideo =
                    realm.copyFromRealm(realm.where(OfflineVideo.class).equalTo("videoId", videoId).findFirst());
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, "getOfflineVideo: " + e.getMessage());
        } finally {
            if (realm != null) realm.close();
        }
        return offlineVideo;
    }
}
