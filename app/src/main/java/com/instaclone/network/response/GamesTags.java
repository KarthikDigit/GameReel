package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GamesTags implements Serializable {

    @SerializedName("gameDeviceName")
    @Expose
    private List<String> gameDeviceName = null;
    @SerializedName("gameDeviceValue")
    @Expose
    private List<String> gameDeviceValue = null;
    private final static long serialVersionUID = 1123703051251363953L;

    public List<String> getGameDeviceName() {
        return gameDeviceName;
    }

    public void setGameDeviceName(List<String> gameDeviceName) {
        this.gameDeviceName = gameDeviceName;
    }

    public List<String> getGameDeviceValue() {
        return gameDeviceValue;
    }

    public void setGameDeviceValue(List<String> gameDeviceValue) {
        this.gameDeviceValue = gameDeviceValue;
    }

}