package com.trust.id.ui.home;

import android.app.Activity;

import com.trust.id.Oauth2Helper;
import com.trust.id.model.Profile;

import java.util.Calendar;

public class HomePresenter implements HomeContract.Presenter, HomeContract.InteractorOutput {
    private final Oauth2Helper mHelper;
    private HomeContract.Router mRouter;
    private HomeContract.View mView;
    private HomeContract.Interactor mInteractor;

    public HomePresenter(Activity activity) {
        this.mView = (HomeContract.View) activity;
        this.mInteractor = new HomeInteractor(this);
        this.mRouter = new HomeRouter(activity);
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
            String gender = ""; // default
            gender = profile.getBirthdate();
            if (profile.getGivenName() != null && !profile.getGivenName().equals("S/N")) {
                name = profile.getGivenName();
            }
            if (profile.getFamilyName() != null && !profile.getFamilyName().equals("S/A")) {
                family = profile.getFamilyName();
            }
            if (profile.getMiddleName() != null && !profile.getMiddleName().equals("S/A")) {
                middle = profile.getMiddleName();
            }
         /*   if (profile.getBirthdate() != null && profile.getBirthdate().length() >= 8) {
                String date = profile.getBirthdate().substring(0, 8).replace("-", "");
                birthday = Utils.formatBirthDate(date);
                birthdayServer = Utils.formatBirthDateServer(Utils.getDateLong(date));
            }*/

            if (profile.getAddress() != null && profile.getAddress().getCountry() != null) {
                nationality = profile.getAddress().getCountry();
            }

        /*    if (profile.getGender() != null) {
                gender = profile.isMale() ? R.id.radio_btn_male : R.id.radio_btn_female;
            }*/

            mView.displayProfileData(name, family, middle, birthday, gender, birthdayServer, nationality);
        }
    }

    @Override
    public void birthdayButtonWasPressed() {
        Profile profile = mInteractor.getProfileData();
      /*  long birthday = 0;
        if (profile.getBirthdate() != null && profile.getBirthdate().length() >= 8) {
            String date = profile.getBirthdate().substring(0, 8).replace("-", "");
            birthday = Utils.getDateLong(date);
        }
        Calendar calendar = Calendar.getInstance();

        if (birthday != 0) {
            calendar.setTimeInMillis(birthday);
        }*/
       // mView.showDatePickerDialog(birthday != 0 ? calendar.get(Calendar.YEAR) : calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                String gender = "female";

                //TODO check date y wea
                mInteractor.doProfileUpdate(accessToken, names, familyName, middleName, birthday, gender, selectedNat);
            }

            @Override
            public void onError(String error) {
                //TODO: feedback
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
