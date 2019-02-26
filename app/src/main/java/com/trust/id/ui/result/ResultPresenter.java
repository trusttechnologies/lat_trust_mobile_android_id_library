package com.trust.id.ui.result;

import android.app.Activity;
import android.content.Intent;

import com.trust.id2.Oauth2Helper;
import com.trust.id2.model.Profile;

import org.apache.commons.lang3.text.WordUtils;

public class ResultPresenter implements ResultContract.Presenter,ResultContract.InteractorOutputs{
    private ResultContract.View mView;
    private ResultContract.Interactor mInteractor;
    private ResultContract.Router mRouter;
    private Oauth2Helper mHelper;

    public ResultPresenter(Activity activity) {
        this.mView = (ResultContract.View) activity;
        this.mInteractor = new ResultInteractor(this);
        this.mRouter = new ResultRouter(activity);
        this.mHelper = Oauth2Helper.getInstance().with();
    }

    @Override
    public void onViewCreated(Intent intent) {
        mView.showLoading();
        if (intent != null && intent.getData() != null) {
            mHelper.retrieveUriResponse(intent, new Oauth2Helper.AuthListener.TokenExchangeListener() {
                @Override
                public void onSuccess(String accessToken) {
                    mInteractor.getUserInfo(accessToken);
                }

                @Override
                public void onError(String error) {
                    mView.dismissLoading();
                    mView.showLogoutNeeded();
                }
            });

        } else {
            mInteractor.clearData();
            mHelper.clear();
            mRouter.backToWelcome();
        }
    }


    @Override
    public void profileWasUpdated() {
        mView.showLoading();
        mHelper.requestFreshToken(new Oauth2Helper.AuthListener.StateListener() {
            @Override
            public void onSuccess(String accessToken, String idToken) {
                mInteractor.getUserInfo(accessToken);
            }
            @Override
            public void onError(String error) {
                mView.dismissLoading();
                mView.showLogoutNeeded();
            }
        });
    }

    @Override
    public void continueButtonWasPressed() {
        mView.showLoading();
        mView.onRequestFirebaseToken();
    }

    @Override
    public void onDestroy() {
        mInteractor = null;
        mRouter = null;
    }

    @Override
    public void onFirebaseTokenObtained(final String token) {
        mHelper.requestFreshToken(new Oauth2Helper.AuthListener.StateListener() {
            @Override
            public void onSuccess(String accessToken, String idToken) {
                mInteractor.updateFirebaseToken(accessToken, token);
            }

            @Override
            public void onError(String error) {
                mView.dismissLoading();
                mView.showLogoutNeeded();
            }
        });
    }

    @Override
    public void logoutWasRequested() {
        mView.showLoading();
        mHelper.requestFreshToken(new Oauth2Helper.AuthListener.StateListener() {
            @Override
            public void onSuccess(String accessToken, String idToken) {
                mInteractor.updateFirebaseToken(accessToken, "empty");
            }

            @Override
            public void onError(String error) {
                mView.dismissLoading();
                mView.showLogoutNeeded();
            }
        });
    }

    @Override
    public void onProfileDownloadFails() {
        mView.dismissLoading();
        mView.showLogoutNeeded();
    }

    @Override
    public void onProfileLoaded(Profile profile) {
        if (profile.missingData()) {
            mRouter.goToRegister();
        } else {
            mRouter.goToMain();
            mView.showUser(WordUtils.capitalize(profile.getFullName().toLowerCase(), ' '), profile.isMale());
        }
        mView.dismissLoading();
    }

    @Override
    public void onFirebaseTokenUpdated(boolean logoutNeeded) {
        if (logoutNeeded) {
            mInteractor.doLogout(mHelper.getSessionParameters());
        } else {
            mView.dismissLoading();
            mRouter.goToMain();
        }
    }

    @Override
    public void onFirebaseTokenUpdateFails() {
        mView.dismissLoading();
        mView.showMessage("No se pudieron configurar las notificaciones. Reintente");
    }

    @Override
    public void logoutSuccess() {
        mInteractor.clearData();
        mView.dismissLoading();
        mHelper.clear();
        mRouter.backToWelcome();
    }
}
