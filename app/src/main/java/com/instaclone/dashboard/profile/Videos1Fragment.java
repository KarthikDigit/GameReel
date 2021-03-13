package com.instaclone.dashboard.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arasthel.spannedgridlayoutmanager.SpanSize;
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;
import com.instaclone.R;
import com.instaclone.base.BaseFragment;
import com.instaclone.helper.SpaceItemDecorator;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.MyPhotos;
import com.instaclone.preference.Preferences;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function1;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos1Fragment extends BaseFragment {
    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.photoListView)
    RecyclerView photoListView;
    @BindView(R.id.msgError)
    TextView msgError;

    private VideosListAdapter mVideosListAdapter;

    public Videos1Fragment() {
        // Required empty public constructor
    }


    public static Videos1Fragment getInstance(String userid) {

        Videos1Fragment fragment = new Videos1Fragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userid);

        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, view);

        mVideosListAdapter = new VideosListAdapter(new ArrayList<>(), getContext());


        SpannedGridLayoutManager manager = new SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3);

        manager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer integer) {
                return new SpanSize(1, 1);
            }
        }));

        SpaceItemDecorator itemDecoration = new SpaceItemDecorator(getContext());
        itemDecoration.setInsets(1);

        photoListView.setHasFixedSize(true);
        photoListView.setLayoutManager(manager);
        photoListView.addItemDecoration(itemDecoration);
        photoListView.setAdapter(mVideosListAdapter);

        photoListView.setNestedScrollingEnabled(false);

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(ARG_USER_ID)) {

            String id = bundle.getString(ARG_USER_ID);

            getPhotos(id);

        } else {

            getPhotos(Preferences.INSTANCE.getUserId());

        }


        return view;
    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }


    private void getPhotos(String id) {

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().getMyPhotos(Preferences.INSTANCE.getAuthendicate(), id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<MyPhotos>>() {
                    @Override
                    public void onNext(BaseResponse<MyPhotos> response) {

                        handlePhotosResponse(response);

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

    private void handlePhotosResponse(BaseResponse<MyPhotos> response) {

        hideLoading();

        if (response.getSuccess() && response.getData() != null && response.getData().getVideos() != null && response.getData().getVideos().size() > 0) {

            mVideosListAdapter.update(response.getData().getVideos());

            photoListView.setVisibility(View.VISIBLE);
            msgError.setVisibility(View.GONE);

        } else {
            photoListView.setVisibility(View.GONE);
            msgError.setVisibility(View.VISIBLE);
            mVideosListAdapter.update(new ArrayList<>());

        }


    }


}
