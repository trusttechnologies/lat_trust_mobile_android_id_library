package com.trust.id.ui.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.trust.id.Oauth2Helper;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.InteractorOutputs {


    private final LoginContract.View mListener;
    //Configured but unused
    private final LoginContract.Interactor mInteractor;
    private LoginContract.Router mRouter;
    private Oauth2Helper mOauth;

    public LoginPresenter(Activity activity) {
        this.mListener = (LoginContract.View) activity;
        this.mInteractor = new LoginInteractor(this);
        this.mRouter = new LoginRouter(activity);
    }
    @Override
    public void authorizationWasRequested(Context context) {
        mOauth.doAuthorization(context);
    }
    @Override
    public void onDestroy() {

    }
    @Override
    public void onCreate() {
        this.mOauth = Oauth2Helper.getInstance().with();
        if (mOauth.checkAuthState()) {
            if (Hawk.contains("INCOMPLETE")) {
                mListener.showMessage("Logout necesario", "", true);
                mInteractor.doLogout(mOauth.getSessionParameters());
            } else {
                mOauth.requestFreshToken(new Oauth2Helper.AuthListener.StateListener() {
                    @Override
                    public void onSuccess(String accessToken, String idToken) {
                        mRouter.goToMain();
                    }

                    @Override
                    public void onError(String error) {
                        if (error != null)
                            mListener.showMessage("", "Sesión expirada", false);
                        Log.d("LoginPresenter","mView.showWelcome();");
                       // mView.showWelcome();
                    }
                });
            }
        } else {
            Log.d("LoginPresenter","mView.showWelcome();");
        }
    }


    @Override
    public void logoutSuccess() {
        mInteractor.clearData();
        mOauth.clear();
        onCreate();
    }
}
