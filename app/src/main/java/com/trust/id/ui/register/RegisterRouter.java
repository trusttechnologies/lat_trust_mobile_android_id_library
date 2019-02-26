package com.trust.id.ui.register;

import android.app.Activity;
import android.content.Intent;

import com.trust.id.ui.home.HomeActivity;
import com.trust.id.ui.result.ResultRouter;

public class RegisterRouter implements RegisterContract.Router {
    private Activity mActivity;

    public RegisterRouter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void backToRegister(boolean isSuccess) {

        mActivity.startActivity(new Intent(mActivity,HomeActivity.class));
        mActivity.finishAffinity();
       /* mActivity.setResult(isSuccess ? Activity.RESULT_OK : ResultRouter.RESULT_LOGOUT);
        mActivity.finish();*/
    }
}
