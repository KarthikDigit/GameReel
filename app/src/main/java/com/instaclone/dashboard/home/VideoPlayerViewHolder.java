package com.instaclone.dashboard.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.instaclone.R;
import com.instaclone.network.response.MyFeeds;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    FrameLayout media_container;
    TextView title;
    ImageView thumbnail, volumeControl;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        media_container = itemView.findViewById(R.id.media_container);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        title = itemView.findViewById(R.id.title);
        progressBar = itemView.findViewById(R.id.progressBar);
        volumeControl = itemView.findViewById(R.id.volume_control);
    }

    public void onBind(MyFeeds.Feed mediaObject, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        title.setText(!TextUtils.isEmpty(mediaObject.description) ? mediaObject.description : "No des");

        MyFeeds.Feed.Image image = mediaObject.images.get(0);

        if (image.media.endsWith(".mp4")) {

//            Glide.with(thumbnail.getContext())
//                    .load(image.getMedia())
//                    .into(thumbnail);

            this.requestManager.asBitmap()
                    .load(image.getMedia())
                    .into(thumbnail);

        } else {
            this.requestManager
                    .load(image.getMedia())
                    .into(thumbnail);
        }
    }

}