package app.mobile.examwarrior.adapters.explorer;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.listener.ExploreCardClickListener;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.widget.CustomFontTextView;


public class ExploreCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<CourseCategories> arraylist;
    private Activity activity;
    private ExploreCardClickListener moreClickListener;

    //create constructor to initializ context and data sent from main activity.
    public ExploreCategoryAdapter(Activity activity, ArrayList<CourseCategories> playlistWithVideoses) {
        this.arraylist = playlistWithVideoses;
        this.activity = activity;
        this.moreClickListener = (ExploreCardClickListener) activity;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_horizontal_recycle_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        MyHolder holder = (MyHolder) viewHolder;
        if (arraylist.get(position).getCourse_cat_title() != null) {
            holder.textData.setText(arraylist.get(position).getCourse_cat_title().toUpperCase());
        }

        holder.horizontalVideoListAdapter = new ExploreHorizontalAdapter(position, activity, arraylist.get(position).getMcourses());
        holder.horizontalVideoList.setAdapter(holder.horizontalVideoListAdapter);
        holder.horizontalVideoListAdapter.notifyDataSetChanged();
        holder.moreVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreClickListener.getMoreCources(position, arraylist.get(position).getCourse_cat_id());
            }
        });
        holder.moreVideos.setTag(position - 1);

    }

    @Override
    public int getItemCount() {

        return arraylist != null ? arraylist.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        CustomFontTextView textData;
        TextView moreVideos;
        RecyclerView horizontalVideoList;
        ExploreHorizontalAdapter horizontalVideoListAdapter;
        List<CourseCategories.McoursesBean> data = new ArrayList<>();

        //contructor for getting reference to the widget
        public MyHolder(View itemView) {
            super(itemView);

            textData = itemView.findViewById(R.id.textPropertyName);
            moreVideos = (TextView) itemView.findViewById(R.id.moreButton);
            horizontalVideoList = (RecyclerView) itemView.findViewById(R.id.horizontalVideoList);
            LinearLayoutManager imageLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            horizontalVideoList.setLayoutManager(imageLayoutManager);
            horizontalVideoList.setNestedScrollingEnabled(false);

        }

        public void setDaa(List<CourseCategories.McoursesBean> dataValues) {
            if (dataValues != null && !dataValues.isEmpty()) {
                data.clear();
                data.addAll(dataValues);
            }
        }
    }
}

