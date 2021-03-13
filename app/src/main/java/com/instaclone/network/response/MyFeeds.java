package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyFeeds implements Serializable {

    @SerializedName("feeds")
    @Expose
    public List<Feed> feeds = null;
    private final static long serialVersionUID = 2989362770242525902L;

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public static class Feed implements Serializable {

        @SerializedName("id")
        @Expose
        public Long id;
        @SerializedName("user_id")
        @Expose
        public Long userId;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("status")
        @Expose
        public Long status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("images")
        @Expose
        public List<Image> images = null;

        @SerializedName("video")
        @Expose
        public Image video = null;

        private final static long serialVersionUID = 2599058759358373240L;

        public Image getVideo() {
            return video;
        }

        public void setVideo(Image video) {
            this.video = video;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Long getStatus() {
            return status;
        }

        public void setStatus(Long status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public static class Image implements Serializable {

            @SerializedName("id")
            @Expose
            public Long id;
            @SerializedName("media")
            @Expose
            public String media;
            @SerializedName("video")
            @Expose
            public String video;
            @SerializedName("post_type")
            @Expose
            public Integer postType;
            private final static long serialVersionUID = -4155624948524438696L;

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getMedia() {
                return media;
            }

            public void setMedia(String media) {
                this.media = media;
            }

            public Integer getPostType() {
                return postType;
            }

            public void setPostType(Integer postType) {
                this.postType = postType;
            }
        }
    }

}