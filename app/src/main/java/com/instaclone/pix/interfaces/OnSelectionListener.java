package com.instaclone.pix.interfaces;

import android.view.View;

import com.instaclone.pix.modals.Img;


/**
 * Created by akshay on 07/05/18.
 */


public interface OnSelectionListener {
    void onClick(Img Img, View view, int position);

    void onLongClick(Img img, View view, int position);
}
