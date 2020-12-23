package com.database.tracker.vehicleverify;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    ImageView imv;
    Button err;
    List<Links> linksList = new ArrayList<Links>();
    List<Links> locallinksList = new ArrayList<Links>();
    String notifLink = null;
    // all the variables used for the addoepn



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            notifLink = getIntent().getExtras().getString("link");
        } catch (Exception e) {

        }
        if (notifLink == null) {
            imv = findViewById(R.id.splachIcon);
            err = findViewById(R.id.errMsg);
            Paper.init(this);
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {

                            if (isNetworkAvailable()) {
                                Query qd;
                                FirebaseApp.initializeApp(SplashActivity.this);
                                qd = FirebaseDatabase.getInstance().getReference();
                                //Toast.makeText(SignInActivity.this, "1st Q", Toast.LENGTH_SHORT).show();

                                ValueEventListener eventResListen = new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            String run = dataSnapshot.child("ver").getValue(String.class);
                                            if (run.equalsIgnoreCase("han")) {

                                                getDataFromFirebase();
                                                String terms = Paper.book().read(Common.terms);
                                                Intent toMainInt;
                                                if (terms == null) {
                                                    toMainInt = new Intent(SplashActivity.this, privacyActivity.class);
                                                } else {
                                                    toMainInt = new Intent(SplashActivity.this, MainActivity.class);

                                                }
                                                startActivity(toMainInt);
                                                finish();
                                            } else {
                                                Toast.makeText(SplashActivity.this, "Access Restricted!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                };
                                qd.addListenerForSingleValueEvent(eventResListen);

                            } else {
                                err.setAlpha(1);
                                final Timer timer = new Timer();
                                timer.scheduleAtFixedRate(new TimerTask() {

                                    @Override
                                    public void run() {
                                        if (isNetworkAvailable()) {
                                            Query qd = null;
                                            FirebaseApp.initializeApp(SplashActivity.this);
                                            qd = FirebaseDatabase.getInstance().getReference();
                                            //Toast.makeText(SignInActivity.this, "1st Q", Toast.LENGTH_SHORT).show();

                                            ValueEventListener eventResListen = new ValueEventListener() {

                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        String run = dataSnapshot.child("run").getValue(String.class);
                                                        if (run.equalsIgnoreCase("true")) {
                                                            String terms = Paper.book().read(Common.terms);
                                                            if (terms == null) {
                                                                Intent toMainInt = new Intent(SplashActivity.this, privacyActivity.class);
                                                                startActivity(toMainInt);
                                                                finish();
                                                            } else {
                                                                Intent toMainInt = new Intent(SplashActivity.this, MainActivity.class);
                                                                startActivity(toMainInt);
                                                                finish();
                                                                timer.cancel();
                                                            }
                                                        } else {
                                                            Toast.makeText(SplashActivity.this, "Access Restricted!", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            };
                                            qd.addListenerForSingleValueEvent(eventResListen);
                                        }
                                    }

                                }, 0, 1000);
                            }
                        }

                    }, 800
            );
        } else {
            Intent broesInt = new Intent(Intent.ACTION_VIEW, Uri.parse(notifLink));
            startActivity(broesInt);
            finish();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getDataFromFirebase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("links");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (linksList.size() > 0)
                    return;

                Iterable<DataSnapshot> list = dataSnapshot.getChildren();
                while (list.iterator().hasNext()) {
                    DataSnapshot ds = list.iterator().next();
                    String yt = ds.getKey();
                    String yt1 = ds.getValue().toString();
                    yt1 = yt1.replace("{link=", "");
                    yt1 = yt1.replace("}", "");
                    linksList.add(new Links(yt, yt1));

                }

                Utils.globalList = linksList;

                //writeDataToFile();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SplashActivity.this, "Failed!.\nPlease restart the app", Toast.LENGTH_LONG).show();
                getDataFromFirebase();
            }
        };

        mDatabase.addValueEventListener(postListener);

    }


}
