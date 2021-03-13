package com.instaclone.Share;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.darsh.multipleimageselect.models.Image;
import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.utils.FileUtils;
import com.instaclone.utils.UniversalImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 7/24/2017.
 */

public class NextActivity extends BaseActivity {

    private static final String TAG = "NextActivity";

//    //firebase
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference myRef;
//    private FirebaseMethods mFirebaseMethods;

    //widgets
    private EditText mCaption;

    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private Bitmap bitmap;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
//        mFirebaseMethods = new FirebaseMethods(NextActivity.this);
        mCaption = (EditText) findViewById(R.id.caption);

//        setupFirebaseAuth();

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        ImageView backArrow = (ImageView) findViewById(R.id.ivBackArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the activity");
                finish();
            }
        });


        TextView share = (TextView) findViewById(R.id.tvShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the final share screen.");
                //upload the image to firebase
                Toast.makeText(NextActivity.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                String caption = mCaption.getText().toString();

                if (intent.hasExtra(getString(R.string.selected_image))) {
                    imgUrl = intent.getStringExtra(getString(R.string.selected_image));
                    Log.e(TAG, "onClick: " + imgUrl);
                    uploadFile(imgUrl);
//                    mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, imgUrl,null);
                } else if (intent.hasExtra(getString(R.string.selected_video))) {
                    imgUrl = intent.getStringExtra(getString(R.string.selected_video));
                    Log.e(TAG, "onClick: " + imgUrl);
                    uploadVideoFile(imgUrl);
//                    mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, imgUrl,null);
                } else if (intent.hasExtra(getString(R.string.selected_multiple_image))) {

                    List<Image> imageList = intent.getParcelableArrayListExtra(getString(R.string.selected_multiple_image));

                    if (imageList != null && !imageList.isEmpty()) {
                        imgUrl = imageList.get(0).path;
                        uploadMultipleFile(imageList);
                    }


                } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                    bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));

                    Uri uri = getImageUri(NextActivity.this, bitmap);
                    String selectedImagePath = getPath(uri);
                    uploadFile(selectedImagePath);
                    Log.e(TAG, "onClick: " + selectedImagePath);
//                    mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, null,bitmap);
                }


            }
        });

        setImage();
    }

    private void uploadMultipleFile(List<Image> imageList) {

        if (imageList != null && !imageList.isEmpty()) {

            showLoading();

            String des = mCaption.getText().toString();

            List<MultipartBody.Part> parts = new ArrayList<>();

            for (int i = 0; i < imageList.size(); i++) {

                File file = new File(imageList.get(i).path);


                // create RequestBody instance from file
                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(FileUtils.MIME_TYPE_IMAGE),
                                file
                        );

                // MultipartBody.Part is used to send also the actual file name
                int n = i + 1;
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image_" + n, file.getName(), requestFile);

                parts.add(body);


            }

//
            // add another part within the multipart request
            String descriptionString = "hello, this is description speaking " + des;
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, descriptionString);

            RequestBody is_image =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "1");

            RetrofitAdapter.getNetworkApiServiceClient1()
                    .uploadMultipleFile(description, is_image, parts).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    hideLoading();

                    if (response.isSuccessful()) {

                        showToast("Uploaded Successfully");

                        finish();

                    } else {
                        showToast("Failed");
                        finish();
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    hideLoading();
                    Log.e(TAG, "onFailure: " + t.getMessage());

                }
            });

        }

    }


    private void uploadFile(String path) {

        if (path != null) {

            showLoading();

            String des = mCaption.getText().toString();

//            File file = new File(mAppend + path);
            File file = new File(path);

            Log.e(TAG, "uploadFile: " + file.getAbsolutePath());

            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(FileUtils.MIME_TYPE_IMAGE),
                            file
                    );

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image_1", file.getName(), requestFile);

            // add another part within the multipart request
            String descriptionString = "hello, this is description speaking " + des;
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, descriptionString);

            RequestBody is_image =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "1");

            RetrofitAdapter.getNetworkApiServiceClient1()
                    .upload(description, is_image, body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    hideLoading();

//                    try {
//                        Log.e(TAG, "onResponse: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        Log.e(TAG, "onResponse: " + e.getMessage());
//                    }

                    if (response.isSuccessful()) {

                        showToast("Uploaded Successfully");

                        finish();
//                        try {
//                            Log.e(TAG, "onResponse: " + response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                    } else {
                        showToast("Failed");
                        finish();
//                        try {
//                            Log.e(TAG, "onResponse: ." + response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    hideLoading();
                    Log.e(TAG, "onFailure: " + t.getMessage());

                }
            });

        }

    }

    private void uploadVideoFile(String path) {

        if (path != null) {

            showLoading();

            String des = mCaption.getText().toString();

//            File file = new File(mAppend + path);
            File file = new File(path);

            Log.e(TAG, "uploadFile: " + file.getAbsolutePath());

            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(FileUtils.MIME_TYPE_IMAGE),
                            file
                    );

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            // add another part within the multipart request
            String descriptionString = "hello, this is description speaking " + des;
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, descriptionString);

            RequestBody is_image =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "1");

            RetrofitAdapter.getNetworkApiServiceClient1()
                    .upload(description, is_image, body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    hideLoading();

//                    try {
//                        Log.e(TAG, "onResponse: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        Log.e(TAG, "onResponse: " + e.getMessage());
//                    }

                    if (response.isSuccessful()) {

                        showToast("Uploaded Successfully");

                        finish();
//                        try {
//                            Log.e(TAG, "onResponse: " + response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                    } else {
                        showToast("Failed");
                        finish();
//                        try {
//                            Log.e(TAG, "onResponse: ." + response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    hideLoading();
                    Log.e(TAG, "onFailure: " + t.getMessage());

                }
            });

        }

    }


    private String getPath(Uri uri) {

        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);


        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        path = cursor.getString(column_index);
        cursor.close();
        return path;
//        cursor.getString(column_index);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void someMethod() {
        /*
            Step 1)
            Create a data model for Photos

            Step 2)
            Add properties to the Photo Objects (caption, date, imageUrl, photo_id, tags, user_id)

            Step 3)
            Count the number of photos that the user already has.

            Step 4)
            a) Upload the photo to Firebase Storage
            b) insert into 'photos' node
            c) insert into 'user_photos' node

         */

    }


    /**
     * gets the image url from the incoming intent and displays the chosen image
     */
    private void setImage() {
        intent = getIntent();
        ImageView image = (ImageView) findViewById(R.id.imageShare);

        if (intent.hasExtra(getString(R.string.selected_image))) {
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            Log.d(TAG, "setImage: got new image url: " + imgUrl);
            UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
        } else if (intent.hasExtra(getString(R.string.selected_video))) {
            imgUrl = intent.getStringExtra(getString(R.string.selected_video));


            Glide.with(this)
                    .asBitmap()
                    .load(Uri.fromFile(new File(imgUrl)))
                    .into(image);

        } else if (intent.hasExtra(getString(R.string.selected_multiple_image))) {

            List<Image> imageList = intent.getParcelableArrayListExtra(getString(R.string.selected_multiple_image));

            if (imageList != null && !imageList.isEmpty()) {
                imgUrl = imageList.get(0).path;
                UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
            }


        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));
            Log.d(TAG, "setImage: got new bitmap");
            image.setImageBitmap(bitmap);
        }
    }

     /*
     ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
//    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//        Log.d(TAG, "onDataChange: image count: " + imageCount);
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                imageCount = mFirebaseMethods.getImageCount(dataSnapshot);
//                Log.d(TAG, "onDataChange: image count: " + imageCount);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
    @Override
    public void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
    }
}
