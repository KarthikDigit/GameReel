package com.instaclone.network;


import com.instaclone.network.service.NetworkApiService;
import com.instaclone.network.service.UserService;
import com.instaclone.preference.Preferences;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitAdapter {

    private static final String API_BASE_URL = "https://api.gamereel.io/api/";
    public static final String API_IMAGE_URL = "https://api.gamereel.io/storage/app/game/image/";
    private String headerData = null;

    private static Retrofit retrofit;

    private static Retrofit retrofitUpload;

    private static OkHttpClient okHttpClient;

    private static OkHttpClient okHttpUploadClient;

    public static final int UPLOAD_TIMEOUT_IN_MINUTES = 2;


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getNormalClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static NetworkApiService getNetworkApiServiceClient() {
        Retrofit apiClient = RetrofitAdapter.getRetrofit();
        return apiClient.create(NetworkApiService.class);
    }


    public static NetworkApiService getNetworkApiServiceClient1() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(new AuthorizationInterceptor())
//                .addInterceptor(logging).build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(getNormalClient())
//                .build();


        return getRetrofitUpload().create(NetworkApiService.class);
    }


    private static class AuthorizationInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response mainResponse = chain.proceed(chain.request());
            Request mainRequest = chain.request();

            if (Preferences.INSTANCE.isUserLoggedIn()) {

                Request.Builder builder = mainRequest.newBuilder()
                        .addHeader("Authorization", Preferences.INSTANCE.getTokenType() + " " + Preferences.INSTANCE.getAccessToken())
                        .addHeader("Accept", "application/json")
                        .method(mainRequest.method(), mainRequest.body());
                mainResponse = chain.proceed(builder.build());

            }

            return mainResponse;
        }
    }


    public static UserService getUserApiServiceClient() {
        Retrofit apiClient = RetrofitAdapter.getRetrofit();
        return apiClient.create(UserService.class);
    }

    public static Retrofit getRetrofitUpload() {
        if (retrofitUpload == null) {
            retrofitUpload = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getUploadClient())
                    .build();
        }
        return retrofitUpload;
    }


    private static OkHttpClient getNormalClient() {
        if (okHttpClient == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder builder = original.newBuilder()
//                                    /*.header("Content-Type", "application/json")*/
                                    .method(original.method(), original.body());

                            /*if (Preferences.INSTANCE.getAccessToken() != null) {

                            }*/


                            builder.header("Content-Type", "application/json");
                            if (Preferences.INSTANCE.isUserLoggedIn()) {
                                builder.header("Authorization", Preferences.INSTANCE.getApiAccessToken());
                            }

                            Request request = builder.build();

                            return chain.proceed(request);
                        }
                    })

//            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(30, TimeUnit.SECONDS)
//                    .readTimeout(30, TimeUnit.SECONDS)
                    // TODO: Since we are using simple GET Requests, this is not required now.
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request original = chain.request();
//
//                            Request.Builder builder = original.newBuilder()
//                                    /*.header("Content-Type", "application/json")*/
//                                    .header("Content-Type", "application/json")
//                                    //.header("Content-Type", "application/x-www-form-urlencoded")
//                                    .method(original.method(), original.body());
//
//                            builder.header("Authorization", "Basic cm9vdDpwYXNzd29yZA==");
//
//                            Request request = builder.build();
//
//                            return chain.proceed(request);
//                        }
//                    })
                    .addInterceptor(logging);
            //okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private static OkHttpClient getUploadClient() {
        if (okHttpUploadClient == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .connectTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder builder = original.newBuilder()
                                    /*.header("Content-Type", "application/json")*/
                                    .method(original.method(), original.body());

                            /*if (Preferences.INSTANCE.getAccessToken() != null) {

                            }*/

                            if (Preferences.INSTANCE.isUserLoggedIn()) {
                                builder.header("Authorization", Preferences.INSTANCE.getApiAccessToken());
                            }
//                            builder.header("Authorization", "Basic cm9vdDpwYXNzd29yZA==");

                            Request request = builder.build();

                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging)
                    .readTimeout(UPLOAD_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
            okHttpUploadClient = builder.build();
        }
        return okHttpUploadClient;
    }

    public void clearRetrofitAdapters() {
        retrofit = null;
        retrofitUpload = null;
        okHttpClient = null;
        okHttpUploadClient = null;
    }

}
