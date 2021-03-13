package com.instaclone.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

//import com.fxn.pix.Options;
//import com.fxn.pix.Pix;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.instaclone.MainActivity;
import com.instaclone.R;
import com.instaclone.Share.ShareActivity;
import com.instaclone.dashboard.home.HomeFragment;
import com.instaclone.pix.pix.Options;
import com.instaclone.pix.pix.Pix;
import com.instaclone.pix.utility.PermUtil;
import com.instaclone.uploadfiles.UploadActivity;
import com.instaclone.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DashBoardActivity";
    private static final int RequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        if (getSupportActionBar() != null) getSupportActionBar().hide();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        chooseFragment(R.id.navigation_home);

//        new Thread().start();


    }


    private void chooseFragment(int id) {

        Fragment fragment = Utils.getFragment(id);

        if (fragment != null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment).commit();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.navigation_add) {

//            Intent intent = InstagramLikeGallery.getGalleryIntent(DashBoardActivity.this);
//            intent.putExtra(ILGConstants.EXTRA_ENABLE_MULTI_SELECT_MODE, true);
//            startActivityForResult(intent, ILGRequestCode.GALLERY_GET_IMAGES);

//            Intent intent2 = new Intent(DashBoardActivity.this, ShareActivity.class);//ACTIVITY_NUM = 1
//            startActivity(intent2);

            Intent intent2 = new Intent(DashBoardActivity.this, UploadActivity.class);//ACTIVITY_NUM = 1
            startActivity(intent2);

//            fetchPicturesAndVideos();

            return false;
        }

        chooseFragment(item.getItemId());
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
////        switch (requestCode) {
//////            case ILGRequestCode.GALLERY_GET_IMAGES:
//////                if (resultCode == ILGResultCode.RESULT_OK) {
//////                    ArrayList<File> images = (ArrayList<File>) data.getSerializableExtra(ILGConstants.EXTRA_GALLERY_IMAGE_FILES);
//////
//////                    Log.e(TAG, "onActivityResult: " + new Gson().toJson(images));
//////
//////                }
////                break;
////        }
//    }

    private void fetchPicturesAndVideos() {

        Options options = Options.init()
                .setRequestCode(RequestCode)                                           //Request code for activity results
                .setCount(3)                                                   //Number of images to restict selection count
                .setFrontfacing(true)                                         //Front Facing camera on start
//                .setPreSelectedUrls(returnValue)                               //Pre selected Image Urls
                .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                .setMode(Options.Mode.All)                                     //Option to select only pictures or videos or both
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");                                       //Custom Path For media Storage

        Pix.start(DashBoardActivity.this, options);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            for (int i = 0; i < returnValue.size(); i++) {


//                Uri file = Uri.fromFile(new File(returnValue.get(i)));
//
//                String fileExt = MimeTypeMap.getFileExtensionFromUrl(file.toString());
//
//                Log.e(TAG, "onActivityResult: " + returnValue.get(i) + " File ex:   " + fileExt);
//
//                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
//                Glide.with(context)
//                        .load(uri)
//                        .placeholder(R.drawable.ic_video_place_holder)
//                        .into(imageView);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(DashBoardActivity.this, Options.init().setRequestCode(100));
                } else {
                    Toast.makeText(DashBoardActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
