package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TournamentResult implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("devices_support")
    @Expose
    private String devicesSupport;
    @SerializedName("game_id")
    @Expose
    private Integer gameId;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("gametype")
    @Expose
    private Integer gametype;
    @SerializedName("devices_supported")
    @Expose
    private Integer devicesSupported;
    @SerializedName("gimage")
    @Expose
    private String gimage;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("card_image")
    @Expose
    private String cardImage;
    @SerializedName("game_logo")
    @Expose
    private String gameLogo;
    @SerializedName("is_prize")
    @Expose
    private Integer isPrize;
    @SerializedName("no_of_prize")
    @Expose
    private Integer noOfPrize;
    @SerializedName("prize_amount")
    @Expose
    private String prizeAmount;
    @SerializedName("start_date_time")
    @Expose
    private String startDateTime;
    @SerializedName("is_approved")
    @Expose
    private Integer isApproved;
    @SerializedName("next_level_time")
    @Expose
    private Object nextLevelTime;
    @SerializedName("no_of_players")
    @Expose
    private Integer noOfPlayers;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("requirement")
    @Expose
    private String requirement;
    @SerializedName("rule")
    @Expose
    private String rule;
    @SerializedName("support")
    @Expose
    private String support;
    @SerializedName("game_type")
    @Expose
    private Integer gameType;
    @SerializedName("private_player_email")
    @Expose
    private String privatePlayerEmail;
    @SerializedName("created_type")
    @Expose
    private Integer createdType;
    @SerializedName("cancel_reason")
    @Expose
    private String cancelReason;
    @SerializedName("dname")
    @Expose
    private Object dname;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("uname")
    @Expose
    private Object uname;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("more_detail_url")
    @Expose
    private String moreDetailUrl;
    @SerializedName("utimezone")
    @Expose
    private Object utimezone;
    private final static long serialVersionUID = 3603217840858573640L;

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

    public String getDevicesSupport() {
        return devicesSupport;
    }

    public void setDevicesSupport(String devicesSupport) {
        this.devicesSupport = devicesSupport;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getGametype() {
        return gametype;
    }

    public void setGametype(Integer gametype) {
        this.gametype = gametype;
    }

    public Integer getDevicesSupported() {
        return devicesSupported;
    }

    public void setDevicesSupported(Integer devicesSupported) {
        this.devicesSupported = devicesSupported;
    }

    public String getGimage() {
        return gimage;
    }

    public void setGimage(String gimage) {
        this.gimage = gimage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getGameLogo() {
        return gameLogo;
    }

    public void setGameLogo(String gameLogo) {
        this.gameLogo = gameLogo;
    }

    public Integer getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(Integer isPrize) {
        this.isPrize = isPrize;
    }

    public Integer getNoOfPrize() {
        return noOfPrize;
    }

    public void setNoOfPrize(Integer noOfPrize) {
        this.noOfPrize = noOfPrize;
    }

    public String getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(String prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Integer getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Integer isApproved) {
        this.isApproved = isApproved;
    }

    public Object getNextLevelTime() {
        return nextLevelTime;
    }

    public void setNextLevelTime(Object nextLevelTime) {
        this.nextLevelTime = nextLevelTime;
    }

    public Integer getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(Integer noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public Integer getGameType() {
        return gameType;
    }

    public void setGameType(Integer gameType) {
        this.gameType = gameType;
    }

    public String getPrivatePlayerEmail() {
        return privatePlayerEmail;
    }

    public void setPrivatePlayerEmail(String privatePlayerEmail) {
        this.privatePlayerEmail = privatePlayerEmail;
    }

    public Integer getCreatedType() {
        return createdType;
    }

    public void setCreatedType(Integer createdType) {
        this.createdType = createdType;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Object getDname() {
        return dname;
    }

    public void setDname(Object dname) {
        this.dname = dname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUname() {
        return uname;
    }

    public void setUname(Object uname) {
        this.uname = uname;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMoreDetailUrl() {
        return moreDetailUrl;
    }

    public void setMoreDetailUrl(String moreDetailUrl) {
        this.moreDetailUrl = moreDetailUrl;
    }

    public Object getUtimezone() {
        return utimezone;
    }

    public void setUtimezone(Object utimezone) {
        this.utimezone = utimezone;
    }

}