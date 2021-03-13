package com.instaclone.uploadfiles;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.ContentUris;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;

//import com.iceteck.silicompressorr.SiliCompressor;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.UploadRequest;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.Utils;
import com.vincent.videocompressor.VideoCompress;
import com.vincent.videocompressor.VideoController;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Response;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadFilesService extends IntentService {

    private static final String TAG = "UploadFilesService";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPLOAD_FILES = "com.instaclone.uploadfiles.action.UPLOAD_FILES";

    // TODO: Rename parameters
    private static final String EXTRA_UPLOADING_FILES = "com.instaclone.uploadfiles.extra.UPLOAD_FILES";
    private static final String EXTRA_DESCRIPTION = "com.instaclone.uploadfiles.extra.DESCRIPTION";

    public UploadFilesService() {
        super("UploadFilesService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFileUpload(Context context, List<String> param1, String des) {
        Intent intent = new Intent(context, UploadFilesService.class);
        intent.setAction(ACTION_UPLOAD_FILES);
        intent.putExtra(EXTRA_DESCRIPTION, des);
        intent.putStringArrayListExtra(EXTRA_UPLOADING_FILES, (ArrayList<String>) param1);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e(TAG, "onHandleIntent: Service is started ");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_FILES.equals(action)) {
                final List<String> fileList = intent.getStringArrayListExtra(EXTRA_UPLOADING_FILES);
                final String des = intent.getStringExtra(EXTRA_DESCRIPTION);
                if (fileList != null) {
                    handleActionUploadFile(fileList, des);
                }
            }
        }
    }

    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUploadFile(List<String> fileList, String des) {
        // TODO: Handle action Upload Files

//        Toast.makeText(this, "File Uploading is started", Toast.LENGTH_SHORT).show();

        String mediaCaption = !TextUtils.isEmpty(des) ? des : " ";

        UploadRequest uR = new UploadRequest();
        uR.setDescription(mediaCaption);

        List<Object> imageVideoList = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {

            Log.e(TAG, "handleActionUploadFile: " + fileList.get(i));

            File file = new File(fileList.get(i));

            Uri uri = Uri.fromFile(file);

            int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));

            Log.e(TAG, "handleActionUploadFile: oSize " + file_size);

            outputDir = outputDir + "/" + "VOD_" + System.currentTimeMillis() + ".mp4";
            File file2 = new File(outputDir);
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            boolean isCompressed = VideoCompress.videoCompress(file.getPath(), outputDir, new VideoController.CompressProgressListener() {
                @Override
                public void onProgress(float percent) {

                    Log.e(TAG, "onProgress: " + percent);
                }
            });

            File file1 = new File(outputDir);

            int cFileSize = Integer.parseInt(String.valueOf(file1.length() / 1024));

            Log.e(TAG, "handleActionUploadFile: oSize " + file_size);
//                String filePath = SiliCompressor.with(this).compressVideo(getFilePath(this, uri), outputDir);

            Log.e(TAG, "handleActionUploadFile: " + cFileSize);

        }

//            Log.e(TAG, "handleActionUploadFile: " + file.getAbsolutePath());
//
//            String fileExt = MimeTypeMap.getFileExtensionFromUrl(uri.toString()).toLowerCase();
//
//
//            if (fileExt.equalsIgnoreCase("mp4") ||
//                    fileExt.equalsIgnoreCase("mov")) {
//                UploadRequest.ImageVideo imageVideo = new UploadRequest.ImageVideo();
//                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
//
////                bMap = Bitmap.createScaledBitmap(bMap, 480, 240, false);
//
////                Bitmap bitmap=Glide.with(this)
////                        .asBitmap().load(file);
//
//
////                Bitmap bitmap = null;
////                try {
////                    bitmap = Utils.retriveVideoFrameFromVideo(file.getPath());
////                } catch (Throwable throwable) {
////                    bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
////                }
////
////
////                if (bitmap != null) {
////                    bitmap = Bitmap.createScaledBitmap(bitmap, 480, 240, false);
//////                    YOUR_IMAGE_VIEW.setImageBitmap(bitmap);
////                }
//
////                image.setImageBitmap(bitmap);
//
//                String type = "image/png";
////                String imgBase64 = "data:" + type + ";base64," + Utils.convert(bMap);
//                String imgBase64 = Utils.convert(bMap);
//                imageVideo.setCover_image(imgBase64);
//                imageVideo.setCover_type(type);
//
//                int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
//
//                Log.e(TAG, "handleActionUploadFile: oSize " + file_size);
//
////                File f = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Silicompressor/videos/compressed.mp4");
//
//                outputDir = outputDir + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
//
//                String compresedFile = null;
//                try {
//                    compresedFile = SiliCompressor.with(this).compressVideo(getFilePath(this, uri), outputDir)
//                    ;
//
//                    File comFile = new File(compresedFile);
//
//                    int filesize = Integer.parseInt(String.valueOf(comFile.length() / 1024));
//
//                    Log.e(TAG, "handleActionUploadFile: cSize " + filesize);
//
//                    String videoType = "video/" + fileExt;
////                String videoBase64 = "data:" + videoType + ";base64," + Utils.getVideo64(file);
//                    String videoBase64 = Utils.getVideo64(comFile);
//
//                    imageVideo.setVideo(videoBase64);
//                    imageVideo.setType(videoType);
//
//                    imageVideoList.add(imageVideo);
//
//                } catch (CompressionException e) {
//                    e.printStackTrace();
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//
//
//            } else {
//
//                UploadRequest.ImageOnly imageOnly = new UploadRequest.ImageOnly();
//
////                String imgBase64 = Utils.convertBase64(file);
//                String type = "image/" + fileExt;
////                String type = "image/png";
////                String imgBase64 = "data:" + type + ";base64," + Utils.convertBase64(file);
////                Environment.getExternalStorageDirectory()
////                Compressor compressor = Compressor.INSTANCE.compress(this, file);
////                File file1 = new Compressor().compress(this, file);
//
////                try {
////
////                    File compressedImageFile = new Compressor(this).compressToFile(file);
//
//                String imgBase64 = Utils.convertBase64(file);
//
//
//                imageOnly.setImage(imgBase64);
//                imageOnly.setType(type);
//                imageVideoList.add(imageOnly);
//
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//
//            }
//
//
//        }
//
//        uR.setImageVideoList(imageVideoList);
//
//        callFileUploadApi(uR);
    }


    private void callFileUploadApi(UploadRequest uploadRequest) {

        try {
            Response<String> call = RetrofitAdapter.getNetworkApiServiceClient()
                    .uploadRetrofit(Preferences.INSTANCE.getAuthendicate(), uploadRequest).execute();

            if (call.isSuccessful()) {

                Log.e(TAG, "callFileUploadApi: file uploaded successfully");

//                Toast.makeText(this, "Files have been successfully uploaded", Toast.LENGTH_SHORT).show();

            } else {

                String err = call.errorBody().string();

                Log.e(TAG, "callFileUploadApi: " + err);

            }

        } catch (IOException e) {
            Log.e(TAG, "callFileUploadApi: " + e.getMessage());
        }
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(String s) {
//
//                        Log.e(TAG, "onNext: " + s);
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        Log.e(TAG, "onError: " + e.getMessage());
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                }));

    }


    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
