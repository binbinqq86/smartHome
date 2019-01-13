package com.tb.baselib.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by : tb on 2017/9/25 下午1:51.
 * Description :图片展示接口
 */
public interface IImageDisplay {
    /**
     * 清除Glide内存缓存 只能在主线程执行
     *
     * @return
     */
    boolean clearMemoryCache();

    /**
     * 清除图片磁盘缓存 子线程执行
     *
     * @return
     */
    boolean clearDiskCache();

    /**
     * 加载网络图片
     *
     * @param mContext  不要传递Application
     * @param imageView 要显示图片的控件
     * @param param     可以为url,file,uri,resourceId,byte[]...
     */
    void displayImg(Context mContext, ImageView imageView, Object param);
    void displayImg(Context mContext, ImageView imageView, Object param,int placeHolder,int errorHolder);
//    void displayRoundCornerImg();
//    void displayCircleImg();
//    void displayBlurImg();
}
