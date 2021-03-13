package com.instaclone.dashboard.profile.updateprofile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.cameraorgallery.CameraAndGallary;
import com.instaclone.cameraorgallery.ImageUtils;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.LoginRequest;
import com.instaclone.network.request.ProfileUpdateRequest;
import com.instaclone.network.request.UpdateProfile;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.LoginResponse;
import com.instaclone.network.response.UserProfile;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.ImageLoader;
import com.instaclone.utils.TextInputUtil;
import com.instaclone.utils.Utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class UpdateProfileActivity extends BaseActivity implements CameraAndGallary.CameraAndGallaryCallBack {

    private static final int REQUEST_CODE = 123;
    private static final String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_PER_CAMERA = 145;

    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;
    @BindView(R.id.profile_layout)
    ConstraintLayout profileLayout;
    @BindView(R.id.name)
    TextInputEditText name;
    @BindView(R.id.first_name)
    TextInputEditText firstName;
    @BindView(R.id.last_name)
    TextInputEditText lastName;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.dob)
    TextInputEditText dob;
    @BindView(R.id.mobile_no)
    TextInputEditText mobileNo;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.group_layout)
    RadioGroup groupLayout;
    final Calendar myCalendar = Calendar.getInstance();
    private CameraAndGallary cameraAndGallary;

    private static final String TAG = "UpdateProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        cameraAndGallary = new CameraAndGallary(this, this);

        setBackButtonEnabledAndTitle(getString(R.string.profile_update));

        getProfile(Preferences.INSTANCE.getUserId());

//        mobileNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b) {
//                    new DatePickerDialog(UpdateProfileActivity.this,
//                            date, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                }
//            }
//        });
        dob.setOnClickListener(view -> new DatePickerDialog(UpdateProfileActivity.this,
                date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

    }

    private void getProfile(String id) {

        showLoading();

        disposable.add(RetrofitAdapter
                .getNetworkApiServiceClient()
                .getProfileById(Preferences.INSTANCE.getAuthendicate(), id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<UserProfile>>() {
                    @Override
                    public void onNext(BaseResponse<UserProfile> response) {

                        handleUserProfileResponse(response);

                    }

                    @Override
                    public void onError(Throwable e) {

                        handleErrorMsg(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    private String getString(String s) {
        return !TextUtils.isEmpty(s) ? s : "";
    }

    private void handleUserProfileResponse(BaseResponse<UserProfile> response) {

        hideLoading();

        if (response.getSuccess() && response.getData() != null) {

            UserProfile.User user = response.getData().getUser();

            name.setText(getString(user.getName()));
            lastName.setText(getString(user.getLastName()));
            firstName.setText(getString(user.getUsername()));
            email.setText(getString(user.getEmail()));
            dob.setText(getString(user.getDob()));
            mobileNo.setText(getString(user.getMobileNo()));
            String r = getString(user.getGender());

            String d = getString(user.getDob());

            if (!TextUtils.isEmpty(d)) {

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                myCalendar.setTime(Utility.getStringInDate(d, myFormat));


            }

            if (r.toLowerCase().equalsIgnoreCase("female")) {

                female.setChecked(true);
                male.setChecked(false);
            } else {

                female.setChecked(false);
                male.setChecked(true);

            }

//            String url = "https://api.gamereel.io/storage/app/profile/";

            if (user.getPhoto().contains("http")) {

                ImageLoader.loadImageError(this, profilePhoto, user.getPhoto());
            } else {

                String url = "https://api.gamereel.io/storage/app/profile/" + user.getPhoto();

                ImageLoader.loadImageError(this, profilePhoto, url);

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save:

                if (validateFormInputFieldsAll()) {

                    callUpdateApi();

                } else {

                    showToast(getString(R.string.allfieldsrequired));

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void callUpdateApi() {


        final UpdateProfile request = new UpdateProfile();

        request.setEmail(email.getText().toString());
        request.setName(name.getText().toString());
        request.setLast_name(lastName.getText().toString());
        request.setUsername(firstName.getText().toString());
        request.setMobile_no(mobileNo.getText().toString());
        request.setDob(dob.getText().toString());

        RadioButton button = findViewById(groupLayout.getCheckedRadioButtonId());

        request.setGender(button.getText().toString().toLowerCase());

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().updateProfile(Preferences.INSTANCE.getAuthendicate(), request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String response) {

                        hideLoading();

                        showToast("Profile has been update successfully");

                    }

                    @Override
                    public void onError(Throwable e) {

                        handleErrorMsg(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                }));


    }

    @OnClick(R.id.profile_layout)
    public void onViewClicked() {

        requestCameraPermission();

    }


    private boolean validateFormInputFieldsAll() {

        boolean isValid = true;


        if (TextUtils.isEmpty(name.getText())) {

            TextInputUtil.setError(name, getString(R.string.firstnameempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(name);

        }

        if (TextUtils.isEmpty(firstName.getText())) {

            TextInputUtil.setError(firstName, getString(R.string.nameempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(firstName);

        }

        if (TextUtils.isEmpty(lastName.getText())) {

            TextInputUtil.setError(lastName, getString(R.string.lastnameempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(lastName);

        }

        if (TextUtils.isEmpty(dob.getText())) {

            TextInputUtil.setError(dob, getString(R.string.dobnameempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(dob);

        }

        if (TextUtils.isEmpty(mobileNo.getText())) {

            TextInputUtil.setError(mobileNo, getString(R.string.nobileempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(mobileNo);

        }


        String e = email.getText().toString();

        if (TextUtils.isEmpty(e)) {

            TextInputUtil.setError(email, getString(R.string.emailempty));
            isValid = false;

        } else if (!TextUtils.isEmpty(e) && !Patterns.EMAIL_ADDRESS.matcher(e).matches()) {

            TextInputUtil.setError(email, getString(R.string.emailempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(email);

        }


        return isValid;

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }


    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    @AfterPermissionGranted(REQUEST_PER_CAMERA)
    public void requestCameraPermission() {

        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            cameraAndGallary.selectImageProfileUpadte();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_PER_CAMERA, perms);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        cameraAndGallary.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {


        }

    }

    @Override
    public void onSelectFromGalleryResult(Bitmap bitmap) {

//        bitmap = ImageUtils.getResizedBitmap(bitmap, 240, 240);

        profilePhoto.setImageBitmap(bitmap);

        String encoded = ImageUtils.convertImageToBase64(bitmap);

        String type = "image/png";

//        String id = Preferences.INSTANCE.getUserId();

        ProfileUpdateRequest request = new ProfileUpdateRequest();

        request.setPhoto(encoded);
        request.setType(type);

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().updateProfileImageJson(Preferences.INSTANCE.getAuthendicate(), request)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    hideLoading();
                    showToast("Profile has been updated successfully");
                }, throwable -> {
                    hideLoading();
                    Log.e(TAG, "accept: " + throwable.getMessage());
                }));

    }

    @Override
    public void onVideo(File file) {

    }
}
