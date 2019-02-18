package com.trust.id.ui.login;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.trust.id.network.RestClientOauth;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor implements LoginContract.Interactor {
    private final LoginContract.InteractorOutputs mOutputs;

    public LoginInteractor(LoginContract.InteractorOutputs listener) {
        this.mOutputs = listener;
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
