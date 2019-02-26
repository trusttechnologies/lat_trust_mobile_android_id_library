package com.trust.id.ui.home;


import com.trust.id2.model.Profile;

import java.util.Map;

public interface HomeContract {
    interface View {

        void displayProfileData(Profile profile);

        void showLoading(String message);

        void dismissLoading();

    }

    interface Presenter {

        void onViewCreated();

        void logoutButtonWasPressed();

        void logoutSuccess();

        void logoutFailure();
    }

    interface Interactor {

        void logoutRequest(Map<String, String> sessionParameters);

        Profile getProfileData();

    }

    interface InteractorOutput {

        void onLogoutSuccess();

        void onLogoutError();

        void onLogoutFailure();
    }

    interface Router {

        void backToRegister(boolean isSuccess);

        void goToLogin();
    }
}
