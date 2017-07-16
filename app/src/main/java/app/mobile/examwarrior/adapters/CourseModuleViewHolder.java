package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.expandable_list.viewholders.GroupViewHolder;
import app.mobile.examwarrior.widget.CustomFontTextView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


class CourseModuleViewHolder extends GroupViewHolder {
    private AppCompatImageView courseImage;
    private CustomFontTextView courseTitle;
    public AppCompatImageView info,share,bookmark,directionArrow;

    private CourseHeader courseHeader;
    private Context context;
    public CourseModuleViewHolder(View view,Context context) {
        super(view);
        this.context=context;
        courseImage = (AppCompatImageView) view.findViewById(R.id.course_image);
        courseTitle = (CustomFontTextView) view.findViewById(R.id.course_title);
        info = (AppCompatImageView) view.findViewById(R.id.star);
        share = (AppCompatImageView) view.findViewById(R.id.share);
        bookmark = (AppCompatImageView) view.findViewById(R.id.bookMark);
        directionArrow = (AppCompatImageView) view.findViewById(R.id.direction);
    }


    public void settitle(String title) {
        courseTitle.setText(title);
    }

    public void setImage(String url, Context context,int number) {
        Glide.with(context).load("https://s3.ap-south-1.amazonaws.com/dsn.ew.timages/"+number+".jpg").bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.placeholder_).placeholder(R.drawable.placeholder_).into(courseImage);
    }

    @Override
    public void expand() {
        directionArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus));
    }

    @Override
    public void collapse() {
        directionArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus));
    }

}
