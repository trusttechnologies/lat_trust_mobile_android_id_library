package com.trust.id.ui.register;

import com.trust.id2.model.Profile;

import java.util.Map;

public interface RegisterContract {

    interface View {

        void displayProfileData(String name, String family, String middle, String birthday, int gender, String birthdayServer, String nationality);

        void showDatePickerDialog(int year, int month, int day);

        void showLoadingDialog();

        void dismissLoadingDialog();

        void showNamesError(boolean isError);

        void showFamilyNameError(boolean isError);

        void showMiddleNameError(boolean isError);

        void showBirthDayError(boolean isError);

        void showNatError(boolean isError);
    }

    interface Presenter {

        void onViewCreated();

        void birthdayButtonWasPressed();

        void onNextButtonPressed(String names, String familyName, String middleName, String birthday, int genderId, String selectedNat);

        void logoutWasRequested();
    }

    interface Interactor {

        void logoutRequest(Map<String, String> sessionParameters);

        Profile getProfileData();

        void doProfileUpdate(String accessToken, String names, String familyName, String middleName, String birthday, String gender, String nationality);

        void setIncomplete();
    }

    interface InteractorOutput {

        void onProfileUpdated();

        void onProfileUpdateFail();
    }

    interface Router {

        void backToRegister(boolean isSuccess);
    }
}
