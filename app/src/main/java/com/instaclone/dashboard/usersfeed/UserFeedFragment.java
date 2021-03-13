package com.instaclone.dashboard.usersfeed;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.instaclone.R;
import com.instaclone.base.BaseFragment;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.UserFeed;
import com.instaclone.preference.Preferences;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.PlayerSelector;
import im.ene.toro.widget.Container;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFeedFragment extends BaseFragment {

    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.feedListView)
    Container mFeedListView;
    @BindView(R.id.header_menu)
    LinearLayout headerMenu;
    private UserFeedAdapter mHomeFeedAdapter;

    public UserFeedFragment() {
        // Required empty public constructor
    }

    public static UserFeedFragment getInstance(String userid) {

        UserFeedFragment fragment = new UserFeedFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userid);

        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_view, container, false);

        ButterKnife.bind(this, view);

        mHomeFeedAdapter = new UserFeedAdapter(getContext(), new ArrayList<>());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mFeedListView.setLayoutManager(manager);
        mFeedListView.setHasFixedSize(true);

        manager.setItemPrefetchEnabled(true);

        mFeedListView.setPlayerSelector(PlayerSelector.DEFAULT);
        mFeedListView.setCacheManager(mHomeFeedAdapter);
        mFeedListView.setAdapter(mHomeFeedAdapter);


        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(ARG_USER_ID)) {

            String id = bundle.getString(ARG_USER_ID);
            getAllMyFeeds(id);

        }

        headerMenu.setVisibility(View.GONE);


        return view;
    }


    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

    private void getAllMyFeeds(String id) {

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().getUserFeeds(Preferences.INSTANCE.getAuthendicate(), id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<UserFeed>>() {
                    @Override
                    public void onNext(BaseResponse<UserFeed> response) {

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

    private void handleMyFeedsResponse(BaseResponse<UserFeed> response) {

        hideLoading();

        if (response.getSuccess() && response.getData() != null) {

            List<UserFeed.Feed> myFeeds = response.getData().getFeeds();

            if (myFeeds != null && !myFeeds.isEmpty()) {

                mHomeFeedAdapter.update(myFeeds);

            }


        }


    }


}
