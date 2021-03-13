package com.instaclone.dashboard.home;

import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.instaclone.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<MediaObject> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PlayerView mExoPlayer;

        public ViewHolder(View itemView) {
            super(itemView);
            mExoPlayer = itemView.findViewById(R.id.video_view);
        }
    }

    public VideoAdapter(ArrayList<MediaObject> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        initPlayer(holder, position);

    }

    private void initPlayer(ViewHolder holder, int position) {
        mDataset.get(position).setContext(holder.itemView.getContext());
        mDataset.get(position).setPlayer(ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mDataset.get(position).getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl()));

        holder.mExoPlayer.setPlayer(mDataset.get(position).getPlayer());

        mDataset.get(position).getPlayer().setPlayWhenReady(mDataset.get(position).getPlayWhenReady());
        mDataset.get(position).getPlayer().seekTo(mDataset.get(position).getCurrentWindow(), mDataset.get(position).getPlaybackPosition());

        Uri uri = Uri.parse(mDataset.get(position).getUrl());
        MediaSource mediaSource = buildMediaSource(uri);
        mDataset.get(position).getPlayer().prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {

        int position = holder.getAdapterPosition();
        if (mDataset.get(position) != null) {
            mDataset.get(position).getPlayer().release();
        }

        super.onViewRecycled(holder);
    }

//    holder.yourplayername.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//        @Override
//        public void onViewAttachedToWindow(View v) {
//
//        }
//
//        @Override
//        public void onViewDetachedFromWindow(View v) {
//            holder.yourplayername.getPlayer().stop();
//
//        }
//    });

}