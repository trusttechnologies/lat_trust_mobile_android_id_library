package com.trust.id.ui.register;

import android.app.Activity;
import android.content.Context;
import android.support.design.theme.MaterialComponentsViewInflater;
import android.util.Log;

import com.trust.id.R;
import com.trust.id.utils.Util;
import com.trust.id2.Oauth2Helper;
import com.trust.id2.model.Profile;

import java.util.Calendar;

public class RegisterPresenter implements RegisterContract.Presenter,RegisterContract.InteractorOutput {

    private final Oauth2Helper mHelper;
    private RegisterContract.Router mRouter;
    private RegisterContract.View mView;
    private RegisterContract.Interactor mInteractor;

    public RegisterPresenter(Activity activity) {
        this.mView = (RegisterContract.View) activity;
        this.mInteractor = new RegisterInteractor(this);
        this.mRouter = new RegisterRouter(activity);
        this.mHelper = Oauth2Helper.getInstance();
    }
    @Override
    public void onViewCreated() {
        Profile profile = mInteractor.getProfileData();
        mInteractor.setIncomplete();

        if (profile != null) {
            String name = "";
            String family = "";
            String middle = "";
            String birthday = "";
            String birthdayServer = "";
            String nationality = "";
            int gender = R.id.radio_btn_male; // default

            if (profile.getGivenName() != null && !profile.getGivenName().equals("S/N")) {
                name = profile.getGivenName();
            }
            if (profile.getFamilyName() != null && !profile.getFamilyName().equals("S/A")) {
                family = profile.getFamilyName();
            }
            if (profile.getMiddleName() != null && !profile.getMiddleName().equals("S/A")) {
                middle = profile.getMiddleName();
            }
            if (profile.getBirthdate() != null && profile.getBirthdate().length() >= 8) {
                String date = profile.getBirthdate().substring(0, 8).replace("-", "");
                birthday = Util.formatBirthDate(date);
                birthdayServer = Util.formatBirthDateServer(Util.getDateLong(date));
            }

            if(profile.getAddress() != null && profile.getAddress().getCountry() != null){
                nationality = profile.getAddress().getCountry();
            }

            if (profile.getGender() != null) {
                gender = profile.isMale() ? R.id.radio_btn_male : R.id.radio_btn_female;
            }

            mView.displayProfileData(name, family, middle, birthday, gender, birthdayServer, nationality);
        }
    }

    @Override
    public void birthdayButtonWasPressed() {

        Profile profile = mInteractor.getProfileData();
        long birthday = 0;
        if (profile.getBirthdate() != null && profile.getBirthdate().length() >= 8) {
            String date = profile.getBirthdate().substring(0, 8).replace("-", "");
            birthday = Util.getDateLong(date);
        }
        Calendar calendar = Calendar.getInstance();

        if (birthday != 0) {
            calendar.setTimeInMillis(birthday);
        }
        mView.showDatePickerDialog(birthday != 0 ? calendar.get(Calendar.YEAR) : calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onNextButtonPressed(String names, String familyName, String middleName, String birthday, int genderId, String selectedNat) {
        boolean error = names.isEmpty() || familyName.isEmpty() || middleName.isEmpty() || birthday.isEmpty() || selectedNat.isEmpty();

        mView.showNamesError(names.isEmpty());
        mView.showFamilyNameError(familyName.isEmpty());
        mView.showMiddleNameError(middleName.isEmpty());
        mView.showBirthDayError(birthday.isEmpty());
        mView.showNatError(selectedNat.isEmpty());

        if (error) return;
        mView.showLoadingDialog();
        mHelper.requestFreshToken(new Oauth2Helper.AuthListener.StateListener() {
            @Override
            public void onSuccess(String accessToken, String idToken) {
                String gender = genderId == R.id.radio_btn_male ? "male" : "female";
                Log.d("TAG","refresh token: " + accessToken);
                Log.d("TAG","id token: " + idToken);
                //TODO check date y wea
                mInteractor.doProfileUpdate(accessToken, names, familyName, middleName, birthday, gender, selectedNat);
            }

            @Override
            public void onError(String error) {
                //TODO: feedback
                mView.dismissLoadingDialog();
            }
        });
    }

    @Override
    public void logoutWasRequested() {
        mRouter.backToRegister(false);
    }

    @Override
    public void onProfileUpdated() {
        mView.dismissLoadingDialog();
        mRouter.backToRegister(true);
    }

    @Override
    public void onProfileUpdateFail() {
        mView.dismissLoadingDialog();
    }
}
