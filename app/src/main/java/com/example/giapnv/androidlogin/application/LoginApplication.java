package com.example.giapnv.androidlogin.application;

import android.app.Application;
import android.os.Debug;
import android.util.Log;

import com.example.giapnv.androidlogin.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;

public class LoginApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Required - Initialize the Parse SDK
        Parse.initialize(this, getString(R.string.parse_app_id),
                getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(String.valueOf(R.string.facebook_app_id));

        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
    }
}
