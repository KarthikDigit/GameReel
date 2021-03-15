package com.instaclone.dashboard.followerandfollowing;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.instaclone.R;
import com.instaclone.base.BaseFragment;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.Following;
import com.instaclone.preference.Preferences;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
public class FollowersFragment extends BaseFragment {


    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.followerView)
    RecyclerView followerView;

    private FollowerListAdapter mFollowerListAdapter;

    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        ButterKnife.bind(this, view);

        mFollowerListAdapter = new FollowerListAdapter(getContext(), new ArrayList<>());

        followerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followerView.setAdapter(mFollowerListAdapter);

        getFollowing();

        return view;
    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

    private void getFollowing() {

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().getFollowing(Preferences.INSTANCE.getAuthendicate(), 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<List<Following>>>() {
                    @Override
                    public void onNext(BaseResponse<List<Following>> response) {

                        handleFollowingResponse(response);

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

    private void handleFollowingResponse(BaseResponse<List<Following>> response) {

        hideLoading();

        if (response != null && response.getData() != null && response.getData().size() > 0) {

            List<Following> followings = response.getData();

            mFollowerListAdapter.update(followings);

        }


    }


}
