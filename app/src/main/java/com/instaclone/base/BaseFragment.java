package com.instaclone.base;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.battlelounge.utils.ProgressUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.instaclone.utils.ProgressUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

/**
 * Created by yasar on 26/3/18.
 */

public abstract class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final String TAG = "BaseFragment";


    private Unbinder unbinder;

    protected CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setButterKnife(Fragment fragment, View view) {
        unbinder = ButterKnife.bind(fragment, view);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) disposable.dispose();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        if (disposable != null) disposable.dispose();


    }

    @Override
    public void onDetach() {
        super.onDetach();


    }


    protected void setText(TextInputLayout text, Object vale) {

//        if (vale != null && !(vale instanceof String)) {
//            text.getEditText().setText(StringUtils.getString(vale.toString()));
//        } else {
//            text.getEditText().setText(StringUtils.getString((String) vale));
//        }


    }

    public int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(getActivity(), colorId);
    }


    public void showSnackBar(View view, String msg) {

        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public void onSuccess(Object object) {
        hideLoading();
    }


    public void showLoading() {

        ProgressUtils.showProgress(getContext(), "Loading");

//        progressStateCall(R.drawable.nointernet, "loading");

    }


    public void hideLoading() {

        ProgressUtils.hideProgress();
//        progressStateCall(R.drawable.nointernet, "content");

    }


    public void showToast(String msg) {

        if (getActivity() != null)

            Toast.makeText(getContext(), "" + msg
                    , Toast.LENGTH_SHORT).show();

    }


    public void showToast(@StringRes int msgID) {
        if (getActivity() != null)
            Toast.makeText(getContext(), "" + msgID, Toast.LENGTH_SHORT).show();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public View.OnClickListener errorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callNetwork();
        }
    };

    public void callNetwork() {

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


    public String getText(TextInputLayout inputLayout) {

        return inputLayout.getEditText().getText().toString();
    }


    //@Override
    public void onComplete(Location location) {

        hideLoading();

    }

    //@Override
    public void onFailure(Exception e) {

        hideLoading();
        Toast.makeText(getContext(), "Something went wrong, Please try again", Toast.LENGTH_SHORT).show();
    }

    //@Override
    public void needPermission() {
        hideLoading();
    }

    //@Override
    public void enableLocation() {
        hideLoading();
    }


//    public boolean isLocationServiceEnabled() {
//
//        if (!LocationApi.isLocationServicesAvailable(getActivity())) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
//
//        return true;
//    }

    //@Override
    public void serverError(Response<?> response, boolean isToastMsg) {


//        String errorMsg = MyCallBackWrapper.getErrorMessage(response.errorBody());
//
//
//        showToast(errorMsg);

//        ResponseBody baseResponse = APIErrorUtil.cloneResponseBody(response);
//        BaseApiError baseApiError = APIErrorUtil.parseErrorTest(BaseApiError.class, baseResponse);
//        if (baseApiError != null) {
//
//            if (isToastMsg)
//                showToast(baseApiError.getMessage());
//
//        }
//        parseServerError(response, isToastMsg);
    }

    public abstract void parseServerError(Response<?> response, boolean isToastMsg);

    //    @Override
    public void onTimeout(boolean isToastMsg) {

        if (isToastMsg) {

            showToast("Timeout");
        } else {
//            showViewError("Timeout");
        }

    }

    //@Override
    public void onNetworkError(boolean isToastMsg) {

        if (isToastMsg) {

            showToast("There is no internet connection");

        } else {
//            showViewError("There is no internet connection");
        }

    }

    //@Override
    public void onUnknownError(String message, boolean isToastMsg) {

        if (isToastMsg) {

            showToast(message);

        } else {

//            showViewError(message);

        }


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
