package com.instaclone.dashboard.home;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayer;

public class MediaObject {
    private String title;
    private String media_url;
    private String thumbnail;
    private String description;

    private String url;
    private Context context;
    Boolean playWhenReady = false;
    int currentWindow = 0;
    long playbackPosition = 0;
    ExoPlayer player;

    public String getUrl() {
        return media_url;
    }

    public void setUrl(String url) {
        this.media_url = url;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Boolean getPlayWhenReady() {
        return playWhenReady;
    }

    public void setPlayWhenReady(Boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }

    public int getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    public ExoPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ExoPlayer player) {
        this.player = player;
    }

    public MediaObject(String title, String media_url, String thumbnail, String description) {
        this.title = title;
        this.media_url = media_url;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public MediaObject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}