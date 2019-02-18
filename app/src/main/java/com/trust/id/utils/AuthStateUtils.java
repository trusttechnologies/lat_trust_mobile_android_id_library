package com.trust.id.utils;

import com.orhanobut.hawk.Hawk;

import net.openid.appauth.AuthState;

import org.json.JSONException;

public class AuthStateUtils {
    public static final String HAWK_AUTH_STATE = "hawk_auth_state";

    public static void store(AuthState authState) {
        String authString = authState.jsonSerializeString();
        Hawk.put(HAWK_AUTH_STATE, authString);
    }

    public static AuthState restore() {
        if (!Hawk.contains(HAWK_AUTH_STATE)) return null;

        String data = Hawk.get(HAWK_AUTH_STATE);
        try {
            return AuthState.jsonDeserialize(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clear() {
        Hawk.delete(HAWK_AUTH_STATE);
    }
}