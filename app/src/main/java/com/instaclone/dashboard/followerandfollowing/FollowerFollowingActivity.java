package com.instaclone.dashboard.followerandfollowing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.instaclone.R;

public class FollowerFollowingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_following);

        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.followingContent, new FollowingFollowerFragment()).commit();

    }
}
