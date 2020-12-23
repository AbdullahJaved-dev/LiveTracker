package com.database.tracker.vehicleverify;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

public class Myapp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        // initialized the method of audience netwok here
        AudienceNetworkAds.initialize(this);
    }
}
