package com.instaclone.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtil {


    public static void showDialog(Context context, String title, String content) {

        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .negativeText("Cancel")
                .show();

    }

    public static void showDialogWithCallBack(Context context, String title, String content, MaterialDialog.SingleButtonCallback singleButtonCallback, MaterialDialog.SingleButtonCallback singleButtonCallbackCancel) {

        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("Enable")
                .cancelable(false)
                .onPositive(singleButtonCallback)
                .negativeText("Cancel")
                .onNegative(singleButtonCallbackCancel)
                .show();

    }
}
