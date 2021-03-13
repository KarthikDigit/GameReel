package com.instaclone.dashboard.searchvideos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arasthel.spannedgridlayoutmanager.SpanSize;
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;
import com.instaclone.R;
import com.instaclone.base.BaseFragment;
import com.instaclone.dashboard.usersfeed.UserFeedActivity;
import com.instaclone.helper.SpaceItemDecorator;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.SearchData;
import com.instaclone.preference.Preferences;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
public class SearchVideoFragment extends BaseFragment implements SearchVideoListAdapter.OnItemClickListener {


    @BindView(R.id.photoListView)
    RecyclerView photoListView;

    private SearchVideoListAdapter mPhotoListAdapter;

    public SearchVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_videos, container, false);
        ButterKnife.bind(this, view);

        mPhotoListAdapter = new SearchVideoListAdapter(getContext(), new ArrayList<>());

        photoListView.setLayoutManager(new GridLayoutManager(getContext(), 4));


        SpannedGridLayoutManager manager =
                new SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3);

        manager.setItemOrderIsStable(true);


        manager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer pos) {

                if (pos % 7 == 1) return new SpanSize(2, 2);

                return new SpanSize(1, 1);
            }
        }));
        photoListView.setLayoutManager(manager);

//        photoListView.setHasFixedSize(true);

        SpaceItemDecorator decorator = new SpaceItemDecorator(getContext());
//
        decorator.setInsets(1);
        photoListView.addItemDecoration(decorator);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset_with);
//        photoListView.addItemDecoration(itemDecoration);
        photoListView.setAdapter(mPhotoListAdapter);

        photoListView.setNestedScrollingEnabled(false);


        mPhotoListAdapter.setOnItemClickListener(this);

        getAllMyFeeds();
        return view;
    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

    private void getAllMyFeeds() {

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().getSearchFeed(Preferences.INSTANCE.getAuthendicate())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<SearchData>>() {
                    @Override
                    public void onNext(BaseResponse<SearchData> response) {

                        handleSearchDataResponse(response);

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

    private void handleSearchDataResponse(BaseResponse<SearchData> response) {

        hideLoading();

        if (response.getSuccess() && response.getData() != null) {

            SearchData feed = response.getData();

            List<SearchData.Feed> feedList = feed.getFeeds();

            if (feedList != null && !feedList.isEmpty()) {

                mPhotoListAdapter.updateList(feedList);

            }

        }

    }

    @Override
    public void OnItemClick(View view, SearchData.Feed feed, int position) {


        UserFeedActivity.startUserActivity(getContext(), String.valueOf(feed.getUserId()));

    }
}
