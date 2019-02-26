package com.trust.id.ui.result;

import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.trust.id2.Utils.Utils;
import com.trust.id2.model.Profile;
import com.trust.id2.network.RestClient;
import com.trust.id2.network.RestClientOauth;
import com.trust.id2.network.req.FirebaseUpdateBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultInteractor implements ResultContract.Interactor {
    private ResultContract.InteractorOutputs mOutputs;

    public ResultInteractor(ResultContract.InteractorOutputs mOutputs) {
        this.mOutputs = mOutputs;
    }

    @Override
    public void getUserInfo(String accessToken) {
        Call<Profile> call = RestClientOauth.get().getUserInfo(accessToken);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile profile = response.body();
                    Hawk.put(Utils.PROFILE, profile);
                    mOutputs.onProfileLoaded(profile);
                } else {
                    mOutputs.onProfileDownloadFails();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                t.printStackTrace();
                mOutputs.onProfileDownloadFails();
            }
        });

    }

    @Override
    public void updateFirebaseToken(String accessToken, final String newToken) {

        Profile profile = Hawk.get(Utils.PROFILE);

        FirebaseUpdateBody.Profile profileAttribute = new FirebaseUpdateBody.Profile(newToken);
        Call<Void> updateCall = RestClient.get().updateFirebaseToken("Bearer " + accessToken, profile.getProfileId(), new FirebaseUpdateBody(profileAttribute));

        updateCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mOutputs.onFirebaseTokenUpdated(newToken.equals("empty"));
                } else {
                    mOutputs.onFirebaseTokenUpdateFails();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mOutputs.onFirebaseTokenUpdateFails();
            }
        });
    }

    @Override
    public void doLogout(Map<String, String> sessionParameters) {
        if (sessionParameters.size() > 2 && sessionParameters.containsKey("session_id") && sessionParameters.containsKey("session_state")) {
            Call<Object> logout = RestClientOauth.get().logout(sessionParameters.get("session_id"), sessionParameters.get("session_state"), "jumpitt.app://logout.id");
            logout.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    mOutputs.logoutSuccess();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    mOutputs.logoutSuccess();
                }
            });
        } else {
            mOutputs.logoutSuccess();
        }
    }

    @Override
    public void clearData() {
        Hawk.deleteAll();
    }
}
