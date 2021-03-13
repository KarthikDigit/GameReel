package com.instaclone.Share;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.instaclone.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickImageFragment extends Fragment {

    private static final String TAG = "PickImageFragment";
    private static final int VIDEO_CAPTURE = 107;
    private static final int numberOfImagesToSelect = 5;

    public PickImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        Button btnLaunchCamera = (Button) view.findViewById(R.id.btnLaunchCamera);

        btnLaunchCamera.setText(R.string.open_gallery);

        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, numberOfImagesToSelect);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

//        Uri videoUri = data.getData();

//        Toast.makeText(getContext(), "Called", Toast.LENGTH_SHORT).show();

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            //The array list has the image paths of the selected images
            List<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

            passImages(images);

//            for (int i = 0; i < images.size(); i++) {
//
//                Log.e(TAG, "onActivityResult: " + images.get(i).path);
//
//            }

//            if (resultCode == RESULT_OK) {
//                Toast.makeText(getContext(), "Video saved to:\n" +
//                        videoUri, Toast.LENGTH_LONG).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getContext(), "Video recording cancelled.",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getContext(), "Failed to record video",
//                        Toast.LENGTH_LONG).show();
//            }
        }
    }

    private void passImages(List<Image> imageList) {

        Intent intent = new Intent(getActivity(), NextActivity.class);
        intent.putParcelableArrayListExtra(getString(R.string.selected_multiple_image), (ArrayList<? extends Parcelable>) imageList);
        startActivity(intent);

    }


}
