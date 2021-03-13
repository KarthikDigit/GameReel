package com.instaclone;

import android.app.Application;
import android.os.FileUtils;

import com.google.firebase.FirebaseApp;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        FileUtils.createApplicationFolder();
        FirebaseApp.initializeApp(this);
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);

        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        Preferences.INSTANCE.createPreferences(this);

    }
}
