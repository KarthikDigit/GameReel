package com.instaclone.base;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.instaclone.auth.LoginActivity;
import com.instaclone.network.APIError;
import com.instaclone.network.APIErrorUtil;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.Deviceinfo;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.DialogUtil;
import com.instaclone.utils.ProgressUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final String TAG = "BaseActivity";

    private static final int RC_PHONE_STATS = 12;

    public Unbinder unbinder;


    protected CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getFcmTokenAndUpdate();


    }


    public void getLastLocation() {

        showLoading();


        showToast("Application Test");

    }


    public void setButterKnife() {
        unbinder = ButterKnife.bind(this);
    }


    protected void getFcmTokenAndUpdate() {

        String android_id = "";
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

//        Log.d("Android", "Android ID : " + android_id);


//        final String token = Preferences.INSTANCE.getFcmToken();
//
//        if (!(token != null && token.length() > 0)) {
////            a9cf10a5798da358
//
////
////
////
//            if (Preferences.INSTANCE.isUserLoggedIn()) {
//
//                final String finalAndroid_id = android_id;
//
//
//                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                    @Override
//                    public void onSuccess(final InstanceIdResult instanceIdResult) {
//
//                        Deviceinfo deviceinfo = new Deviceinfo();
//
//                        deviceinfo.setDevice_id(finalAndroid_id);
//                        deviceinfo.setPush_token_id(instanceIdResult.getToken());
//                        deviceinfo.setDevice_model("Android");
//                        String id = Preferences.INSTANCE.getUserId();
//                        deviceinfo.setUser_id(id);
//
//
////                    loge(new Gson().toJson(deviceinfo));
//
//                        disposable.add(RetrofitAdapter.getNetworkApiServiceClient()
//                                .postDeviceInfo(Preferences.INSTANCE.getAuthendicate(), deviceinfo)
//                                .subscribeOn(Schedulers.newThread())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribeWith(new DisposableObserver<String>() {
//
//
//                                    @Override
//                                    public void onNext(String s) {
////                                    dataSource.saveTokenAndDeviceID(instanceIdResult.getToken(), finalAndroid_id);
//
//                                        Preferences.INSTANCE.putFcmToken(instanceIdResult.getToken());
//                                        Preferences.INSTANCE.putFCMToken(instanceIdResult.getToken());
//                                        Log.e(TAG, "onSuccess: fcm success registered " + s);
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//
//                                        Log.e(TAG, "onFail: " + e.getMessage());
//
////                                    Log.e(TAG, "onFail: fcm fail to regidtered");
//
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//
//                                    }
//                                }));
//
//
////                    Log.e(TAG, "onSuccess: token " + instanceIdResult.getToken());
////                    Log.e(TAG, "onSuccess: id " + instanceIdResult.getId());
//
//
//                    }
//                });
//            } else {
//                loge("Already registered");
//            }
//        }


    }


    @AfterPermissionGranted(RC_PHONE_STATS)
    protected void phoneStatePermission() {
//        String[] perms = {Manifest.permission.READ_PHONE_STATE};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            // Already have permission, do the thing

//        getFcmTokenAndUpdate();
        // ...
//        } else {
//            getFcmTokenAndUpdate();
//            // Do not have permissions, request them now
//            EasyPermissions.requestPermissions(this, getString(R.string.phone_state_rationale),
//                    RC_PHONE_STATS, perms);
//        }
    }


    public void logout() {


//        LogoutRequest request = new LogoutRequest();
//        request.setUserId(Preferences.INSTANCE.getUserId());
//        request.setApiKey(Preferences.INSTANCE.getApiAccessToken());

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().logout(Preferences.INSTANCE.getAuthendicate())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        hideLoading();
                        callLogin();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        callLogin();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                }));


    }


    private void callLogin() {


        showToast("Logged out");

        Preferences.INSTANCE.clearPreference();

//        dataSource.clear();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);

//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
        if (unbinder != null) unbinder.unbind();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTitle(String title) {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(title);
        }

    }

    public void setBackButtonEnabledAndTitle(String title) {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorMenuBg)));


        }

    }

    public void showSnackBar(View view, String msg) {

        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);

        snackbar.show();
    }


    //    @Override
    public void onSuccess(Object object) {
        hideLoading();
    }

    //    @Override
    public void onFail(Throwable throwable) {
        hideLoading();

        try {
            Log.e(TAG, "onFail: error body " + ((HttpException) throwable).response().errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "onFail: json " + new Gson().toJson(((HttpException) throwable).response().errorBody()));

        APIError apiError = APIErrorUtil.parseError(((HttpException) throwable).response());

//        if (apiError.getData() instanceof String) {
//
//            showDialog("Error", apiError.getData().toString());
//
//        } else {
//
//            showToast("Something went wrong ");
//
//        }

        Log.e(TAG, "onFail: " + throwable.getMessage());


//        Toast.makeText(this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

//        progressStateCall(R.drawable.somethingwentwrong, "error");
    }


    //    @Override
    public void onNetworkFailure() {
        hideLoading();
        showDialogCallBack("Error", "There is no internet connection");
//        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        progressStateCall(R.drawable.nointernet, "nointernet");


    }


    public void showLoading() {

        ProgressUtils.showProgress(this, "Loading");

//        progressStateCall(R.drawable.nointernet, "loading");

    }

    public void showLoading(boolean isCancel) {

        ProgressUtils.showProgress(this, "Loading", isCancel);

//        progressStateCall(R.drawable.nointernet, "loading");

    }


    public void hideLoading() {

        ProgressUtils.hideProgress();
//        progressStateCall(R.drawable.nointernet, "content");

    }


    public void showToast(String msg) {

        Toast.makeText(this, "" + msg
                , Toast.LENGTH_SHORT).show();

    }


    public void showToast(@StringRes int msgID) {

        Toast.makeText(this, "" + msgID, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
////            String yes = getString(R.string.yes);
////            String no = getString(R.string.no);
////
////            // Do something after user returned from app settings screen, like showing a Toast.
////            Toast.makeText(
////                    this,
////                    getString(R.string.returned_from_app_settings_to_activity,
////                            hasCameraPermission() ? yes : no,
////                            hasLocationAndContactsPermissions() ? yes : no,
////                            hasSmsPermission() ? yes : no),
////                    Toast.LENGTH_LONG)
////                    .show();
//        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d(TAG, "onRationaleDenied:" + requestCode);
    }


    public void showDialog(String title, String content) {

        DialogUtil.showDialog(BaseActivity.this, title, content);

    }

    public void showDialogCallBack(String title, String content) {

        DialogUtil.showDialogWithCallBack(BaseActivity.this, title, content, (dialog, which) -> {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setClassName("com.android.phone","com.android.phone.NetworkSetting");
//                startActivity(intent);
            finish();
            Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(i);

        }, (dialog, which) -> finish());

    }


    public void loge(String message) {

        Log.e(TAG, "loge: " + message);

    }

    public void logi(String message) {

        Log.i(TAG, "logi: " + message);

    }

    public void logv(String message) {

        Log.v(TAG, "logv: " + message);

    }

    public void showLoop(List<?> list) {

        Gson gson = new Gson();

        int n = list.size();

        for (int i = 0; i < n; i++) {

            Object o = list.get(i);

            Log.e(TAG, "showLoop: " + gson.toJson(o));


        }


    }


    protected boolean checkListEmptyAndNull(List<?> list) {
        return list != null && !list.isEmpty();
    }


    public <T> T getSpinnerAdapterObject(ArrayAdapter arrayAdapter, int position) {

        return (T) arrayAdapter.getItem(position);
    }

    public boolean checkObjectIsNotNull(Object object) {

        return object != null;

    }

    public boolean checkArrayAdapterNotEmptyAndNotNull(ArrayAdapter arrayAdapter) {

        return arrayAdapter != null && arrayAdapter.getCount() > 0;
    }

    public boolean getLength(String s) {

        return s.length() > 0;
    }

    //    @Override
    public void onComplete(Location location) {
        hideLoading();
    }

    //    @Override
    public void onFailure(Exception e) {

        hideLoading();
        Toast.makeText(getApplicationContext(), "Something went wrong, Please try again", Toast.LENGTH_SHORT).show();

    }

    //    @Override
    public void needPermission() {

        hideLoading();

    }

    //    @Override
    public void enableLocation() {

        hideLoading();
    }


    public boolean isLocationServiceEnabled() {
//
//        if (!LocationApi.isLocationServicesAvailable(this)) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
//            builder.setTitle("Location Enable");  // GPS not found
//            builder.setMessage("Please enable loaction to proceed"); // Want to enable?
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//
//                }
//            });
//            builder.setNegativeButton("No", null);
//            builder.create().show();
//
//            return false;
//        }

        return true;
    }


    //    @Override
    public void onConnected() {

//        showToast("Connection is available");

    }

    //    @Override
    public void onDisconnected() {

//        showToast("Connection is not available");

    }

    protected void handleErrorMsg(Throwable e) {

        hideLoading();

        Log.e(TAG, "handleErrorMsg: " + e.getMessage());

        if (e instanceof HttpException) {

            try {
                String s = ((HttpException) e).response().errorBody().string();

                JSONObject jsonObject = new JSONObject(s);

                String s1 = jsonObject.optString("message");
//                showSnackBar(mLogin, s1);

                showToast(s1);

            } catch (IOException | JSONException e1) {

//                showSnackBar(mLogin, "Something went wrong, Please check your credentials");

                showToast("Something went wrong, Please check your credentials");

//            e1.printStackTrace();
            }
        } else {

            showToast("Something went wrong, Please check your credentials");
//            showSnackBar(mLogin, "Something went wrong, Please check your credentials");
        }


    }
}
