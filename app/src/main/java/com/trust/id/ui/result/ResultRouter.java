package com.trust.id.ui.result;

import android.app.Activity;
import android.content.Intent;

import com.trust.id.ui.home.HomeActivity;
import com.trust.id.ui.login.LoginActivity;
import com.trust.id.ui.register.RegisterActivity;

public class ResultRouter implements ResultContract.Router {
    public static final int REGISTER_REQUEST = 1000;
    public static final int RESULT_LOGOUT = -100;
    private Activity mActivity;

    public ResultRouter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void backToWelcome() {
        Intent welcome = new Intent(mActivity, LoginActivity.class);
        welcome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(welcome);
        mActivity.finishAffinity();
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(mActivity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        mActivity.finishAffinity();
    }

    @Override
    public void goToRegister() {
        Intent intent = new Intent(mActivity, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        mActivity.finishAffinity();
        //    Intent intent = new Intent(mActivity, RegisterActivity.class);
        //    mActivity.startActivityForResult(intent, REGISTER_REQUEST);
    }

}
