package com.instaclone.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.instaclone.R;
import com.instaclone.dashboard.DashBoardActivity;
import com.instaclone.preference.Preferences;

public class MultipleLoginScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_login_screen);

        if (Preferences.INSTANCE.isUserLoggedIn()) {

            Intent intent = new Intent(this, DashBoardActivity.class);
            startActivity(intent);
            finish();

        }

    }

    public void moveToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void moveToSignUpActivity(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }
}
