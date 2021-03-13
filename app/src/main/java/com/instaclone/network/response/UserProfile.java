package com.instaclone.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserProfile implements Serializable {

    @SerializedName("user")
    @Expose
    private User user;
    private final static long serialVersionUID = 7101866192927123080L;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User implements Serializable {

        @SerializedName("isFollowed")
        @Expose
        private Integer isFollowed = 0;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile_no")
        @Expose
        private String mobileNo;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("photo")
        @Expose
        private String photo;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("role_id")
        @Expose
        private String roleId;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("push_token_id")
        @Expose
        private String pushTokenId;
        @SerializedName("device_model")
        @Expose
        private String deviceModel;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("address_1")
        @Expose
        private String address1;
        @SerializedName("address_2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state_id")
        @Expose
        private String stateId;
        @SerializedName("zip_code")
        @Expose
        private String zipCode;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("social_login")
        @Expose
        private Integer socialLogin;
        @SerializedName("social_type")
        @Expose
        private String socialType;
        @SerializedName("os_type")
        @Expose
        private Integer osType;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("following")
        @Expose
        private Integer following;
        @SerializedName("follower")
        @Expose
        private Integer follower;
        @SerializedName("post_count")
        @Expose
        private Integer post_count;
        private final static long serialVersionUID = -4453783928475312940L;

        public Integer getIsFollowed() {
            return isFollowed;
        }

        public void setIsFollowed(Integer isFollowed) {
            this.isFollowed = isFollowed;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getPushTokenId() {
            return pushTokenId;
        }

        public void setPushTokenId(String pushTokenId) {
            this.pushTokenId = pushTokenId;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public Integer getSocialLogin() {
            return socialLogin;
        }

        public void setSocialLogin(Integer socialLogin) {
            this.socialLogin = socialLogin;
        }

        public String getSocialType() {
            return socialType;
        }

        public void setSocialType(String socialType) {
            this.socialType = socialType;
        }

        public Integer getOsType() {
            return osType;
        }

        public void setOsType(Integer osType) {
            this.osType = osType;
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

        public Integer getFollowing() {
            return following;
        }

        public void setFollowing(Integer following) {
            this.following = following;
        }

        public Integer getFollower() {
            return follower;
        }

        public void setFollower(Integer follower) {
            this.follower = follower;
        }

        public Integer getPost_count() {
            return post_count;
        }

        public void setPost_count(Integer post_count) {
            this.post_count = post_count;
        }
    }

}