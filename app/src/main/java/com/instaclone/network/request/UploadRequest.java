package com.instaclone.network.request;

import java.util.List;

public class UploadRequest {

    private String description;

    private List<Object> attachments;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Object> getImageVideoList() {
        return attachments;
    }

    public void setImageVideoList(List<Object> attachments) {
        this.attachments = attachments;
    }

    public static class ImageOnly {

        private String image;
        private String type;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ImageVideo {


        private String type;

        private String cover_image;
        private String video;
        private String cover_type;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getCover_type() {
            return cover_type;
        }

        public void setCover_type(String cover_type) {
            this.cover_type = cover_type;
        }
    }

}
