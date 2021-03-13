package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyPhotos implements Serializable {

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    private final static long serialVersionUID = 8883433225823340185L;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }


    public static class Video implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("media")
        @Expose
        private String media;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("post_id")
        @Expose
        private Integer postId;
        @SerializedName("post_type")
        @Expose
        private Integer postType;
        @SerializedName("video")
        @Expose
        private String video;
        private final static long serialVersionUID = 937990322565188685L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getPostId() {
            return postId;
        }

        public void setPostId(Integer postId) {
            this.postId = postId;
        }

        public Integer getPostType() {
            return postType;
        }

        public void setPostType(Integer postType) {
            this.postType = postType;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

    }


    public static class Image implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("media")
        @Expose
        private String media;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("post_id")
        @Expose
        private Integer postId;
        @SerializedName("post_type")
        @Expose
        private Integer postType;
        private final static long serialVersionUID = -5236557287122666265L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getPostId() {
            return postId;
        }

        public void setPostId(Integer postId) {
            this.postId = postId;
        }

        public Integer getPostType() {
            return postType;
        }

        public void setPostType(Integer postType) {
            this.postType = postType;
        }

    }

}