package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class WarningLightMeaningActivity extends AppCompatActivity {

    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_light_meaning);

        setTitle("Warning Light Meaning");

        ImageView symbolImage = findViewById(R.id.symbolImageView);
        TextView symbolName = findViewById(R.id.symbolNameTextView);
        TextView symbolDescription = findViewById(R.id.symbolMeaningTextView);

        Intent intent = getIntent();
        String sDescription = intent.getStringExtra("description");
        String sName = intent.getStringExtra("name");
        int sImageId = intent.getIntExtra("image", 0);
        color = intent.getStringExtra("color");

        symbolName.setText(sName);
        symbolDescription.setText(sDescription);
        symbolImage.setImageResource(sImageId);

        // AdMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(WarningLightMeaningActivity.this, WarningLightListActivity.class);
            intent.putExtra("color", color);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }

    }
}