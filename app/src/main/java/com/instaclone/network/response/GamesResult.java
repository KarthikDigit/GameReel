package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GamesResult implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("game_logo")
    @Expose
    private String gameLogo;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("devices_supported")
    @Expose
    private List<String> devicesSupported = null;
    private final static long serialVersionUID = 7396734799392982722L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGameLogo() {
        return gameLogo;
    }

    public void setGameLogo(String gameLogo) {
        this.gameLogo = gameLogo;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDevicesSupported() {
        return devicesSupported;
    }

    public void setDevicesSupported(List<String> devicesSupported) {
        this.devicesSupported = devicesSupported;
    }

}