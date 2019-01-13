package com.tb.baselib.image;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * @auther tb
 * @time 2018/2/23 下午5:39
 * @desc
 */
public class DefaultImageViewTarget extends GlideDrawableImageViewTarget {
    
    /**
     * 这里的scaleType并非glide处理，而是imageView自己绘制处理
     */
    protected ImageView.ScaleType scaleType;
    
    /**
     * @param view
     */
    public DefaultImageViewTarget(ImageView view, ImageView.ScaleType scaleType) {
        super(view);
        if (scaleType == null) {
            this.scaleType = ImageView.ScaleType.CENTER_CROP;
        } else {
            this.scaleType = scaleType;
        }
    }
    
    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setScaleType(scaleType);
        super.onLoadFailed(e, errorDrawable);
    }
    
    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setScaleType(scaleType);
        super.onResourceReady(resource, animation);
    }
    
    @Override
    public void onLoadStarted(Drawable placeholder) {
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setScaleType(scaleType);
        super.onLoadStarted(placeholder);
    }
}
