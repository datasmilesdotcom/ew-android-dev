package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;

import java.util.List;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.Course;
import app.mobile.examwarrior.ui.activity.CourseDetailsActivity;
import app.mobile.examwarrior.util.Utility;
import app.mobile.examwarrior.widget.CustomFontTextView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by sandesh on 16/1/17.
 */

public class CoursesListAdapter extends RealmRecyclerViewAdapter<Course, RecyclerView.ViewHolder> {

    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;
    private static final int LAYOUT_TYPE = 2;
    private static final int LAYOUT_TYPE_LIST = 2;
    private static final int LAYOUT_TYPE_GRID = 3;
    private Context context;


    public CoursesListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Course> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View v;
        switch (viewType) {
            case LAYOUT_TYPE_LIST:
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.course_list_row, parent, false);
                vh = new CoursesViewHolder(v);
                break;
            case LAYOUT_TYPE_GRID:
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.course_row_grid, parent, false);
                vh = new CoursesViewHolder(v);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_TYPE_LIST:
                if (getData() != null && getData().get(position).isValid())
                    bindCourseViewHolder(holder, null, position, getData().get(position));
                break;
            case LAYOUT_TYPE_GRID:
                if (getData() != null && getData().get(position).isValid())
                    bindCourseViewHolder(holder, null, position, getData().get(position));
                break;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    private void bindCourseViewHolder(RecyclerView.ViewHolder holder, String data, int position, Course sample) {
        CoursesViewHolder coursesViewHolder = (CoursesViewHolder) holder;
        coursesViewHolder.courseTitle.setText(sample.getCourseName());
        Glide.with(context).load("https://unsplash.it/200/200/?random").signature(new StringSignature(sample.getId())).thumbnail(0.4f).bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.placeholder_).placeholder(R.drawable.placeholder_).into(coursesViewHolder.courseImage);
    }

    @Override
    public int getItemCount() {
        if (getData() != null && !getData().isEmpty()) {
            return getData().size();
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        return LAYOUT_TYPE_LIST;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }

    private class CoursesViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView courseImage;
        private CustomFontTextView courseTitle;
        private AppCompatImageView linkedin;
        private AppCompatImageView fb;
        private AppCompatImageView twitter;
        private AppCompatImageView whatsapp;

        private CoursesViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra(CourseDetailsActivity.KEY_COURSE_ID, getData().get(getAdapterPosition()).getCourseId());
                    context.startActivity(intent);
                }
            });
            courseImage = (AppCompatImageView) view.findViewById(R.id.course_image);
            courseTitle = (CustomFontTextView) view.findViewById(R.id.course_title);
            linkedin = (AppCompatImageView) view.findViewById(R.id.linkedin);
            fb = (AppCompatImageView) view.findViewById(R.id.fb);
            twitter = (AppCompatImageView) view.findViewById(R.id.twitter);
            whatsapp = (AppCompatImageView) view.findViewById(R.id.whatsapp);
        }
    }
}
