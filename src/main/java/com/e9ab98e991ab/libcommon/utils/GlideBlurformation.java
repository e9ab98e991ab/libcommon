package com.e9ab98e991ab.libcommon.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/***
 * 模糊类
 */
public class GlideBlurformation extends BitmapTransformation {
    private Context context;
    private int radius,sampling;
    public GlideBlurformation(Context context, int radius, int sampling) {
        this.context = context;
        this.radius = radius;
        this.sampling = sampling;
    }
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.instance().blurBitmap(context,pool ,toTransform, outWidth,outHeight,radius,sampling);
    }
    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}