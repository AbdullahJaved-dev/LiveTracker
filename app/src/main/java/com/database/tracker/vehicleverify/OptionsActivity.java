package com.database.tracker.vehicleverify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OptionsActivity extends AppCompatActivity {
    ImageView back;

    RelativeLayout notifi, privacy, terms, rate;
    String privacyy, termss, moreApps;
    String privacyL, termsL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        rate=findViewById(R.id.rateOptId);
        back = findViewById(R.id.backOptionsId);
        notifi = findViewById(R.id.notificationOptId);
        privacy = findViewById(R.id.privacyOptId);
        terms = findViewById(R.id.termsOptId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Query qd = null;
        FirebaseApp.initializeApp(this);
        qd = FirebaseDatabase.getInstance().getReference();
        //Toast.makeText(SignInActivity.this, "1st Q", Toast.LENGTH_SHORT).show();

        if(getIntent() != null){
            privacyy = getIntent().getStringExtra("privacy");
            termss = getIntent().getStringExtra("terms");
        }


//        ValueEventListener eventResListen = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    privacyL = dataSnapshot.child("links").child("privacy").child("link").getValue(String.class);
//                    termsL = dataSnapshot.child("links").child("terms").child("link").getValue(String.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        qd.addListenerForSingleValueEvent(eventResListen);


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent priI = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.database.org.livetracker"));
                startActivity(priI);
            }
        });

        notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noI = new Intent(OptionsActivity.this, NotificationActivity.class);
                startActivity(noI);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent priI = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyy));
                startActivity(priI);

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent priI = new Intent(Intent.ACTION_VIEW, Uri.parse(termss));
                startActivity(priI);
            }
        });
    }
}
