package com.instaclone.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.RegisterRequest;
import com.instaclone.utils.TextInputUtil;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.confirm_password)
    TextInputEditText confirmPassword;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;
    @BindView(R.id.sign_in)
    LinearLayout signIn;
    @BindView(R.id.cardView2)
    FrameLayout cardView2;
    @BindView(R.id.first_name)
    TextInputEditText firstName;
    @BindView(R.id.last_name)
    TextInputEditText lastName;
    @BindView(R.id.mobile_no)
    TextInputEditText mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);


    }

    public void onBack(View view) {

        onBackPressed();

    }

    @OnClick({R.id.sign_up_btn, R.id.sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_up_btn:

                if (validateFormInputFieldsAll()) {

                    callRegisterApi();

                } else {

                    showToast(getString(R.string.allfieldsrequired));

                }


                break;
            case R.id.sign_in:

                onBackPressed();

                break;
        }
    }

    private void callRegisterApi() {

        final RegisterRequest request = new RegisterRequest();

        request.setEmail(email.getText().toString());
        request.setLast_name(lastName.getText().toString());
        request.setMobile_no(mobileNo.getText().toString());
        request.setName(firstName.getText().toString());
        request.setPassword(password.getText().toString());
        request.setC_password(confirmPassword.getText().toString());

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().register(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String response) {

//                        handleLoginResponse(response);

                        hideLoading();

                        showToast("Successfully registered....");

                        onBackPressed();

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

    private boolean validateFormInputFieldsAll() {

        boolean isValid = true;

        String e = email.getText().toString();

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

        if (TextUtils.isEmpty(mobileNo.getText())) {

            TextInputUtil.setError(mobileNo, getString(R.string.nobileempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(mobileNo);

        }

        if (TextUtils.isEmpty(e)) {

            TextInputUtil.setError(email, getString(R.string.emailempty));
            isValid = false;

        } else if (!TextUtils.isEmpty(e) && !Patterns.EMAIL_ADDRESS.matcher(e).matches()) {

            TextInputUtil.setError(email, getString(R.string.emailempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(email);

        }

        if (TextUtils.isEmpty(password.getText())) {

            TextInputUtil.setError(password, getString(R.string.passwordempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(password);

        }

        if (TextUtils.isEmpty(confirmPassword.getText())) {

            TextInputUtil.setError(confirmPassword, getString(R.string.confirmpasswordempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(confirmPassword);

        }


        return isValid;

    }

}
