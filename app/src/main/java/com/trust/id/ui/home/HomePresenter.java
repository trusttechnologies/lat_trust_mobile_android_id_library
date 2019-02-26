package com.trust.id.ui.home;

import android.app.Activity;
import android.util.Log;

import com.trust.id2.Oauth2Helper;
import com.trust.id2.model.Profile;

import java.util.Calendar;
import java.util.Map;

public class HomePresenter implements HomeContract.Presenter, HomeContract.Interactor {
    private HomeContract.View mListener;
    private HomeContract.Interactor mInteractor;
    private HomeContract.Router mRouter;
    private boolean isLoading = false;
    private Oauth2Helper mHelper;

    public HomePresenter(Activity context) {
        this.mListener = (HomeContract.View) context;
        this.mInteractor = new HomeInteractor(this);
        this.mHelper = Oauth2Helper.getInstance().with();
        this.mRouter = new HomeRouter(context);
    }

    @Override
    public void onViewCreated() {
        Profile profile = mInteractor.getProfileData();
        if (profile != null) {
            mListener.displayProfileData(profile);
        }
    }

    @Override
    public void logoutButtonWasPressed() {
        mListener.showLoading("Cerrando Sesión");
        mInteractor.logoutRequest(mHelper.getSessionParameters());

    }

    @Override
    public void logoutSuccess() {
        mHelper.clear();
        mListener.dismissLoading();
        mRouter.goToLogin();
    }

    @Override
    public void logoutFailure() {
        //TODO: should ask for logout force (?)
        mHelper.clear();
        mListener.dismissLoading();
        mRouter.goToLogin();
    }



    @Override
    public void logoutRequest(Map<String, String> sessionParameters) {

    }

    @Override
    public Profile getProfileData() {
        return null;
    }


}
