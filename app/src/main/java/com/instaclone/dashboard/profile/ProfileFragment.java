package com.instaclone.dashboard.profile;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.instaclone.R;
import com.instaclone.ViewPagerAdapter;
import com.instaclone.base.BaseFragment;
import com.instaclone.dashboard.followerandfollowing.FollowingAndFollowerActivity;
import com.instaclone.dashboard.profile.updateprofile.UpdateProfileActivity;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.UserProfile;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.ImageLoader;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {
    private static final String ARG_USER_ID = "user_id";


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;
    @BindView(R.id.profile_name)
    AppCompatTextView profileName;
    @BindView(R.id.edit_profile)
    AppCompatTextView editProfile;
    @BindView(R.id.followers_count)
    TextView followersCount;
    @BindView(R.id.following_count)
    TextView followingCount;
    @BindView(R.id.header_menu)
    LinearLayout headerMenu;
    @BindView(R.id.post_count1)
    TextView postCount1;
    @BindView(R.id.followers_layout)
    LinearLayout followersLayout;
    @BindView(R.id.following_layout)
    LinearLayout followingLayout;

    private ViewPagerAdapter mViewPagerAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance(String userid) {

        ProfileFragment fragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userid);

        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        mViewPagerAdapter.addFragment(new PhotosFragment(), "Photos", R.drawable.ic_image);
//        mViewPagerAdapter.addFragment(new VideosFragment(), "Videos", R.drawable.ic_videocam_black_24dp);
        mViewPagerAdapter.addFragment(new Videos1Fragment(), "Videos", R.drawable.ic_video);


        viewPager.setAdapter(mViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            tabLayout.getTabAt(i).setIcon(mViewPagerAdapter.getIcon(i));

        }

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(ARG_USER_ID)) {

            String id = bundle.getString(ARG_USER_ID);

            headerMenu.setVisibility(View.GONE);

        }

//        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
//
//            tab.setText(mViewPagerAdapter.getTitle(position));
//            tab.setIcon(mViewPagerAdapter.getIcon(position));
//
//        }).attach();


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        getProfile();

    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

    @OnClick(R.id.edit_profile)
    public void onViewClicked() {

        Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
        startActivity(intent);

    }

    private void getProfile() {

        showLoading();

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

                        handleErrorMsg(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    private void handleUserProfileResponse(BaseResponse<UserProfile> response) {

        hideLoading();

        if (response.getSuccess() && response.getData() != null) {

            UserProfile.User user = response.getData().getUser();

            profileName.setText(user.getName());
            followersCount.setText(String.valueOf(user.getFollower()));
            followingCount.setText(String.valueOf(user.getFollowing()));
            postCount1.setText(String.valueOf(user.getPost_count()));

//            ImageLoader.loadImage(getContext(), profilePhoto, user.getPhoto());

            if (user.getPhoto().contains("http")) {

                ImageLoader.loadImageError(getContext(), profilePhoto, user.getPhoto());
            } else {

                String url = "https://api.gamereel.io/storage/app/profile/" + user.getPhoto();

                ImageLoader.loadImageError(getContext(), profilePhoto, url);

            }

        }


    }

    @OnClick({R.id.followers_layout, R.id.following_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.followers_layout:

                FollowingAndFollowerActivity.startActivity(getContext(), 2);

                break;
            case R.id.following_layout:

                FollowingAndFollowerActivity.startActivity(getContext(), 1);

                break;
        }
    }
}
