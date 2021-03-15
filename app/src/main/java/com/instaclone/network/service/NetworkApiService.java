package com.instaclone.network.service;


import com.instaclone.network.request.ChangeFollow;
import com.instaclone.network.request.ChangePassRequest;
import com.instaclone.network.request.DeviceListRequest;
import com.instaclone.network.request.Deviceinfo;
import com.instaclone.network.request.FilteredRequest;
import com.instaclone.network.request.LoginRequest;
import com.instaclone.network.request.MyProfileRequest;
import com.instaclone.network.request.ProfileUpdateRequest;
import com.instaclone.network.request.RegisterRequest;
import com.instaclone.network.request.UpdateProfile;
import com.instaclone.network.request.UploadRequest;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.Following;
import com.instaclone.network.response.GamesResult;
import com.instaclone.network.response.GamesTags;
import com.instaclone.network.response.LoginResponse;
import com.instaclone.network.response.MyCompetition;
import com.instaclone.network.response.MyFeeds;
import com.instaclone.network.response.MyPhotos;
import com.instaclone.network.response.NotificationResult;
import com.instaclone.network.response.ProfileResult;
import com.instaclone.network.response.SearchData;
import com.instaclone.network.response.StatesResponse;
import com.instaclone.network.response.TournamentResult;
import com.instaclone.network.response.UserFeed;
import com.instaclone.network.response.UserProfile;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkApiService {


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("auth/login")
    Observable<BaseResponse<LoginResponse>> login(@Body LoginRequest json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("auth/signup")
    Observable<String> register(@Body RegisterRequest json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("post-feed")
    Observable<String> upload(@HeaderMap Map<String, String> headermap, @Body UploadRequest json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("post-feed")
    Call<String> uploadRetrofit(@HeaderMap Map<String, String> headermap, @Body UploadRequest json);


    @Multipart
    @POST("https://api.gamereel.io/api/post")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part("is_image") RequestBody is_image,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("https://api.gamereel.io/api/post")
    Call<ResponseBody> uploadMultipleFile(
            @Part("description") RequestBody description,
            @Part("is_image") RequestBody is_image,
            @Part List<MultipartBody.Part> files
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("searchfeeds")
    Observable<BaseResponse<SearchData>> getSearchFeed(@HeaderMap Map<String, String> headermap);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("get-state")
    Observable<BaseResponse<List<StatesResponse>>> getAllState();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("userfeeds")
    Observable<BaseResponse<UserFeed>> getUserFeeds(@HeaderMap Map<String, String> headermap, @Query("id") String id);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("feeds")
    Observable<BaseResponse<MyFeeds>> getAllMyFeeds(@HeaderMap Map<String, String> headermap);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("user")
    Observable<BaseResponse<UserProfile>> getProfile(@HeaderMap Map<String, String> headermap);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("follow-list")
    Observable<BaseResponse<List<Following>>> getFollowing(@HeaderMap Map<String, String> headermap, @Query("type") int type);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("follow-list")
    Observable<BaseResponse<List<Following>>> getFollower(@HeaderMap Map<String, String> headermap, @Query("type") int type);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("user")
    Observable<BaseResponse<UserProfile>> getProfileById(@HeaderMap Map<String, String> headermap, @Query("id") String id);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("follow")
    Observable<String> changeFollow(@HeaderMap Map<String, String> headermap, @Body ChangeFollow json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("myposts")
    Observable<BaseResponse<MyPhotos>> getMyPhotos(@HeaderMap Map<String, String> headermap);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("myposts")
    Observable<BaseResponse<MyPhotos>> getMyPhotos(@HeaderMap Map<String, String> headermap, @Query("id") String id);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("profile")
    Observable<String> updateProfile(@HeaderMap Map<String, String> headermap, @Body UpdateProfile json);


//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("register")
//    Observable<BaseResponse<Object>> register(@Body RegisterRequest json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("get-browsetn-list")
    Observable<BaseResponse<List<TournamentResult>>> getFilteredTournment(@HeaderMap Map<String, String> headermap, @Body FilteredRequest filteredRequest);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("after-login-tnlist")
    Observable<BaseResponse<List<TournamentResult>>> getTournmentAfterLogin(@HeaderMap Map<String, String> headermap);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("before-login-tnlist")
    Observable<BaseResponse<List<TournamentResult>>> getTournmentBeforeLogin();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("change-password")
    Observable<String> changePassword(@HeaderMap Map<String, String> headermap, @Body ChangePassRequest changePassRequest);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("gamelist")
    Observable<BaseResponse<List<GamesResult>>> getGameList(@HeaderMap Map<String, String> headermap);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("get-game-device-list")
    Observable<BaseResponse<List<TournamentResult>>> getGameDeviceList(@HeaderMap Map<String, String> headermap, @Body DeviceListRequest deviceListRequest);


    //    @Headers({"Accept: application/json;charset=UTF-8"})
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    @POST("get-user-profile")
    Observable<BaseResponse<List<ProfileResult>>> getProfileInfo(@HeaderMap Map<String, String> headermap, @Body MyProfileRequest myProfileRequest);

    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    @POST("get-gametag")
    Observable<BaseResponse<GamesTags>> getGamesTags(@HeaderMap Map<String, String> headermap, @Body MyProfileRequest myProfileRequest);

    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    @POST("get-notification-list")
    Observable<BaseResponse<List<Map<String, List<NotificationResult.NotiData>>>>> getNotification(@HeaderMap Map<String, String> headermap, @Body MyProfileRequest myProfileRequest);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mytournament-list")
    Observable<BaseResponse<List<TournamentResult>>> getMyCompetitions(@HeaderMap Map<String, String> headermap, @Body MyCompetition myCompetition);


    //    @GET("announcements/{id}")
//    Observable<AnouncementDetails> getAnnouncementDetails(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/type/{type}")
//    Observable<Customers> fetchAllCustomers(@HeaderMap Map<String, String> headermap, @Path("type") String type, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customerslist")
//    Observable<CustomersApiResponse> fetchAllCustomers(@HeaderMap Map<String, String> headermap, @Query("type") Integer type, @Query("page") Integer page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/create")
//    Observable<AddCustomerApiResponse> fetchPreCustomerDetails(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/comtypes/regions")
//    Observable<AddCustomerApiResponse> fetchCusTypeAndRegion(@HeaderMap Map<String, String> headermap, @Query("customer_group") Integer type);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/marketsubtypes")
//    Observable<AddCustomerApiResponse> fetchMarketSubType(@HeaderMap Map<String, String> headermap, @Query("market_type") Integer type);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/usagetypes")
//    Observable<AddCustomerApiResponse> fetchUsageType(@HeaderMap Map<String, String> headermap, @Query("market_subtype") Integer type);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/usagesubtypes")
//    Observable<AddCustomerApiResponse> fetchUsageSubType(@HeaderMap Map<String, String> headermap, @Query("market_usagetype") Integer type);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/states")
//    Observable<AddCustomerApiResponse> fetchStatsByCountryId(@HeaderMap Map<String, String> headermap, @Query("id") Integer type);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("customers/store")
//    Observable<CustomerResponse> addNewCustomer(@HeaderMap Map<String, String> headermap, @Body CustomerPostData customerPostData);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("password-change")
//    Observable<String> changePassword(@HeaderMap Map<String, String> headermap, @Body ChangePassword changePassword);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("customers/update")
//    Observable<String> updateCustomer(@HeaderMap Map<String, String> headermap, @Body CustomerUpdatePostData customerPostData);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("tasks/create")
//    Observable<CustomersListResponse> fetchAllCustomers(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("task-contacts/{id}")
//    Observable<ContactPersonApiResponse> fetchContactPersonByCustomers(@HeaderMap Map<String, String> headermap, @Path("id") Integer id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("tasks/{id}")
//    Observable<TaskAddedResult> editTask(@HeaderMap Map<String, String> headermap, @Body TaskUpdatePostData taskPostData, @Path("id") Integer id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("tasks")
//    Observable<TaskAddedResult> addTask(@HeaderMap Map<String, String> headermap, @Body TaskPostData taskPostData);
//
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("deviceinfo")
    Observable<String> postDeviceInfo(@HeaderMap Map<String, String> headermap, @Body Deviceinfo json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("logout")
    Observable<String> logout(@HeaderMap Map<String, String> headermap);

    @FormUrlEncoded
    @POST("avatar")
    Observable<String> updateProfileImage(@HeaderMap Map<String, String> headermap, @Field("photo") String avatar, @Field("type") String type);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("avatar")
    Observable<String> updateProfileImageJson(@HeaderMap Map<String, String> headermap, @Body ProfileUpdateRequest updateRequest);


//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/type/{type}")
//    Single<Customers> fetchAllSingleCustomers(@HeaderMap Map<String, String> headermap, @Path("type") String type, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/type/{type}")
//    Single<Customers> fetchAllCustomersTest(@HeaderMap Map<String, String> headermap, @Path("type") String type, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/all/customer")
//    Observable<CustomersMap> fetchAllCustomersForMap(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/all/customer")
//    Observable<TaskCustomers> fetchAllCustomersForMap1(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/showtype/{type}")
//    Observable<Customers> fetchAllMyCustomers(@HeaderMap Map<String, String> headermap, @Path("type") String type, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customers/details/{id}")
//    Observable<AddCustomerApiResponse> fetchCustomerById(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("customer")
//    Observable<ResponseCustomerAdded> postCustomer(@HeaderMap Map<String, String> headermap, @Body RequestCustomer requestCustomer);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @PUT("customer/{id}")
//    Observable<ResponseCustomerUpdated> updateCustomer(@HeaderMap Map<String, String> headermap, @Path("id") String id, @Body RequestCustomer requestCustomer);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/{id}")
//    Observable<CustomersComments> fetchAllCustomerComments(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("comment")
//    Observable<String> postCustomerComments(@HeaderMap Map<String, String> headermap, @Body PostCustomerComments postCustomerComments);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("meeting")
//    Observable<String> postTask(@HeaderMap Map<String, String> headermap, @Body PostTask postTask);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("tasks")
//    Observable<RsponseTask> fetchAllTasks(@HeaderMap Map<String, String> headermap, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("tasks")
//    Single<RsponseTask> fetchAllSingleTasks(@HeaderMap Map<String, String> headermap, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("meeting/create")
//    Observable<TaskCustomers> fetchAllMyCustomers(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("tasks/{id}")
//    Observable<MeetingDetails> fetchMeetingDetails(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("meeting/{id}")
//    Observable<TaskComments> fetchAllTasksComments(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("project/status/{status}")
//    Observable<ProjectApiResponse> fetchAllProjects(@HeaderMap Map<String, String> headermap, @Path("status") String status, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("project/{id}/edit")
//    Observable<ProjectDetails> fetchProjectDetails(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("project")
//    Observable<String> postProject(@HeaderMap Map<String, String> headermap, @Body RequestProject requestProject);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @PUT("project/{id}")
//    Observable<String> updateProject(@HeaderMap Map<String, String> headermap, @Body RequestProject requestProject, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("project/{id}")
//    Observable<ProjectComments> fetchAllProjectComments(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("download/get_ctgy")
//    Observable<DownloadCateloge> fetchAllCateloge(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("download/get_subcat/{id}")
//    Observable<DownloadSubCategory> fetchAllCatelogeSubcategory(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("download/get_file/{cid}/{scid}")
//    Observable<DownloadFiles> fetchAllDownloadfiles(@HeaderMap Map<String, String> headermap, @Path("cid") String cid, @Path("scid") String scid);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("attendance/swipein")
//    Observable<SwipeResponse> swipeIn(@HeaderMap Map<String, String> headermap, @Body PunchIn punchIn);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("attendance/swipeout")
//    Observable<SwipeResponse> swipeOut(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("attendance/view")
//    Observable<SwipeInOut> fetchSwipeInOut(@HeaderMap Map<String, String> headermap, @Query("page") String page);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/get/notify")
//    Observable<NotificationApiResponse> fetchAllNotifications(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("customer/read/notify")
//    Observable<String> setReadNotification(@HeaderMap Map<String, String> headermap, @Body ReadNotification readNotification);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("customer/read/salesrep")
//    Observable<SalesResApiResponse> fetchAllSalesRep(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("customer/gpstrack")
//    Observable<String> postUserLocation(@HeaderMap Map<String, String> headermap, @Body RequestLocation requestLocation);

//    @GET("events")
//    Observable<EventListResponseApi> fetchAllEvents(@HeaderMap Map<String, String> headermap);
//
//    @GET("events/{id}")
//    Observable<EventDetailsApi> fetchEventDetailsById(@HeaderMap Map<String, String> headermap, @Path("id") int id);
//
//    @GET("events/{id}")
//    Observable<String> fetchEventStringById(@HeaderMap Map<String, String> headermap, @Path("id") int id);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("event-attendence")
//    Observable<String> postKidsEvent(@HeaderMap Map<String, String> headermap, @Body KidsRequest json);
//
//
//    //
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("deviceinfo")
//    Observable<String> postPustId(@HeaderMap Map<String, String> headermap, @Body Deviceinfo json);

//    //    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @GET("notifications")
//    Observable<NotificationApi> getAllNotification(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("notifications/{id}")
//    Observable<String> deleteNotificationById(@HeaderMap Map<String, String> headermap, @Path("id") int id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("notifications/clearall")
//    Observable<String> deleteAllNotification(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("notifications/read/{id}")
//    Observable<ReadNotification> readNotification(@HeaderMap Map<String, String> headermap, @Path("id") String id);
//
//
//    @GET("user")
//    Observable<ProfileDetails> fetchUserDetails(@HeaderMap Map<String, String> headermap);
//
//    @GET("contactlist")
//    Observable<ContactListApi> fetchContactList(@HeaderMap Map<String, String> headermap);
//
//    @GET("contactlist")
//    Observable<String> fetchContactList1(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Accept: application/json;charset=UTF-8"})
//    @GET("announcements")
//    Observable<AnnouncementApi> fetchAnnouncements(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("announcements/{id}")
//    Observable<String> getAnnouncementById(@HeaderMap Map<String, String> headermap, @Path("id") int id);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8",
//            "Authorization: key=AAAA5zQ5y4I:APA91bFEXLS3t0uo6AjGFmuof-ZpFosw5HXGe9E0cBCOVDBrzjriffudkh5lb6AgX42cImB378nB-8lxKSdHjZR0yWsZRx7wzAMoT93agNqdv_z9M1kV9pIp8nPBctF7yKTzYi0mNOzZ"})
//    @POST("https://fcm.googleapis.com/fcm/send")
//    Observable<String> sendPustNotification(@Body String bodyMap);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("verify-otp")
//    Observable<OTPValidateApi> validateOtp(@Body String json);
//
//
//    @GET("kids-coaches")
//    Observable<KidsAndCoaches> fetchKids(@HeaderMap Map<String, String> headermap);
//
//
//    @GET("kids-coaches")
//    Observable<KidsAndCoaches> fetchKidsN();
//
//
//    @GET("kid/{id}")
//    Observable<KidInfoResponse> fetchKidDetailsById(@HeaderMap Map<String, String> headermap, @Path("id") int id);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("kid/{id}")
//    Observable<KidDetailsUpdatedResponse> updateKidDetails(@HeaderMap Map<String, String> headermap, @Body UpdateKidDetail json, @Path("id") int id);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("kid/avatar/{id}")
//    Observable<String> updateKidProfileImage(@HeaderMap Map<String, String> headermap, @Body UploadKidProfile json, @Path("id") int id);
//
//
//    //    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @FormUrlEncoded
//    @POST("kid/avatar/{id}")
//    Observable<String> updateKidProfileImage1(@HeaderMap Map<String, String> headermap, @Field("avatar") String avatar, @Path("id") int id);
//
//
//    @GET("teams")
//    Observable<TeamResponse> fetchAllTeams(@HeaderMap Map<String, String> headermap);
//
//    @GET("event-types")
//    Observable<EventTypesResponse> fetchAllEventTypes(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("events")
//    Observable<String> postEvents(@HeaderMap Map<String, String> headermap, @Body BasePostEvents json);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("events")
//    Observable<String> postEvents(@HeaderMap Map<String, String> headermap, @Body Map<String, Object> json);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("announcements")
//    Observable<String> postAnnouncements(@HeaderMap Map<String, String> headermap, @Body Map<String, Object> json);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("contact")
//    Observable<ContactUsApi> postFeedback(@HeaderMap Map<String, String> headermap, @Body Feedback feedback);
//
//    @GET("receipts")
//    Observable<MyReceipt> fetchAllReceipt(@HeaderMap Map<String, String> headermap);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("subaccount/create")
//    Observable<String> postAddSubAccount(@HeaderMap Map<String, String> headermap, @Body AddSubAccount addSubAccount);
//
//    @GET("paid-events")
//    Observable<PaidEventResponse> fetchAllPaidEvent(@HeaderMap Map<String, String> headermap);
//
//    @GET("paid-event/{id}")
//    Observable<PaidEventDetailsResponse> fetchPaidEventDetails(@HeaderMap Map<String, String> headermap, @Path("id") int id);


//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("categories")
//    Observable<CategoryAddedApi> addCategory(@HeaderMap Map<String, String> headermap, @Body CategoryRequest json);
//
//    @Multipart
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("products")
//    Observable<String> addProducts(@HeaderMap Map<String, String> headermap, @Part MultipartBody.Part file, @Part("json") RequestBody json);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("products")
//    Observable<AddedProductApi> addProducts(@HeaderMap Map<String, String> headermap, @Body AddProductRequest json);
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("products/{id}")
//    Observable<UpdateProductApi> updateProducts(@HeaderMap Map<String, String> headermap, @Path("id") int id, @Body AddProductRequest json);
//
//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("products/{id}")
//    Observable<DeleteApi> deleteProduct(@HeaderMap Map<String, String> headermap, @Path("id") int id, @Body DeleteRequest request);

//
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("contacts")
//    Observable<CommentsApi> postComments(@HeaderMap Map<String, String> headermap, @Body PostCommentsRequest json);
//
//
//    //
////    @Multipart
////    @Headers({"Content-Type: application/json;charset=UTF-8"})
////    @POST("products")
////    Observable<String> addProducts(@HeaderMap Map<String, String> headermap, @Part("product_image") RequestBody product_image, @Body AddProductRequest json);


//
//    @GET("public/api/poojas/{id}")
//    Call<PoojaDetailApi> fetchPoojaDetails(@Path("id") String id);
//
//    @GET("public/api/tours")
//    Call<PilgrimageToursListApi> fetchToursList();
//
//    @GET("public/api/tours/{id}")
//    Call<PilgrimageTourDetailsApi> fetchTourDetails(@Path("id") String id);
//
//    @GET("public/api/audios")
//    Call<AudioVideoMessageListApi> fetchAudioMessages();
//
//    @GET("public/api/videos")
//    Call<AudioVideoMessageListApi> fetchVideoMessages();
//
//    @GET("public/api/audios/{id}")
//    Observable<AudioVideoMessageDetailApi> fetchAudioMessageDetails(@Path("id") String id);
//
//    @GET("public/api/videos/{id}")
//    Call<AudioVideoMessageDetailApi> fetchVideoMessageDetails(@Path("id") String id);
//
//    @GET("public/api/slokas")
//    Observable<SlokasMantrasListApi> fetchSlokasList();
//
//    @GET("public/api/slokas/{id}")
//    Call<SlokasMantrasDetailApi> fetchSlokasWithId(@Path("id") String id);
//
//    @GET("public/api/mantras")
//    Observable<SlokasMantrasListApi> fetchMantrasList();
//
//    @GET("public/api/mantras/{id}")
//    Call<SlokasMantrasDetailApi> fetchMantrasWithId(@Path("id") String id);
//
//    @GET("public/api/slokadays")
//    Observable<SlokaOfTheDayApi> fetchSlokaOfTheDayList();
//
//    @GET("public/api/events")
//    Call<CurrentEventsListApi> fetchCurrentEventsList();
//
//    @GET("public/api/events/{id}")
//    Call<CurrentEventsDetailsApi> fetchCurrentEventDetails(@Path("id") String id);
//
//    @GET("public/api/eappnotices")
//    Call<EappEventsListApi> fetchEappEventsList();
//
//    @GET("public/api/eappnotices/{id}")
//    Call<EappEventsDetailsApi> fetchEappEventDetails(@Path("id") String id);
//
//    @GET("public/api/testimonials")
//    Observable<TestimonialListApi> fetchTestimonialInfo();
//
//    @POST("public/api/testimonials")
//    Observable<TestimonialRequest> postTestimonialInfo(@Body TestimonialRequest testimonialRequest);

}
