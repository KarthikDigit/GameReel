package com.instaclone.dashboard.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.dashboard.usersfeed.UserFeedFragment;
import com.instaclone.preference.Preferences;

public class AnotherUserDetailsActivity extends BaseActivity {

    private static final String EXTRA_ID = "user_id";
    private static final String EXTRA_NAME = "user_name";


    public static void startUserActivity(Context context, String user_id, String name) {

        Intent intent = new Intent(context, AnotherUserDetailsActivity.class);
        intent.putExtra(EXTRA_ID, user_id);
        intent.putExtra(EXTRA_NAME, name);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        setBackButtonEnabledAndTitle(getString(R.string.explore));


        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_ID) && intent.hasExtra(EXTRA_NAME)) {

            String id = intent.getStringExtra(EXTRA_ID);
            String name = intent.getStringExtra(EXTRA_NAME);

            setBackButtonEnabledAndTitle(name);

            String user_id = Preferences.INSTANCE.getUserId();

            if (!TextUtils.isEmpty(user_id) && user_id.equalsIgnoreCase(id)) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, ProfileFragment.getInstance(id))
                        .commit();

            } else {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, AotherUsersProfileFragment.getInstance(id))
                        .commit();
            }

        }


    }
}
