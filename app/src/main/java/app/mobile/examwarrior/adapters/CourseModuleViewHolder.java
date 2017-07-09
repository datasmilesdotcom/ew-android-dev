package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.RotateAnimation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.expandable_list.listeners.CourseHeader;
import app.mobile.examwarrior.expandable_list.viewholders.GroupViewHolder;
import app.mobile.examwarrior.widget.CustomFontTextView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by sandesh on 5/5/17, 2:26 AM.
 */

class CourseModuleViewHolder extends GroupViewHolder {
    private AppCompatImageView courseImage;
    private CustomFontTextView courseTitle;
    private AppCompatImageView linkedin;
    private AppCompatImageView fb;
    private AppCompatImageView twitter;
    private AppCompatImageView whatsapp;
    private CourseHeader courseHeader;

    public CourseModuleViewHolder(View view) {
        super(view);

        courseImage = (AppCompatImageView) view.findViewById(R.id.course_image);
        courseTitle = (CustomFontTextView) view.findViewById(R.id.course_title);
        linkedin = (AppCompatImageView) view.findViewById(R.id.linkedin);
        fb = (AppCompatImageView) view.findViewById(R.id.fb);
        twitter = (AppCompatImageView) view.findViewById(R.id.twitter);
        whatsapp = (AppCompatImageView) view.findViewById(R.id.whatsapp);
    }

    public CourseHeader getCourseHeader() {
        return courseHeader;
    }

    public void setCourseHeader(CourseHeader courseHeader) {
        this.courseHeader = courseHeader;
    }

    public AppCompatImageView getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(AppCompatImageView courseImage) {
        this.courseImage = courseImage;
    }

    public CustomFontTextView getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(CustomFontTextView courseTitle) {
        this.courseTitle = courseTitle;
    }

    public AppCompatImageView getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(AppCompatImageView linkedin) {
        this.linkedin = linkedin;
    }

    public void settitle(String title) {
        courseTitle.setText(title);
    }

    public void setImage(String url, Context context) {
        Glide.with(context).load("https://unsplash.it/200/200/?random").bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.placeholder_).placeholder(R.drawable.placeholder_).into(courseImage);
    }

    @Override
    public void expand() {
        //animateExpand();
    }

    @Override
    public void collapse() {
        //animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        courseTitle.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        courseTitle.setAnimation(rotate);
    }


}
