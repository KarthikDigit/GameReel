package com.instaclone.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.instaclone.R;
import com.instaclone.base.BaseActivity;
import com.instaclone.dashboard.DashBoardActivity;
import com.instaclone.network.RetrofitAdapter;
import com.instaclone.network.request.LoginRequest;
import com.instaclone.network.response.BaseResponse;
import com.instaclone.network.response.LoginResponse;
import com.instaclone.preference.Preferences;
import com.instaclone.utils.TextInputUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.cardView2)
    FrameLayout cardView2;
    @BindView(R.id.sign_up)
    LinearLayout signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    public void moveToSignUpActivity(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }


    @OnClick({R.id.login, R.id.forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:

                if (validateFormInputFieldsAll()) {

                    callLoginApi();

                } else {

                    showToast(getString(R.string.allfieldsrequired));

                }

                break;
            case R.id.forgot_password:


                break;
        }
    }

    private void callLoginApi() {


        final LoginRequest request = new LoginRequest(email.getText().toString(), password.getText().toString());

        showLoading();

        disposable.add(RetrofitAdapter.getNetworkApiServiceClient().login(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onNext(BaseResponse<LoginResponse> response) {

                        handleLoginResponse(response);

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

    private void handleLoginResponse(BaseResponse<LoginResponse> response) {

        hideLoading();

        if (response.getSuccess()) {

            LoginResponse data = response.getData();


            String token = data.getTokenType() + " " + data.getAccessToken();
            Preferences.INSTANCE.putUserName(data.getName());
            Preferences.INSTANCE.putUserId(String.valueOf(data.getUserId()));
            Preferences.INSTANCE.putEmail(data.getEmail());
            Preferences.INSTANCE.putAccessToken(data.getAccessToken());
            Preferences.INSTANCE.putTokenType(data.getTokenType());
            Preferences.INSTANCE.putApiAccessToken(token);
            Preferences.INSTANCE.putUserLoggedInStatus(true);

            if (Preferences.INSTANCE.isUserLoggedIn()) {

                moveToNextActivity(response);

            } else {

                showToast("Something went wrong");

            }

        } else {

            showToast(response.getMessage());

        }

    }

    private void moveToNextActivity(BaseResponse<LoginResponse> response) {

        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);

    }

    private boolean validateFormInputFieldsAll() {

        boolean isValid = true;

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

        if (TextUtils.isEmpty(password.getText())) {

            TextInputUtil.setError(password, getString(R.string.passwordempty));
            isValid = false;

        } else {

            TextInputUtil.setDisableError(password);

        }

        return isValid;

    }
}
