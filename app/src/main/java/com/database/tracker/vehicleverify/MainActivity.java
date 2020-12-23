package com.database.tracker.vehicleverify;

import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    LinearLayout simBut, currencyBut, vehicleBut;
    LinearLayout notifBut;
    ImageView onlineNotif;

    LinearLayout notificationMainId;
    String privacy, terms, moreApps;
    ProgressBar progressBar;
    // all the veriable here for the native adds

    private final String TAG = "MainActivity".getClass().getSimpleName();
    private NativeAd nativeAd;
    public InterstitialAd mInterstitialAd;

    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifBut = findViewById(R.id.notificationMainId);
        // onlineNotif=findViewById(R.id.onlineNotifID);
        notificationMainId = findViewById(R.id.notificationMainId);
        progressBar = findViewById(R.id.progressBar);
        simBut = findViewById(R.id.simLay);
        currencyBut = findViewById(R.id.currencyLay);
        vehicleBut = findViewById(R.id.vehicleLay);
        loadAd();

        notifBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notifI = new Intent(MainActivity.this, OptionsActivity.class);

                notifI.putExtra("privacy", getLink("privacy"));
                notifI.putExtra("terms", getLink("terms"));

                startActivity(notifI);
            }
        });


//Notification General topic subscription
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        //Registering a Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LiveTracker Notif Channel";
            String description = "All general notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(getString(R.string.notif_channel), name, importance);
            channel.setDescription(description);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }


        Paper.init(this);

       /* bottomMainAd = findViewById(R.id.adViewBottom);
        AdRequest adRequest = new AdRequest.Builder().build();
        bottomMainAd.loadAd(adRequest);
*/


        simBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent simI = new Intent(MainActivity.this, simActivity.class);
                simI.putExtra("link", getLink("simDB"));
                startActivity(simI);
            }
        });
        currencyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent currencyI = new Intent(MainActivity.this, converterActivity.class);
                currencyI.putExtra("link", getLink("currencyCon"));
                startActivity(currencyI);
            }
        });
        vehicleBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vehicleI = new Intent(MainActivity.this, vehicleActivity.class);
                startActivity(vehicleI);
            }
        });

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//
//                    case R.id.nav_notifications: {
//                        Intent notifI = new Intent(MainActivity.this, NotificationActivity.class);
//                        startActivity(notifI);
//                        break;
//                    }
//                    case R.id.nav_privacy: {
//                        Intent notifI = new Intent(Intent.ACTION_VIEW);
//                        notifI.setData(Uri.parse("https://livetrackerga.com/privacy.php"));
//                        startActivity(notifI);
//
//                        break;
//                    }
//                    case R.id.nav_terms: {
//                        Intent notifI = new Intent(Intent.ACTION_VIEW);
//                        notifI.setData(Uri.parse("https://livetrackerga.com/terms.php"));
//                        startActivity(notifI);
//
//                        break;
//                    }
//                    case R.id.nav_rateus: {
//                        Intent notifI = new Intent(Intent.ACTION_VIEW);
//                        notifI.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.database.org.livetracker&hl=en"));
//                        startActivity(notifI);
//
//                        break;
//                    }
//                }
//                mainLay.closeDrawer(Gravity.LEFT);
//                return true;
//            }
//        });

    }


    public static class MyApplication extends Application {


    }


    private String getLink(String str) {
        for (int i = 0; i < Utils.globalList.size(); i++) {
            if (Utils.globalList.get(i).linkName.equals(str)) {
                return Utils.globalList.get(i).getLinkUrl();
            }
        }
        return "";
    }


    @Override
    protected void onResume() {
        super.onResume();
        final dbHelper db = new dbHelper(MainActivity.this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Cursor c = db.getAllData();

                            int newCount = c.getCount();

                            Cursor countC = db.getCount();
                            countC.moveToFirst();

                            int prev = countC.getInt(0);

                            if (newCount > prev) {
                                onlineNotif.setVisibility(View.VISIBLE);
                            } else {
                                onlineNotif.setVisibility(View.INVISIBLE);
                            }

                        } catch (Exception e) {
                        }

                    }

                });

            }
        }, 0, 1000);


        String terms = Paper.book().read(Common.terms);
        if (terms == null) {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Important Annoucment")
                    .setMessage("To use LiveTracker app you need to accept the Terms of Use. Touch ACCEPT to agree the terms of Use and Privacy Policy.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Paper.book().write(Common.terms, "ok");
                        }

                    })
                    .setNegativeButton("Terms & Conditions", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://livetracker.ga/privacy.php"));
                            startActivity(browserIntent);
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round).setCancelable(false)
                    .show().setCancelable(false);
        }

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("You really want to Exit?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing done
                    }
                })
                .setIcon(R.mipmap.ic_launcher_round)
                .show();

    }

    // these are for the native add
    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_adds_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    private void loadAd() {
        progressBar.setVisibility(View.VISIBLE);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                progressBar.setVisibility(View.GONE);
                // method is for native adds
                loadNativeAd();
            }

            @Override
            public void onAdOpened() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdClicked() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdLeftApplication() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdClosed() {
                // method is for native adds
                loadNativeAd();
            }
        });
    }
}


