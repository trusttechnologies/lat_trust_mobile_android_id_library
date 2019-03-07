package com.trust.id.ui.login;

import android.content.Context;

import java.util.Map;

public interface LoginContract {
    interface View {

        void showWelcome();

        void showMessage(String title, String message, boolean isError);
    }

    interface Presenter {
        void onCreate();

        void authorizationWasRequested(Context context);

        void onDestroy();

        void register();
    }

    interface Interactor {
        void doLogout(Map<String, String> sessionParameters);

        void clearData();

        //Unused on this module

    }

    interface InteractorOutputs {
        void logoutSuccess();

        //Unused on this module

    }

    interface Router {
        void goToMain();

        void goToRegister();
    }
}
