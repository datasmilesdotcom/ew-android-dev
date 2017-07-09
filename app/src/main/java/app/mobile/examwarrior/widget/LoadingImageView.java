package app.mobile.examwarrior.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;

import java.lang.ref.WeakReference;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.util.Utility;

/**
 * Created by sandesh on 27/1/17.
 */

public class LoadingImageView extends FrameLayout {
    private WeakReference<ProgressBar> progressBarWeakReference;
    private WeakReference<AspectRatioImageView> aspectRatioImageViewWeakReference;
    private View view;
    private String uri;
    private String sign;
    private Context context;
    private BitmapImageViewTarget bitmapImageViewTarget = null;

    public LoadingImageView(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.image_progress, this);
        this.context = context;
    }

    public WeakReference<ProgressBar> getProgressBarWeakReference() {
        return progressBarWeakReference;
    }

    public void setProgressBarWeakReference(WeakReference<ProgressBar> progressBarWeakReference) {
        this.progressBarWeakReference = progressBarWeakReference;
    }

    public WeakReference<AspectRatioImageView> getAspectRatioImageViewWeakReference() {
        return aspectRatioImageViewWeakReference;
    }

    public void setAspectRatioImageViewWeakReference(WeakReference<AspectRatioImageView> aspectRatioImageViewWeakReference) {
        this.aspectRatioImageViewWeakReference = aspectRatioImageViewWeakReference;
    }

    public BitmapImageViewTarget getBitmapImageViewTarget() {
        return bitmapImageViewTarget;
    }

    public void setBitmapImageViewTarget(BitmapImageViewTarget bitmapImageViewTarget) {
        this.bitmapImageViewTarget = bitmapImageViewTarget;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void loadImage(String url, String signature, RequestManager requestManager) {
        if (progressBarWeakReference == null)
            progressBarWeakReference = new WeakReference<>((ProgressBar) view.findViewById(R.id.progress_img));
        if (aspectRatioImageViewWeakReference == null)
            aspectRatioImageViewWeakReference = new WeakReference<>((AspectRatioImageView) view.findViewById(R.id.icon___));
        if (Utility.isEmpty(url) || Utility.isEmpty(signature)) return;
        bitmapImageViewTarget = new BitmapImageViewTarget(aspectRatioImageViewWeakReference.get()) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                progressBarWeakReference.get().setVisibility(VISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                progressBarWeakReference.get().setVisibility(VISIBLE);
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {
                super.onLoadCleared(placeholder);
                progressBarWeakReference.get().setVisibility(VISIBLE);
            }

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                progressBarWeakReference.get().setVisibility(GONE);
            }

        };
        requestManager.load(url).asBitmap().atMost().override(1080, 680).format(DecodeFormat.PREFER_RGB_565).signature(new StringSignature(signature)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bitmapImageViewTarget);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
