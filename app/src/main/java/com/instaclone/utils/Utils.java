package com.instaclone.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Base64;

import com.instaclone.R;
import com.instaclone.dashboard.home.HomeFragment;
import com.instaclone.dashboard.home.HomeRecyclerTestFragment;
import com.instaclone.dashboard.home.updatedview.HomeViewFragment;
import com.instaclone.dashboard.messages.Message;
import com.instaclone.dashboard.messages.User;
import com.instaclone.dashboard.profile.AotherUsersProfileFragment;
import com.instaclone.dashboard.profile.ProfileFragment;
import com.instaclone.dashboard.searchvideos.SearchVideoFragment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;

public class Utils {


    public static Fragment getFragment(int id) {

        Fragment fragment = null;

        switch (id) {
            case R.id.navigation_home:

                fragment = new HomeViewFragment();

                break;
            case R.id.navigation_search:

                fragment = new SearchVideoFragment();

                break;
            case R.id.navigation_profile:

                fragment = new ProfileFragment();

                break;

            default:

                fragment = new HomeFragment();

                break;
        }

        return fragment;

    }

    public static List<Message> getMessageList() {

        List<Message> messageList = new ArrayList<>();

        Message message = new Message();
        message.setMessage("Test");
        message.setCreatedAt(System.currentTimeMillis());
        User user = new User();
        user.setNickname("John");
        user.setUserId("5");
        message.setSender(user);
        messageList.add(message);

        message = new Message();
        message.setMessage("Test Android");
        message.setCreatedAt(System.currentTimeMillis());
        user = new User();
        user.setNickname("John");
        user.setUserId("5");
        message.setSender(user);
        messageList.add(message);

        message = new Message();
        message.setMessage("Test");
        message.setCreatedAt(System.currentTimeMillis());
        user = new User();
        user.setNickname("No Problem");
        user.setUserId("1");
        message.setSender(user);
        messageList.add(message);

        return messageList;

    }

    public static String getBase64Image(File imgFile) {

        Bitmap bm = BitmapFactory.decodeFile(imgFile.getPath());
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
        return Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);


    }


    public static String getVideo64(File file) {

        try {
            InputStream inputStream = new FileInputStream(file);

            return Base64.encodeToString(getBytes(inputStream), Base64.DEFAULT);
//            Document=Base64.encodeToString(bytes,Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static String convertBase64(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static String encodeFileToBase64Binary(File yourFile) {

        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encoded;
    }
}
