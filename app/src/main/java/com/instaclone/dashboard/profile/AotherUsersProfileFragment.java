package com.instaclone.dashboard.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.instaclone.R;
import com.instaclone.ViewPagerAdapter;
import com.instaclone.base.BaseFragment;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.ChangeFollow;
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
public class AotherUsersProfileFragment extends BaseFragment {

    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;
    @BindView(R.id.name)
    AppCompatTextView name;
    @BindView(R.id.followers_count)
    TextView followersCount;
    @BindView(R.id.following_count)
    TextView followingCount;
    @BindView(R.id.message)
    AppCompatTextView message;
    @BindView(R.id.follow)
    AppCompatTextView follow;
    @BindView(R.id.post_count1)
    TextView postCount1;
    @BindView(R.id.followers_count1)
    TextView followersCount1;
    @BindView(R.id.following_count1)
    TextView followingCount1;

    private ViewPagerAdapter mViewPagerAdapter;

    private Integer userId = 0;
    private int isFollowed = 0;

    public AotherUsersProfileFragment() {
        // Required empty public constructor
    }

    public static AotherUsersProfileFragment getInstance(String userid) {

        AotherUsersProfileFragment fragment = new AotherUsersProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userid);

        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_users_profile, container, false);

        ButterKnife.bind(this, view);

        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());


        viewPager.setAdapter(mViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            tabLayout.getTabAt(i).setIcon(mViewPagerAdapter.getIcon(i));

        }

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(ARG_USER_ID)) {

            String id = bundle.getString(ARG_USER_ID);

            userId = Integer.valueOf(id);

            getProfile(id);

            mViewPagerAdapter.addFragment(PhotosFragment.getInstance(id), "Photos", R.drawable.ic_image);
            mViewPagerAdapter.addFragment(Videos1Fragment.getInstance(id), "Videos", R.drawable.ic_video);


        }


        return view;

    }

    @Override
    public void parseServerError(Response<?> response, boolean isToastMsg) {

    }

    private void getProfile(String id) {

        showLoading();

        disposable.add(RetrofitAdapter
                .getNetworkApiServiceClient()
                .getProfileById(Preferences.INSTANCE.getAuthendicate(), id)
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

            userId = user.getId();

            isFollowed = user.getIsFollowed();

            if (isFollowed == 1) {

                follow.setText(R.string.unfollow);

            } else {
                follow.setText(R.string.follow);
            }

            name.setText(user.getName());
            followersCount.setText(String.format("Followers %s", String.valueOf(user.getFollower())));
            followersCount1.setText(String.valueOf(user.getFollower()));
            followingCount.setText(String.format("Following %s", String.valueOf(user.getFollowing())));
            followingCount1.setText(String.valueOf(user.getFollowing()));
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

    @OnClick(R.id.follow)
    public void onFollowViewClicked() {

        changeFollowProfile(userId);

    }

    private void changeFollowProfile(Integer id) {

        showLoading();

        ChangeFollow changeFollow = new ChangeFollow();

        changeFollow.setAction(isFollowed == 0 ? "follow" : "unfollow");
        changeFollow.setFollow_id(String.valueOf(id));

        disposable.add(RetrofitAdapter
                .getNetworkApiServiceClient()
                .changeFollow(Preferences.INSTANCE.getAuthendicate(), changeFollow)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String response) {

                        String t = follow.getText().toString();

                        if (t.toLowerCase().equalsIgnoreCase("follow")) {

                            follow.setText("Unfollow");
                        } else {
                            follow.setText("Follow");

                        }

                        hideLoading();
                        loge(response);

//                        handleUserProfileResponse(response);

                    }

                    @Override
                    public void onError(Throwable e) {

                        hideLoading();

                        loge(e.getMessage());

                        handleErrorMsg(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
}
