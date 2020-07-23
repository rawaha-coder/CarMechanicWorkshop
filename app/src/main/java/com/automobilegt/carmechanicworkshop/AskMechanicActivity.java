package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AskMechanicActivity extends AppCompatActivity {

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_mechanic);

        setTitle("Ask A Mechanic");

        TextView askMechanicTextView = findViewById(R.id.ask_mechanic_text_view);
        askMechanicTextView.setText(getString(R.string.askMechanicMessage));

        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void goAskMechanic(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://trk.justanswer.com/SHM4")));
    }
}