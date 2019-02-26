package com.trust.id.ui.register;

import com.orhanobut.hawk.Hawk;
import com.trust.id2.Utils.Utils;
import com.trust.id2.model.Profile;
import com.trust.id2.network.RestClient;
import com.trust.id2.network.req.ProfileUpdateBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInteractor implements RegisterContract.Interactor {

    private RegisterContract.InteractorOutput mListener;

    public RegisterInteractor(RegisterContract.InteractorOutput interactorOutput) {
        this.mListener = interactorOutput;
    }

    @Override
    public void logoutRequest(Map<String, String> sessionParameters) {

    }

    @Override
    public Profile getProfileData() {
        return Hawk.get(Utils.PROFILE);
    }

    @Override
    public void doProfileUpdate(String accessToken, String names, String familyName, String middleName, String birthday, String gender, String nationality) {
        Profile profile = getProfileData();
        Call<Void> update = RestClient.get().updateProfile("Bearer " + accessToken, "@!3011.6F0A.B190.8457!0001!294E.B0CD!0008!145D.F522.FFC3.439E", profile.getProfileId(), new ProfileUpdateBody(new ProfileUpdateBody.Attributes(names, familyName, middleName, gender, birthday, nationality)));

        update.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //TODO: replace profile data manually
                    Hawk.delete(Utils.INCOMPLETE);
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
        Hawk.put(Utils.INCOMPLETE, true);

    }
}
