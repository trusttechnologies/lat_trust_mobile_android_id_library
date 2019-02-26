package com.trust.id.ui.home;

import android.app.Activity;
import android.content.Intent;

import com.trust.id.ui.login.LoginActivity;
import com.trust.id.ui.result.ResultRouter;

public class HomeRouter implements HomeContract.Router {
    private Activity mActivity;

    public HomeRouter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void backToRegister(boolean isSuccess) {
        mActivity.setResult(isSuccess ? Activity.RESULT_OK : ResultRouter.RESULT_LOGOUT);
        mActivity.finish();
    }

    @Override
    public void goToLogin() {
        mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
        mActivity.finishAffinity();
    }
}