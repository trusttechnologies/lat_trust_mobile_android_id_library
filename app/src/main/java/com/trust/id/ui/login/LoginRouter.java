package com.trust.id.ui.login;

import android.app.Activity;
import android.content.Intent;

import com.trust.id.ui.register.RegisterActivity;
import com.trust.id.ui.home.HomeActivity;

public class LoginRouter implements LoginContract.Router {
    private Activity mActivity;

    public LoginRouter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void goToMain() {
        Intent main = new Intent(mActivity, HomeActivity.class);
        if (mActivity.getIntent().getExtras() != null && mActivity.getIntent().getExtras().containsKey("id")) {
            main.putExtras(mActivity.getIntent());
        }
        mActivity.startActivity(main);
        mActivity.finishAffinity();
    }

    @Override
    public void goToRegister() {
        Intent main = new Intent(mActivity, RegisterActivity.class);
        if (mActivity.getIntent().getExtras() != null && mActivity.getIntent().getExtras().containsKey("id")) {
            main.putExtras(mActivity.getIntent());
        }
        mActivity.startActivity(main);
        mActivity.finishAffinity();
    }
}
