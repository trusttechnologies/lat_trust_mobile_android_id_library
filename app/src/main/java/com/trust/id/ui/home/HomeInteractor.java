package com.trust.id.ui.home;


import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.trust.id2.model.Profile;
import com.trust.id2.network.RestClientOauth;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInteractor implements HomeContract.Interactor {
    private HomeContract.Presenter mListener;

    public HomeInteractor(HomeContract.Presenter interactorOutput) {
        this.mListener = interactorOutput;
    }


    @Override
    public void logoutRequest(Map<String, String> sessionParameters) {

        if (sessionParameters.size() > 2 && sessionParameters.containsKey("session_id") && sessionParameters.containsKey("session_state")) {
            Call<Object> logout = RestClientOauth.get().logout(sessionParameters.get("session_id"), sessionParameters.get("session_state"), "jumpitt.app://logout.id");
            logout.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        Hawk.deleteAll();
                        mListener.logoutSuccess();
                    } else {
                        Hawk.deleteAll();
                        mListener.logoutFailure();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.d("Throwable", t.getMessage());
                    Hawk.deleteAll();
                    mListener.logoutFailure();
                }
            });
        } else {
            Hawk.deleteAll();
            mListener.logoutSuccess();
        }
    }

    @Override
    public Profile getProfileData() {
        return Hawk.get("PROFILE");
    }

}
