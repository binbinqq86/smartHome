package com.tb.baselib.image.impl;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tb.baselib.base.BaseApplication;
import com.tb.baselib.image.DefaultImageViewTarget;
import com.tb.baselib.image.IImageDisplay;
import com.tb.baselib.util.LogUtils;
import com.tb.baselib.widget.BaseImageView;

import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.ImageView.ScaleType.FIT_END;
import static android.widget.ImageView.ScaleType.FIT_START;

/**
 * Created by : tb on 2017/9/25 下午2:05.
 * Description :用来加载图片的工具类，可替换其他第三方
 */
public class GlideImageLoader implements IImageDisplay {
    private int imgWidth, imgHeight;
    
    private GlideImageLoader() {
    }
    
    public static final GlideImageLoader getInstance() {
        return GlideSingletonHolder.instance;
    }
    
    private static final class GlideSingletonHolder {
        
        private static final GlideImageLoader instance = new GlideImageLoader();
    }
    
    @Override
    public void displayImg(Context mContext, final ImageView imageView, Object param) {
        displayImg(mContext, imageView, param, 0, 0);
    }
    
    @Override
    public void displayImg(Context mContext, final ImageView imageView, Object param, @IdRes int placeHolder, @IdRes int errorHolder) {
        final DrawableRequestBuilder builder = Glide.with(mContext)
                .load(param)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //当为wrap情况时候，只有图片加载出来后才能得到控件宽高
                //所以需要固定图片宽高，否则控件宽高跟图片一样大了
                imgWidth = imageView.getMeasuredWidth();
                imgHeight = imageView.getMeasuredHeight();
                if (imgWidth > 0 && imgHeight > 0) {
                    //防止图片过大，如不设置，Glide默认会根据控件宽高去缩放图片(inSampleSize只能是2的倍数)
                    builder.override(imgWidth, imgHeight);
                }
            }
        });
        
        if (placeHolder > 0) {
            builder.placeholder(placeHolder);
        }
        if (errorHolder > 0) {
            builder.error(errorHolder);
        }
        if (imageView instanceof BaseImageView) {
            //baseImageView里面默认是centerCrop方式重绘的，所以只需要返回按照比例缩放后的原图即可
            builder.dontTransform();//glide默认就是该方式，可省略不写。。。
        } else {
            //普通的imageView只需要根据imageView的scaleType让imageView自己去处理即可，此处也是返回原图
            //......省略不写了。。。
        }
        builder.into(new DefaultImageViewTarget(imageView, null));
    }
    
    @Override
    public boolean clearDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(BaseApplication.application).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(BaseApplication.application).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean clearMemoryCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(BaseApplication.application).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
