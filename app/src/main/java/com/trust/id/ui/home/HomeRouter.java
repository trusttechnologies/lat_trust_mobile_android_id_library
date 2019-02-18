package com.trust.id.ui.home;

import android.app.Activity;

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
}