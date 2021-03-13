package com.instaclone.dashboard.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.instaclone.R;
import com.instaclone.base.BaseFragment;
import com.instaclone.helper.VerticalSpacingItemDecorator;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.MyFeeds;
import com.instaclone.network.response.MyPhotos;
import com.instaclone.preference.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.homeListView)
    VideoPlayerRecyclerView homeListView;

//    private HomeFeedAdapter mHomeFeedAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

//        mHomeFeedAdapter = new HomeFeedAdapter();

//        homeListView.setLayoutManager(new LinearLayoutManager(getContext()));
//        homeListView.setAdapter(mHomeFeedAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeListView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        homeListView.addItemDecoration(itemDecorator);

//        ArrayList<MediaObject> mediaObjects = new ArrayList<MediaObject>(Arrays.asList(Resources.MEDIA_OBJECTS));
//        homeListView.setMediaObjects(mediaObjects);
//        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(mediaObjects, initGlide());
//        homeListView.setAdapter(adapter);

        getAllMyFeeds();

        return view;

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

//            ArrayList<MyFeeds> mediaObjects = new ArrayList<MyFeeds>(Arrays.asList(Resources.MEDIA_OBJECTS));
            homeListView.setMediaObjects(myFeeds);
            VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(myFeeds, initGlide());
            homeListView.setAdapter(adapter);


        }


    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(getContext())
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if (homeListView != null)
            homeListView.releasePlayer();
        super.onDestroy();
    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

}
