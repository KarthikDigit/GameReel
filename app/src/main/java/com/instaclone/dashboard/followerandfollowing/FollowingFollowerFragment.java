package com.instaclone.dashboard.followerandfollowing;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.instaclone.R;
import com.instaclone.ViewPagerAdapter;
import com.instaclone.dashboard.profile.PhotosFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFollowerFragment extends Fragment {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.dot_menu)
    ImageView dotMenu;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    public FollowingFollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following_follower, container, false);

        ButterKnife.bind(this, view);


        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        mViewPagerAdapter.addFragment(new FollowersFragment(), "128 Followers");
        mViewPagerAdapter.addFragment(new FollowingFragment(), "128 Following");


        viewPager.setAdapter(mViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);


        return view;

    }

    @OnClick({R.id.back, R.id.dot_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.dot_menu:
                break;
        }
    }
}
