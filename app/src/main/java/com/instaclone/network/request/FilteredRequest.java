package com.instaclone.network.request;

import java.util.List;

public class FilteredRequest {

    private List<Integer> gameId;
    private List<String> deviceId;

    public List<Integer> getGameId() {
        return gameId;
    }

    public void setGameId(List<Integer> gameId) {
        this.gameId = gameId;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<String> deviceId) {
        this.deviceId = deviceId;
    }

}
