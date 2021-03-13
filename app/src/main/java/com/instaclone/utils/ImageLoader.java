package com.instaclone.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.instaclone.R;

import androidx.annotation.Nullable;

public class ImageLoader {

    public static void loadImage(Context context, ImageView imageView, String url) {

        if (!TextUtils.isEmpty(url))
            Glide.with(context).load(url).into(imageView);
    }

    public static void loadImageError(Context context, ImageView imageView, String url) {

        if (!TextUtils.isEmpty(url))
            Glide.with(context).load(url).error(R.drawable.profile).into(imageView);
    }

    public static void loadImageProgress(Context context, ImageView imageView, String url, ProgressBar progressBar) {

        if (!TextUtils.isEmpty(url))
            Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(imageView);
    }

}
