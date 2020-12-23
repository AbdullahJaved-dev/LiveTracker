package com.database.tracker.vehicleverify;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.pheonix.org.watcher.R;

/**
 * Created by toshiba1 on 8/2/2019.
 */

public class initialize extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}
