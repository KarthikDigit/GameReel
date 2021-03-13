package com.instaclone.dashboard.usersfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.instaclone.R;
import com.instaclone.base.BaseActivity;

public class UserFeedActivity extends BaseActivity {

    private static final String EXTRA_ID = "user_id";


    public static void startUserActivity(Context context, String user_id) {

        Intent intent = new Intent(context, UserFeedActivity.class);
        intent.putExtra(EXTRA_ID, user_id);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        setBackButtonEnabledAndTitle(getString(R.string.explore));


        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_ID)) {

            String id = intent.getStringExtra(EXTRA_ID);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, UserFeedFragment.getInstance(id))
                    .commit();

        }


    }
}
