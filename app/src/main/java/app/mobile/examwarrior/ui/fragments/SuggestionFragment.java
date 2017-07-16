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

import java.util.Vector;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.demo.ToggleListener;
import app.mobile.examwarrior.players.VideoAdapter;
import app.mobile.examwarrior.players.YoutubeVideo;
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

    private ExpandableGroup relatedVideosGroup;
    private ExpandableGroup relatedCoursesGroup;
    private ExpandableGroup relatedSuggestionGroup;
    private GridLayoutManager layoutManager;
    private RelatedVideosViewHolder relatedVideosViewHolder;
    private RelatedCoursesViewHolder relatedCoursesViewHolder;
    private RelatedVideosHeader relatedVideosHeader;
    private RelatedCoursesHeader relatedCoursesHeader;
    private RelatedSuggestionHeader relatedSuggestionHeader;
    private RelatedSuggestionViewHolder relatedSuggestionViewHolder;
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


        Vector<YoutubeVideo> youtubeVideos = new Vector<YoutubeVideo>();

        //Load video List
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));

        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>"));


        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        /************************************************************
         / Suggestion list adapter
         /*************************************************************/
        suggestionListAdapter = new GroupAdapter<>();
        layoutManager = new GridLayoutManager(getActivity(), suggestionListAdapter.getSpanCount());
        layoutManager.setSpanSizeLookup(suggestionListAdapter.getSpanSizeLookup());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(suggestionListAdapter);

        // Video content details header at first position
        videoContentDetailsAdapter = new VideoContentDetailsAdapter();
        suggestionListAdapter.add(0, videoContentDetailsAdapter);

        // Related videos header and content
        relatedVideosHeader = new RelatedVideosHeader("Related Videos", "Description goes here.");
        relatedVideosHeader.setToggleListener(this);
        relatedVideosGroup = new ExpandableGroup(relatedVideosHeader);

        for (int i = 0; i < 5; i++) {
            relatedVideosViewHolder = new RelatedVideosViewHolder();
            relatedVideosGroup.add(relatedVideosViewHolder);
        }
        ShowMoreViewHolder showMoreViewHolder2 = new ShowMoreViewHolder();
        showMoreViewHolder2.setShowMoreClickListener(this);
        showMoreViewHolder2.setPageGroup(1);
        relatedVideosGroup.add(showMoreViewHolder2);
        suggestionListAdapter.add(1, relatedVideosGroup);

        // Related courses header and content
        relatedCoursesHeader = new RelatedCoursesHeader("Related Courses", "Description goes here.");
        relatedCoursesHeader.setToggleListener(this);
        relatedCoursesGroup = new ExpandableGroup(relatedCoursesHeader);

        for (int i = 0; i < 5; i++) {
            relatedCoursesViewHolder = new RelatedCoursesViewHolder();
            relatedCoursesGroup.add(relatedCoursesViewHolder);
        }
        ShowMoreViewHolder showMoreViewHolder = new ShowMoreViewHolder();
        showMoreViewHolder.setShowMoreClickListener(this);
        showMoreViewHolder.setPageGroup(1);
        relatedCoursesGroup.add(showMoreViewHolder);
        suggestionListAdapter.add(2, relatedCoursesGroup);

        // Related header and content

        relatedSuggestionHeader = new RelatedSuggestionHeader("Related Suggestion", "Description goes here.");
        relatedSuggestionHeader.setToggleListener(this);
        relatedSuggestionGroup = new ExpandableGroup(relatedSuggestionHeader);

        for (int i = 0; i < 5; i++) {
            relatedSuggestionViewHolder = new RelatedSuggestionViewHolder();
            relatedSuggestionGroup.add(relatedCoursesViewHolder);
        }
        ShowMoreViewHolder showMoreViewHolder1 = new ShowMoreViewHolder();
        showMoreViewHolder1.setShowMoreClickListener(this);
        showMoreViewHolder1.setPageGroup(1);
        relatedSuggestionGroup.add(showMoreViewHolder1);
        suggestionListAdapter.add(3, relatedSuggestionGroup);
        //recyclerView.setAdapter(videoAdapter);
    }

    @Override
    public void onToggle(ViewHolder viewHolder, int position, boolean isExpanded) {
        try {
            if (suggestionListAdapter.getItem(viewHolder) instanceof RelatedVideosHeader) {
                if (relatedCoursesGroup.isExpanded()) relatedCoursesGroup.onToggleExpanded();
                if (relatedSuggestionGroup.isExpanded()) relatedSuggestionGroup.onToggleExpanded();
            } else if (suggestionListAdapter.getItem(viewHolder) instanceof RelatedCoursesHeader) {
                if (relatedVideosGroup.isExpanded()) relatedVideosGroup.onToggleExpanded();
                if (relatedSuggestionGroup.isExpanded()) relatedSuggestionGroup.onToggleExpanded();
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
                relatedVideosGroup.add(relatedVideosGroup.getChildCount() - 1, new RelatedVideosViewHolder());
            }
        } else if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedCoursesHeader) {
            for (int i = 0; i < 5; i++) {
                relatedCoursesGroup.add(relatedCoursesGroup.getChildCount() - 1, new RelatedCoursesViewHolder());
            }
        } else if (expandableGroup.getGroup(INITIAL_GROUP_POSITION) instanceof RelatedSuggestionHeader) {
            for (int i = 0; i < 5; i++) {
                relatedSuggestionGroup.add(relatedSuggestionGroup.getChildCount() - 1, new RelatedSuggestionViewHolder());
            }
        }
    }
}
