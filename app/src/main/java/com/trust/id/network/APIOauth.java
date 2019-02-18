package com.trust.id.network;

import com.trust.id.model.Profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIOauth {
    @GET("userinfo")
    Call<Profile> getUserInfo(@Query("access_token") String accessToken);

    @GET("end_session")
    Call<Object> logout(@Query("session_id") String sessionId, @Query("session_state") String state, @Query("post_logout_redirect_uri") String redirect);

}
