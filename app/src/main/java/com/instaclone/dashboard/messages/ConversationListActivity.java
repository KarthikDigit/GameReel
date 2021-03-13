package com.instaclone.dashboard.messages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.instaclone.R;

public class ConversationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);


        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.message_content, new ConversationFragment())
                .commit();

    }
}
