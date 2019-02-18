package com.trust.id.ui.home;


import com.orhanobut.hawk.Hawk;
import com.trust.id.model.Profile;
import com.trust.id.network.RestClient;
import com.trust.id.network.request.ProfileUpdateBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInteractor implements HomeContract.Interactor {
    private HomeContract.InteractorOutput mListener;

    public HomeInteractor(HomeContract.InteractorOutput interactorOutput) {
        this.mListener = interactorOutput;
    }

    @Override
    public void logoutRequest(Map<String, String> sessionParameters) {

    }

    @Override
    public Profile getProfileData() {
        return Hawk.get("PROFILE");
    }

    @Override
    public void doProfileUpdate(String accessToken, String names, String familyName, String middleName, String birthday, String gender, String nationality) {
        Profile profile = getProfileData();
        Call<Void> update = RestClient.get().updateProfile("Bearer " + accessToken, profile.getProfileId(), new ProfileUpdateBody(new ProfileUpdateBody.Attributes(names, familyName, middleName, gender, birthday, nationality)));

        update.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //TODO: replace profile data manually
                    Hawk.delete("INCOMPLETE");
                    mListener.onProfileUpdated();
                } else {
                    mListener.onProfileUpdateFail();
                }
                //TODO: feedback
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mListener.onProfileUpdateFail();
                //TODO: feedback
            }
        });
    }

    @Override
    public void setIncomplete() {
        Hawk.put("INCOMPLETE", true);
    }
}
