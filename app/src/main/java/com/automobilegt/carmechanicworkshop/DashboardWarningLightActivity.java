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

public class DashboardWarningLightActivity extends AppCompatActivity {

    private AdView mAdView;
    private String color;

    public void warningLightSymbols(View view) {
        if(view.getTag().toString().equals("red")){
            Intent intent = new Intent(getApplicationContext(), ListDashboardWarningLightActivity.class );
            color = "Red";
            intent.putExtra("color", color);
            startActivity(intent);
        }else if(view.getTag().toString().equals("orange")) {
            Intent intent = new Intent(getApplicationContext(), ListDashboardWarningLightActivity.class );
            color = "Orange";
            intent.putExtra("color", color);
            startActivity(intent);
        }else if(view.getTag().toString().equals("green")) {
            Intent intent = new Intent(getApplicationContext(), ListDashboardWarningLightActivity.class );
            color = "Green";
            intent.putExtra("color", color);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_warning_light);

        setTitle("Warning Light Color");

        TextView textView = findViewById(R.id.bashboard_warning_text_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(getString(R.string.dashboard_warning_light_text), HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(getString(R.string.dashboard_warning_light_text)));
        }

        // AdMob
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}