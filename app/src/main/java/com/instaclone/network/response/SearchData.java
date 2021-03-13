package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchData implements Serializable {

    @SerializedName("feeds")
    @Expose
    private List<Feed> feeds = null;
    private final static long serialVersionUID = 6151455202709507843L;

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public static class Feed implements Serializable {

        @SerializedName("meta_id")
        @Expose
        private Integer metaId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("post_id")
        @Expose
        private Integer postId;
        @SerializedName("media")
        @Expose
        private String media;
        @SerializedName("video")
        @Expose
        private Object video;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        private final static long serialVersionUID = 6855740166216298686L;

        public Integer getMetaId() {
            return metaId;
        }

        public void setMetaId(Integer metaId) {
            this.metaId = metaId;
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

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public Object getVideo() {
            return video;
        }

        public void setVideo(Object video) {
            this.video = video;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

    }

}