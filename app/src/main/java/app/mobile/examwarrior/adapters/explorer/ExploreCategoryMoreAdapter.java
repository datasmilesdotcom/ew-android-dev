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
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.Mcourses;

import static app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter.ITEM_BOOK;
import static app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter.ITEM_COURSES;
import static app.mobile.examwarrior.adapters.explorer.ExploreSubCatListAdapter.ITEM_TUTOR;


public class ExploreCategoryMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Object> arraylist;
    private int ITEM_TYPE = 0;
    private Activity activity;
    private ExploreCardClickListener moreClickListener;

    //create constructor to initializ context and data sent from main activity.
    public ExploreCategoryMoreAdapter(Activity activity, ArrayList<Object> playlistWithVideoses, int item_type) {
        this.arraylist = playlistWithVideoses;
        this.activity = activity;
        this.moreClickListener = (ExploreCardClickListener) activity;
        ITEM_TYPE = item_type;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_COURSES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_horizontal_more_recycle_item, parent, false);
                viewHolder = new MyHolder(view);
                break;
            case ITEM_BOOK:
                break;
            case ITEM_TUTOR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_horizontal_more_recycle_item, parent, false);
                viewHolder = new MyHolder(view);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        if (arraylist.get(position) != null) {
            Typeface face = null;
            switch (viewHolder.getItemViewType()) {
                case ITEM_COURSES:
                    MyHolder holder = (MyHolder) viewHolder;
                    final CourseMoreCategories courseMoreCategories = (CourseMoreCategories) arraylist.get(position);
                    if (courseMoreCategories.getMcoursename() != null) {
                        holder.textData.setText(courseMoreCategories.getMcoursename().toUpperCase());
                    }
                    face = Typeface.createFromAsset(activity.getAssets(),
                            "fonts/Montserrat-Bold.ttf");
                    holder.textData.setTypeface(face);
                    holder.horizontalVideoListAdapter = new ExploreHorizontalMoreAdapter(activity, new ArrayList<Object>(courseMoreCategories.getCourses()), position, viewHolder.getItemViewType());
                    holder.horizontalVideoList.setAdapter(holder.horizontalVideoListAdapter);
                    holder.horizontalVideoListAdapter.notifyDataSetChanged();
                    holder.about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(courseMoreCategories.getAbout_mcourse()));
                            activity.startActivity(browserIntent);
                        }
                    });
                    holder.subscribe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(courseMoreCategories.getBuy()));
                            activity.startActivity(browserIntent);
                        }
                    });
                    holder.about.setTag(position);
                    holder.subscribe.setTag(position);
                    break;
                case ITEM_TUTOR:
                    MyHolder tutor_holder = (MyHolder) viewHolder;
                    final Mcourses mcourses = (Mcourses) arraylist.get(position);
                    if (mcourses.getMcoursename() != null) {
                        tutor_holder.textData.setText(mcourses.getMcoursename().toUpperCase());
                    }
                    face = Typeface.createFromAsset(activity.getAssets(),
                            "fonts/Montserrat-Bold.ttf");
                    tutor_holder.textData.setTypeface(face);
                    tutor_holder.horizontalVideoListAdapter = new ExploreHorizontalMoreAdapter(activity, new ArrayList<Object>(mcourses.getTutors()), position, viewHolder.getItemViewType());
                    tutor_holder.horizontalVideoList.setAdapter(tutor_holder.horizontalVideoListAdapter);
                    tutor_holder.horizontalVideoListAdapter.notifyDataSetChanged();
                    tutor_holder.about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          /*  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(courseMoreCategories.getAbout_mcourse()));
                            activity.startActivity(browserIntent);*/
                        }
                    });
                    tutor_holder.subscribe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(courseMoreCategories.getBuy()));
                            activity.startActivity(browserIntent);*/
                        }
                    });
                    tutor_holder.about.setTag(position);
                    tutor_holder.subscribe.setTag(position);
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textData, subscribe, about;
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


        }

        public void setDaa(List<CourseMoreCategories.CoursesBean> dataValues) {
            if (dataValues != null) {
                data.clear();
                data.addAll(dataValues);
            }
        }


    }

}

