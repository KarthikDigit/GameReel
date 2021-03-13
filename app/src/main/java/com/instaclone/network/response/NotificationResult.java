package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class NotificationResult {

    private Map<String, List<NotiData>> dataMap;

    public Map<String, List<NotiData>> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, List<NotiData>> dataMap) {
        this.dataMap = dataMap;
    }

    public static class NotiData implements Serializable {

        @SerializedName("snf_id")
        @Expose
        private Integer snfId;
        @SerializedName("snf_message_details")
        @Expose
        private SnfMessageDetails snfMessageDetails;
        @SerializedName("more_detail_url")
        @Expose
        private String moreDetailUrl;
        @SerializedName("snf_view_status")
        @Expose
        private Integer snfViewStatus;
        @SerializedName("snf_created_at")
        @Expose
        private String snfCreatedAt;
        private final static long serialVersionUID = -1899755319175329458L;

        public Integer getSnfId() {
            return snfId;
        }

        public void setSnfId(Integer snfId) {
            this.snfId = snfId;
        }

        public SnfMessageDetails getSnfMessageDetails() {
            return snfMessageDetails;
        }

        public void setSnfMessageDetails(SnfMessageDetails snfMessageDetails) {
            this.snfMessageDetails = snfMessageDetails;
        }

        public String getMoreDetailUrl() {
            return moreDetailUrl;
        }

        public void setMoreDetailUrl(String moreDetailUrl) {
            this.moreDetailUrl = moreDetailUrl;
        }

        public Integer getSnfViewStatus() {
            return snfViewStatus;
        }

        public void setSnfViewStatus(Integer snfViewStatus) {
            this.snfViewStatus = snfViewStatus;
        }

        public String getSnfCreatedAt() {
            return snfCreatedAt;
        }

        public void setSnfCreatedAt(String snfCreatedAt) {
            this.snfCreatedAt = snfCreatedAt;
        }

        public static class SnfMessageDetails implements Serializable {

            @SerializedName("nfn_tournament_id")
            @Expose
            private Integer nfnTournamentId;
            @SerializedName("nfn_tournament_title")
            @Expose
            private String nfnTournamentTitle;
            @SerializedName("nfn_event_title")
            @Expose
            private String nfnEventTitle;
            @SerializedName("nfn_event_content")
            @Expose
            private String nfnEventContent;
            private final static long serialVersionUID = 1590728567510695997L;

            public Integer getNfnTournamentId() {
                return nfnTournamentId;
            }

            public void setNfnTournamentId(Integer nfnTournamentId) {
                this.nfnTournamentId = nfnTournamentId;
            }

            public String getNfnTournamentTitle() {
                return nfnTournamentTitle;
            }

            public void setNfnTournamentTitle(String nfnTournamentTitle) {
                this.nfnTournamentTitle = nfnTournamentTitle;
            }

            public String getNfnEventTitle() {
                return nfnEventTitle;
            }

            public void setNfnEventTitle(String nfnEventTitle) {
                this.nfnEventTitle = nfnEventTitle;
            }

            public String getNfnEventContent() {
                return nfnEventContent;
            }

            public void setNfnEventContent(String nfnEventContent) {
                this.nfnEventContent = nfnEventContent;
            }

        }

    }

}
