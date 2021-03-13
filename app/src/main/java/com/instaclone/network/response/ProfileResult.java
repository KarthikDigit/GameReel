package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProfileResult implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("user_bio")
    @Expose
    private String userBio;
    @SerializedName("profile_img")
    @Expose
    private Object profileImg;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("devices")
    @Expose
    private List<String> devices = null;
    @SerializedName("social")
    @Expose
    private List<Social> social = null;
    @SerializedName("my_tn_title")
    @Expose
    private String myTnTitle;
    @SerializedName("my_tn_image")
    @Expose
    private String myTnImage;
    @SerializedName("my_tn_banner_image")
    @Expose
    private String myTnBannerImage;
    @SerializedName("my_tn_description")
    @Expose
    private String myTnDescription;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("player_state")
    @Expose
    private String playerState;
    @SerializedName("player_country")
    @Expose
    private String playerCountry;
    private final static long serialVersionUID = 3553880844213875486L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public Object getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Object profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public List<Social> getSocial() {
        return social;
    }

    public void setSocial(List<Social> social) {
        this.social = social;
    }

    public String getMyTnTitle() {
        return myTnTitle;
    }

    public void setMyTnTitle(String myTnTitle) {
        this.myTnTitle = myTnTitle;
    }

    public String getMyTnImage() {
        return myTnImage;
    }

    public void setMyTnImage(String myTnImage) {
        this.myTnImage = myTnImage;
    }

    public String getMyTnBannerImage() {
        return myTnBannerImage;
    }

    public void setMyTnBannerImage(String myTnBannerImage) {
        this.myTnBannerImage = myTnBannerImage;
    }

    public String getMyTnDescription() {
        return myTnDescription;
    }

    public void setMyTnDescription(String myTnDescription) {
        this.myTnDescription = myTnDescription;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPlayerState() {
        return playerState;
    }

    public void setPlayerState(String playerState) {
        this.playerState = playerState;
    }

    public String getPlayerCountry() {
        return playerCountry;
    }

    public void setPlayerCountry(String playerCountry) {
        this.playerCountry = playerCountry;
    }


    public static class Social implements Serializable {

        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("instagram")
        @Expose
        private String instagram;
        @SerializedName("steam")
        @Expose
        private String steam;
        @SerializedName("twitch")
        @Expose
        private String twitch;
        private final static long serialVersionUID = -5552594103531251015L;

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getSteam() {
            return steam;
        }

        public void setSteam(String steam) {
            this.steam = steam;
        }

        public String getTwitch() {
            return twitch;
        }

        public void setTwitch(String twitch) {
            this.twitch = twitch;
        }

    }

}