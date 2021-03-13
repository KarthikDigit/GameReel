package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserFeed implements Serializable {

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

        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("post_id")
        @Expose
        private Integer postId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("photo")
        @Expose
        private String photo;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("meta")
        @Expose
        private List<Meta> meta = null;
        @SerializedName("comment_count")
        @Expose
        private Integer commentCount;
        @SerializedName("like_count")
        @Expose
        private Integer likeCount;
        private final static long serialVersionUID = -1908778620821721749L;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public List<Meta> getMeta() {
            return meta;
        }

        public void setMeta(List<Meta> meta) {
            this.meta = meta;
        }

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public static class Meta implements Serializable {

            @SerializedName("meta_id")
            @Expose
            private Integer metaId;
            @SerializedName("media")
            @Expose
            private String media;
            @SerializedName("video")
            @Expose
            private String video;
            private final static long serialVersionUID = 8321396746151494582L;

            public Integer getMetaId() {
                return metaId;
            }

            public void setMetaId(Integer metaId) {
                this.metaId = metaId;
            }

            public String getMedia() {
                return media;
            }

            public void setMedia(String media) {
                this.media = media;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

        }

    }

}