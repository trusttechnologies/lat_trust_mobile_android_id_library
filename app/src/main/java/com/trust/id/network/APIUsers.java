package com.trust.id.network;

import com.trust.id.network.request.FirebaseUpdateBody;
import com.trust.id.network.request.ProfileUpdateBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface APIUsers {
    @Headers({"Content-Type:application/vnd.autentia.profile+json"})
    @PATCH("v1/{profile_id}")
    Call<Void> updateProfile(@Header("Authorization") String header, @Path("profile_id") String profileId, @Body ProfileUpdateBody body);

    @Headers({"Content-Type:application/vnd.autentia.profile+json"})
    @PATCH("v1/{profile_id}")
    Call<Void> updateFirebaseToken(@Header("Authorization") String header, @Path("profile_id") String profileId, @Body FirebaseUpdateBody body);
}
