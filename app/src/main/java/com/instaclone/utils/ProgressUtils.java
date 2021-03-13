package com.instaclone.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class ProgressUtils {

    private static final String TAG = "ProgressUtils";
    private static ProgressDialog progressDialog;
    private static AlertDialog dialog;

    public static void showProgress(Context context, String msg) {

        if (dialog == null) {

            dialog = new SpotsDialog.Builder().setContext(context).build();
            dialog.setCancelable(true);
            dialog.show();

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            //View view = getLayoutInflater().inflate(R.layout.progress);
//            builder.setView(R.layout.progress);
//            dialog = builder.create();
//            dialog.show();
        }

//        if (progressDialog == null) {
//
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage(msg);
//            progressDialog.setCancelable(true);
//            progressDialog.show();
//
//        }

    }

    public static void showProgress(Context context, String msg, boolean cancelable) {

        if (dialog == null) {

            dialog = new SpotsDialog.Builder().setContext(context).build();
            dialog.setCancelable(cancelable);
            dialog.setMessage(msg);
            dialog.show();

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            //View view = getLayoutInflater().inflate(R.layout.progress);
//            builder.setView(R.layout.progress);
//            dialog = builder.create();
//            dialog.show();
        }


//        progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage(msg);
//        progressDialog.setCancelable(cancelable);
//        progressDialog.show();

    }

    public static void hideProgress() {

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

//        if (progressDialog != null) {
//            progressDialog.dismiss();
////            progressDialog = null;
//        }
    }
}
