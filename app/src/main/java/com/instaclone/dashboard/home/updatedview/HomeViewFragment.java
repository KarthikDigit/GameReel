package com.instaclone.dashboard.home.updatedview;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.instaclone.R;
import com.instaclone.base.BaseFragment;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.MyFeeds;
import com.instaclone.network.response.UserProfile;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.ImageLoader;
import com.instaclone.utils.JsonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.PlayerSelector;
import im.ene.toro.widget.Container;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeViewFragment extends BaseFragment {


    @BindView(R.id.feedListView)
    Container mFeedListView;
    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;
    private HomeViewFeedAdapter mHomeFeedAdapter;

    public HomeViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_view, container, false);

        ButterKnife.bind(this, view);

        mHomeFeedAdapter = new HomeViewFeedAdapter(getContext(), new ArrayList<>());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mFeedListView.setLayoutManager(manager);
        mFeedListView.setHasFixedSize(true);

        manager.setItemPrefetchEnabled(true);

        mFeedListView.setPlayerSelector(PlayerSelector.DEFAULT);
        mFeedListView.setCacheManager(mHomeFeedAdapter);
        mFeedListView.setAdapter(mHomeFeedAdapter);


//        getAllMyFeeds();


        String json = JsonUtils.getFeedJson();

//        List<MyFeeds> list = Arrays.asList(new GsonBuilder().create().fromJson(json, MyFeeds[].class));

        Tyepe myFeeds = new Gson().fromJson(json, Tyepe.class);

        List<MyFeeds.Feed> myFeedsList = myFeeds.data.getFeeds();

//        TypeToken<BaseResponse<MyFeeds>> typeToken = (TypeToken<BaseResponse<MyFeeds>>) new TypeToken<BaseResponse<MyFeeds>>() {
//        }.getType();

        mHomeFeedAdapter.update(myFeedsList);

        getProfile();

        return view;
    }

    class Tyepe implements Serializable {

        @SerializedName("status")
        @Expose
        private Boolean success;
        @SerializedName("data")
        @Expose
        private MyFeeds data = null;
        @SerializedName("message")
        @Expose
        private String message;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public MyFeeds getData() {
            return data;
        }

        public void setData(MyFeeds data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

    private void getAllMyFeeds() {

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().getAllMyFeeds(Preferences.INSTANCE.getAuthendicate())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<MyFeeds>>() {
                    @Override
                    public void onNext(BaseResponse<MyFeeds> response) {

                        handleMyFeedsResponse(response);

                    }

                    @Override
                    public void onError(Throwable e) {

                        handleErrorMsg(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    private void handleMyFeedsResponse(BaseResponse<MyFeeds> response) {

        hideLoading();

        if (response.getSuccess() && response.getData() != null) {

            List<MyFeeds.Feed> myFeeds = response.getData().feeds;

            if (myFeeds != null && !myFeeds.isEmpty()) {

                List<MyFeeds.Feed> feeds = new ArrayList<>();

                for (int i = 0; i < myFeeds.size(); i++) {

                    List<MyFeeds.Feed.Image> im = myFeeds.get(i).images;
                    MyFeeds.Feed.Image vm = myFeeds.get(i).video;

                    if (im != null && im.size() > 0) feeds.add(myFeeds.get(i));
                    if (vm != null) feeds.add(myFeeds.get(i));

                }

                mHomeFeedAdapter.update(feeds);

            }


        }


    }


    private void getProfile() {


        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().getProfile(Preferences.INSTANCE.getAuthendicate())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<UserProfile>>() {
                    @Override
                    public void onNext(BaseResponse<UserProfile> response) {

                        handleUserProfileResponse(response);

                    }

                    @Override
                    public void onError(Throwable e) {

//                        handleErrorMsg(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }


    private void handleUserProfileResponse(BaseResponse<UserProfile> response) {


        if (response.getSuccess() && response.getData() != null) {

            UserProfile.User user = response.getData().getUser();

            if (user.getPhoto().contains("http")) {

                ImageLoader.loadImageError(getContext(), profilePhoto, user.getPhoto());
            } else {

                String url = "https://api.gamereel.io/storage/app/profile/" + user.getPhoto();

                ImageLoader.loadImageError(getContext(), profilePhoto, url);

            }

        }


    }

}
