package com.instaclone.dashboard.followerandfollowing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.preference.Preferences;

public class FollowingAndFollowerActivity extends BaseActivity {

    private static final String TYPE = "type";


    public static void startActivity(Context context, int type) {

        Intent intent = new Intent(context, FollowingAndFollowerActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_and_follower);


        setBackButtonEnabledAndTitle(Preferences.INSTANCE.getUserName());


        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(TYPE)) {

            int type = intent.getIntExtra(TYPE, -1);

            if (type == 1) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new FollowingFragment()).commit();

            } else if (type == 2) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new FollowersFragment()).commit();
            }

        }

    }
}
