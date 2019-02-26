package com.trust.id2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;


import com.trust.id2.Utils.AuthStateManager;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.ResponseTypeValues;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("StaticFieldLeak")
public class Oauth2Helper {
    private static final String TAG = Oauth2Helper.class.getSimpleName();
    private static final String SCOPES = "openid uma_protection profile profile.r profile.w address audit.r audit.w";
    private static final String EXTRA_ACR_KEY = "acr_values";
    private static final String EXTRA_ACR_VALUE = "autoidentify";
    private static final String REDIRECT_URI = "jumpitt.app://auth.id";

    private static final String BASE_URL = "https://api.autentia.id/oxauth/restv1/";
    private static final String CLIENT_ID = "@!3011.6F0A.B190.8457!0001!294E.B0CD!0008!145D.F522.FFC3.439E"; //"@!3011.6F0A.B190.8457!0001!294E.B0CD!0008!40E4.A0F9.9E5E.9E81";
    private static final String CLIENT_SECRET = "P2qr7PbPR3QxMMRIJwxqWO81";//"Eo4q2dqdlFQ6yxZP4zme3kHw";

    private static Oauth2Helper instance;
    private static AuthorizationService mAuthService;
    private static AuthorizationServiceConfiguration mServiceConfiguration;
    private static AuthState mAuthState;
    private static AuthStateManager mManager;

    /**
     * Singleton initialization. Must be called on Application class
     */
  public  static void init(Context context) {
        instance = new Oauth2Helper();

        mManager = AuthStateManager.getInstance(context);
        mServiceConfiguration =
                new AuthorizationServiceConfiguration(
                        Uri.parse(BASE_URL + "authorize"), // authorization endpoint
                        Uri.parse(BASE_URL + "token")); // token endpoint

        AppAuthConfiguration appAuthConfig = new AppAuthConfiguration.Builder()
                /*.setBrowserMatcher(new BrowserWhitelist(
                        VersionedBrowserMatcher.CHROME_CUSTOM_TAB, VersionedBrowserMatcher.CHROME_BROWSER, VersionedBrowserMatcher.SAMSUNG_CUSTOM_TAB, VersionedBrowserMatcher.SAMSUNG_BROWSER, VersionedBrowserMatcher.FIREFOX_CUSTOM_TAB, VersionedBrowserMatcher.FIREFOX_BROWSER))*/
                .build();
        mAuthService = new AuthorizationService(context, appAuthConfig);
    }

    /**
     * Return an Oauth2Helper instance and restore or create the mAuthState content
     *
     * @return
     */
    public static Oauth2Helper getInstance() {
        mAuthState = mManager.getCurrent();
        return instance;
    }

    /**
     * Make all the needed configurations. We need to do this every time we instantiate us Helper
     * because mAuthService is disposed every onDestroy calls
     *
     * @return Oauth2Helper instance fully configured
     */
    public Oauth2Helper with() {
        if (mAuthState == null) {
            mAuthState = new AuthState(mServiceConfiguration);
            mManager.replace(mAuthState);
        }
        return this;
    }


    /**
     * Initialize the Authorization request with ouath configuration
     *
     * @return new configuration for the AuthorizationRequest
     */
    private AuthorizationRequest getAuthorizationRequest() {
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put(EXTRA_ACR_KEY, EXTRA_ACR_VALUE);
        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        mServiceConfiguration,
                        CLIENT_ID,
                        ResponseTypeValues.CODE,
                        Uri.parse(REDIRECT_URI))
                        .setAdditionalParameters(extraParams)
                        .setScope(SCOPES);
        return authRequestBuilder.build();
    }

    /**
     * Do an AuthorizationRequest expecting an URI response
     *
     * @param context
     */
    public void doAuthorization(Context context,Activity activity) {

        Intent pendingIntent = new Intent(context, activity.getClass());

        mAuthService.performAuthorizationRequest(
                getAuthorizationRequest(),
                PendingIntent.getActivity(context, 0, pendingIntent, 0),
                PendingIntent.getActivity(context, 0, pendingIntent, 0));
    }

    /**
     * Return if is currently authorized
     *
     * @return
     */
    public boolean checkAuthState() {
        return mAuthState.isAuthorized();
    }

    /**
     * Check if access token is available. If it is expired do a refresh try
     * @param listener
     */
    public void requestFreshToken(final AuthListener.StateListener listener) {
        ClientAuthentication clientPost = new ClientSecretPost(CLIENT_SECRET);
        mAuthState.performActionWithFreshTokens(mAuthService, clientPost, (accessToken, idToken, e) -> {
            if (e == null) {
                Log.e(TAG, accessToken);
                mManager.replace(mAuthState);
                listener.onSuccess(accessToken, idToken);
            } else {
                Log.e(TAG, e.toJsonString());
                listener.onError(e.getMessage());
            }
        });
    }

    /**
     * Parse the response captured for the intent
     *
     * @param mainIntent intent with the uri scheme response
     */
    public void retrieveUriResponse(Intent mainIntent, final AuthListener.TokenExchangeListener listener) {
        AuthorizationResponse resp = AuthorizationResponse.fromIntent(mainIntent);
        AuthorizationException ex = AuthorizationException.fromIntent(mainIntent);

        if (resp != null) {
            mManager.updateAfterAuthorization(resp, ex);

            ClientAuthentication clientBasic = new ClientSecretBasic(CLIENT_SECRET);
            mAuthService.performTokenRequest(resp.createTokenExchangeRequest(), clientBasic, (tokenResponse, e) ->  {
                if (tokenResponse != null) {
                    mManager.updateAfterTokenResponse(tokenResponse, e);
                    listener.onSuccess(tokenResponse.accessToken);
                } else if (e != null) {
                    Log.e(TAG, e.toJsonString());
                    listener.onError(e.toJsonString());
                } else {
                    Log.e(TAG, "Error desconocido");
                    listener.onError("Error desconocido");
                }
            });
        } else if (ex != null) {
            Log.e(TAG, ex.toJsonString());
            listener.onError(ex.toJsonString());
        } else {
            Log.e(TAG, "Error desconocido");
            listener.onError("Error desconocido");
        }
    }

    /**
     * Get session data to do a logout
     * @return Map with session_id and session_state required on logout
     */
    public Map<String, String> getSessionParameters() {
        if (mAuthState.getLastAuthorizationResponse() != null)
            return mAuthState.getLastAuthorizationResponse().additionalParameters;
        else return null;
    }

    /**
     * Remove auth state data from local storage
     */
    public void clear() {
        mManager.clear();
        if (mAuthState != null)
            mAuthState = null;
    }


    public interface AuthListener {
        interface StateListener {

            void onSuccess(String accessToken, String idToken);

            void onError(String error);
        }

        interface TokenExchangeListener {
            void onSuccess(String accessToken);

            void onError(String error);
        }
    }
}
