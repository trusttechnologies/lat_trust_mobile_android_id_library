package com.trust.id.ui.result;

import android.content.Intent;

import com.trust.id.model.Profile;

import java.util.Map;

public interface ResultContract {
    interface View {

        void showUser(String givenName, boolean isMale);

        void showLoading();

        void dismissLoading();

        void onRequestFirebaseToken();

        void showLogoutNeeded();

        void showMessage(String message);
    }

    interface Presenter {

        void onViewCreated(Intent intent);

        void profileWasUpdated();

        void continueButtonWasPressed();

        void onDestroy();

        void onFirebaseTokenObtained(String token);

        void logoutWasRequested();
    }

    interface Interactor {

        void getUserInfo(String accessToken);

        void updateFirebaseToken(String accessToken, String newToken);

        void doLogout(Map<String, String> sessionParameters);

        void clearData();
    }

    interface InteractorOutputs {

        void onProfileDownloadFails();

        void onFirebaseTokenUpdated(boolean logoutNeeded);

        void onProfileLoaded(Profile profile);

        void onFirebaseTokenUpdateFails();

        void logoutSuccess();
    }

    interface Router {

        void backToWelcome();

        void goToMain();

        void goToRegister();
    }
}
