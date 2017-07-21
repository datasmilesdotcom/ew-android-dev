package app.mobile.examwarrior.adapters.explorer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import app.mobile.examwarrior.model.CourseMoreCategories;


public class ExploreCategoryMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<CourseMoreCategories> arraylist;
    private Activity activity;
    private ExploreCardClickListener moreClickListener;

    //create constructor to initializ context and data sent from main activity.
    public ExploreCategoryMoreAdapter(Activity activity, ArrayList<CourseMoreCategories> playlistWithVideoses) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_horizontal_more_recycle_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        MyHolder holder = (MyHolder) viewHolder;
        if(arraylist.get(position).getMcoursename() !=null){
            holder.textData.setText(arraylist.get(position).getMcoursename().toUpperCase());
        }
        Typeface face = Typeface.createFromAsset(activity.getAssets(),
                "fonts/Montserrat-Bold.ttf");
        holder.textData.setTypeface(face);
        holder.setDaa(arraylist.get(position).getCourses());
        holder.horizontalVideoListAdapter.notifyDataSetChanged();
        holder.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arraylist.get(position).getAbout_mcourse()));
                activity.startActivity(browserIntent);
            }
        });
        holder.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arraylist.get(position).getBuy()));
                activity.startActivity(browserIntent);
            }
        });
        holder.about.setTag(position);
        holder.subscribe.setTag(position);

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textData, subscribe,about;
        RecyclerView horizontalVideoList;
        ExploreHorizontalMoreAdapter horizontalVideoListAdapter;
        List<CourseMoreCategories.CoursesBean> data = new ArrayList<>();

        //contructor for getting reference to the widget
        public MyHolder(View itemView) {
            super(itemView);

            textData = (TextView) itemView.findViewById(R.id.textPropertyName);
            about = (TextView) itemView.findViewById(R.id.About);
            subscribe = (TextView) itemView.findViewById(R.id.subscribe);
            horizontalVideoList = (RecyclerView) itemView.findViewById(R.id.horizontalVideoList);
            LinearLayoutManager imageLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            horizontalVideoList.setLayoutManager(imageLayoutManager);
            horizontalVideoList.setNestedScrollingEnabled(false);
            horizontalVideoListAdapter = new ExploreHorizontalMoreAdapter(activity, data);
            horizontalVideoList.setAdapter(horizontalVideoListAdapter);

        }

        public void setDaa(List<CourseMoreCategories.CoursesBean> dataValues) {
            data.clear();
            data.addAll(dataValues);
        }


    }

}

