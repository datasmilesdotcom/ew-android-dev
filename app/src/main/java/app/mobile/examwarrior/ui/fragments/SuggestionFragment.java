package app.mobile.examwarrior.ui.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;

import java.net.HttpURLConnection;

import app.mobile.examwarrior.BuildConfig;
import app.mobile.examwarrior.R;
import app.mobile.examwarrior.api.ApiInterface;
import app.mobile.examwarrior.api.ServiceGenerator;
import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.demo.ToggleListener;
import app.mobile.examwarrior.model.RelatedCourse;
import app.mobile.examwarrior.model.RelatedTopicsVideo;
import app.mobile.examwarrior.model.RelatedVideo;
import app.mobile.examwarrior.model.RelatedVideos;
import app.mobile.examwarrior.model.RelatedVideosBody;
import app.mobile.examwarrior.model.VideoEntity;
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

import static app.mobile.examwarrior.player.VideoPlayerFragment.KEY_MODULE_ITEM_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionFragment extends Fragment implements ToggleListener, ShowMoreViewHolder.ShowMoreClickListener {
    public static final String TAG = SuggestionFragment.class.getSimpleName();

    private static final int INITIAL_GROUP_POSITION = 0;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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


        int betweenPadding = getResources().getDimensionPixelSize(R.dimen.padding_small);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new HeaderItemDecoration(Color.WHITE, betweenPadding));
        recyclerView.addItemDecoration(new InsetItemDecoration(Color.WHITE, betweenPadding));
        betweenPadding = getResources().getDimensionPixelSize(R.dimen.padding_video_header);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(betweenPadding, R.layout.video_header));

        /************************************************************
         / Suggestion list adapter
         /*************************************************************/
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
        Call<RelatedVideos> call = apiInterface.getRelatedVideos("JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJzYXJqdSIsImlhdCI6MTQ5NzYyMTExNiwiZXhwIjoxNDk4MjI1OTE2fQ.raiwmRMC0C3G2axuQLOTYoSJNmoTdUyBZ5eiIskrjnA", new RelatedVideosBody("introduction-powercenter-1", "informatica-powercenter-basics"));
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
        //recyclerView.setAdapter(videoAdapter);
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
        // Get group from chile itemview
        ExpandableGroup expandableGroup = (ExpandableGroup) suggestionListAdapter.getGroup(suggestionListAdapter.getItem(viewHolder));
        if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedVideosHeader) {
            for (int i = 0; i < 5; i++) {
                //relatedVideosGroup.add(relatedVideosGroup.getChildCount() - 1, new RelatedVideosViewHolder());
            }
        } else if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedCoursesHeader) {
            for (int i = 0; i < 5; i++) {
                //relatedCoursesGroup.add(relatedCoursesGroup.getChildCount() - 1, new RelatedCoursesViewHolder());
            }
        } else if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedSuggestionHeader) {
            for (int i = 0; i < 5; i++) {
                //relatedTopicVideosGroup.add(relatedTopicVideosGroup.getChildCount() - 1, new RelatedSuggestionViewHolder());
            }
        }
    }

    /**
     * Update video details when received response
     *
     * @param body
     */
    public void updateVideoContent(VideoEntity body) {
        videoContentDetailsAdapter = new VideoContentDetailsAdapter(data, body);
        suggestionListAdapter.add(0, videoContentDetailsAdapter);
    }
}
