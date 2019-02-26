package com.trust.id;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.trust.id2.Oauth2Helper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initHawk();
        initAuthService();
    }

    private void initAuthService() {
        Oauth2Helper.init(this);
    }

    private void initHawk() {
        Hawk.init(this).build();
    }

}
