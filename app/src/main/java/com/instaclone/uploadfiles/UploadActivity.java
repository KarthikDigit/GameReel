package com.instaclone.uploadfiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.iceteck.silicompressorr.SiliCompressor;
import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.dashboard.DashBoardActivity;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.UploadRequest;
import com.instaclone.pix.pix.Options;
import com.instaclone.pix.pix.Pix;
import com.instaclone.pix.utility.PermUtil;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.Utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.instaclone.uploadfiles.UploadFilesService.getFilePath;

public class UploadActivity extends BaseActivity {

    private static final int RequestCode = 100;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.description)
    AppCompatEditText description;
    private List<String> returnValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload2);
        ButterKnife.bind(this);

        setBackButtonEnabledAndTitle("New Post");

        fetchPicturesAndVideos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.next:

                // Then just use the following:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(image.getWindowToken(), 0);
                }

                if (returnValue != null && !returnValue.isEmpty())
                    callUploadService(returnValue);
//                    new fetchImageAndVideos().execute(returnValue);
////                    makeFileJsonFormat(returnValue);
                else {
                    showToast("No File Selected");
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void callUploadService(List<String> fileList) {

//        String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
//
//
//        File file = new File(fileList.get(0));
//
//        Uri uri = Uri.fromFile(file);

//        try {
////            String filePath = SiliCompressor.with(this).compressVideo(file.getPath(), outputDir);
//
//            Log.e(TAG, "handleActionUploadFile: " + filePath);
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

        String mediaCaption = !TextUtils.isEmpty(description.getText().toString()) ? description.getText().toString() : " ";

        UploadFilesService.startActionFileUpload(this, fileList, mediaCaption);

        finish();


    }

    private void fetchPicturesAndVideos() {

        Options options = Options.init()
                .setRequestCode(RequestCode)                                           //Request code for activity results
                .setCount(5)                                                   //Number of images to restict selection count
                .setFrontfacing(true)                                         //Front Facing camera on start
//                .setPreSelectedUrls(returnValue)                               //Pre selected Image Urls
                .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                .setMode(Options.Mode.All)                                     //Option to select only pictures or videos or both
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");                                       //Custom Path For media Storage

        Pix.start(UploadActivity.this, options);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {

            returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            if (returnValue != null && !returnValue.isEmpty()) {


                Log.e(TAG, "onActivityResult: "+returnValue.get(0) );

                Uri file = Uri.fromFile(new File(returnValue.get(0)));
                setImage(file);
//                for (int i = 0; i < returnValue.size(); i++) {
//
//
////                Uri file = Uri.fromFile(new File(returnValue.get(i)));
////
////                String fileExt = MimeTypeMap.getFileExtensionFromUrl(file.toString());
////
////                Log.e(TAG, "onActivityResult: " + returnValue.get(i) + " File ex:   " + fileExt);
////
////                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
////                Glide.with(context)
////                        .load(uri)
////                        .placeholder(R.drawable.ic_video_place_holder)
////                        .into(imageView);
//
//                }
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
                    Pix.start(UploadActivity.this, Options.init().setRequestCode(100));
                } else {
                    Toast.makeText(UploadActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void setImage(Uri uri) {

        Glide.with(this)
                .asBitmap()
                .load(uri)
                .into(image);

    }

    private UploadRequest makeFileJsonFormat(List<String> fileList) {

        String mediaCaption = !TextUtils.isEmpty(description.getText().toString()) ? description.getText().toString() : " ";

        UploadRequest uR = new UploadRequest();
        uR.setDescription(mediaCaption);

        List<Object> imageVideoList = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {

            File file = new File(fileList.get(i));

            Uri uri = Uri.fromFile(file);

            String fileExt = MimeTypeMap.getFileExtensionFromUrl(uri.toString()).toLowerCase();


            if (fileExt.equalsIgnoreCase("mp4") ||
                    fileExt.equalsIgnoreCase("mov")) {
                UploadRequest.ImageVideo imageVideo = new UploadRequest.ImageVideo();
//                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

//                Bitmap bitmap=Glide.with(this)
//                        .asBitmap().load(file);


                Bitmap bitmap = null;
                try {
                    bitmap = Utils.retriveVideoFrameFromVideo(file.getPath());
                } catch (Throwable throwable) {
                    bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                }


                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 480, 240, false);
//                    YOUR_IMAGE_VIEW.setImageBitmap(bitmap);
                }

//                image.setImageBitmap(bitmap);

                String type = "image/png";
//                String imgBase64 = "data:" + type + ";base64," + Utils.convert(bMap);
                String imgBase64 = Utils.convert(bitmap);
                imageVideo.setCover_image(imgBase64);
                imageVideo.setCover_type(type);


                String videoType = "video/" + fileExt;
//                String videoBase64 = "data:" + videoType + ";base64," + Utils.getVideo64(file);
                String videoBase64 = Utils.getVideo64(file);

                imageVideo.setVideo(videoBase64);
                imageVideo.setType(videoType);

                imageVideoList.add(imageVideo);


            } else {

                UploadRequest.ImageOnly imageOnly = new UploadRequest.ImageOnly();

//                String imgBase64 = Utils.convertBase64(file);
                String type = "image/" + fileExt;
//                String type = "image/png";
//                String imgBase64 = "data:" + type + ";base64," + Utils.convertBase64(file);
                String imgBase64 = Utils.convertBase64(file);


                imageOnly.setImage(imgBase64);
                imageOnly.setType(type);
                imageVideoList.add(imageOnly);

            }


        }

        uR.setImageVideoList(imageVideoList);

        return uR;
//        fileUpload(uR);

//        Log.e(TAG, "makeFileJsonFormat: " + new Gson().toJson(uR));

    }

    private static final String TAG = "UploadActivity";

    private void fileUpload(UploadRequest uploadRequest) {

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient()
                .upload(Preferences.INSTANCE.getAuthendicate(), uploadRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        hideLoading();

                        Log.e(TAG, "onNext: " + s);

                    }

                    @Override
                    public void onError(Throwable e) {

                        hideLoading();
                        Log.e(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    public class fetchImageAndVideos extends AsyncTask<List<String>, Void, UploadRequest> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showLoading();
        }

        @Override
        protected UploadRequest doInBackground(List<String>... lists) {


            return makeFileJsonFormat(lists[0]);
        }

        @Override
        protected void onPostExecute(UploadRequest uploadRequest) {
            super.onPostExecute(uploadRequest);

            hideLoading();
            fileUpload(uploadRequest);

        }
    }
}
