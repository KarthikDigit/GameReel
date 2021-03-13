package com.firefishcomms.instagramlikegallery.fragments;

import android.content.Context;

import com.firefishcomms.instagramlikegallery.activities.BaseActivity;

import androidx.fragment.app.Fragment;

/**
 * Created by Anton on 6/7/2018.
 */

public class BaseFragment extends Fragment {

    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof BaseActivity) {
            activity = (BaseActivity) context;
        }

        setMenuVisibility(isVisible());
    }
}
