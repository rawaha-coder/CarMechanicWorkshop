package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class WarningLightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_light);

        setTitle("Warning Light Color");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        TextView textView = findViewById(R.id.dashboard_warning_text_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(getString(R.string.dashboard_warning_light_text), HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(getString(R.string.dashboard_warning_light_text)));
        }
    }

    public void warningLightSymbols(View view) {
        String color;
        if(view.getTag().toString().equals("red")){
            Intent intent = new Intent(getApplicationContext(), WarningLightListActivity.class );
            color = "Red";
            intent.putExtra("color", color);
            startActivity(intent);
        }else if(view.getTag().toString().equals("orange")) {
            Intent intent = new Intent(getApplicationContext(), WarningLightListActivity.class );
            color = "Orange";
            intent.putExtra("color", color);
            startActivity(intent);
        }else if(view.getTag().toString().equals("green")) {
            Intent intent = new Intent(getApplicationContext(), WarningLightListActivity.class );
            color = "Green";
            intent.putExtra("color", color);
            startActivity(intent);
        }
    }
}