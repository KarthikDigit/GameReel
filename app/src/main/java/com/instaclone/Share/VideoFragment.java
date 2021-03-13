package com.instaclone.Share;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.darsh.multipleimageselect.models.Image;
import com.instaclone.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private static final int VIDEO_CAPTURE = 101;

    private static final String TAG = "VideoFragment";

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        Button btnLaunchCamera = (Button) view.findViewById(R.id.btnLaunchCamera);

        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, VIDEO_CAPTURE);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        Uri videoUri = data.getData();

        if (requestCode == VIDEO_CAPTURE) {

            String videoUrl = getRealPathFromURI(getContext(), videoUri);

            postVideo(videoUrl);

        }
    }

    private void postVideo(String videoUrl) {

        Intent intent = new Intent(getActivity(), NextActivity.class);
        intent.putExtra(getString(R.string.selected_video), videoUrl);
        startActivity(intent);

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private boolean hasCamera() {
        return (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY));
    }

}
