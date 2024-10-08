package com.automobilegt.carmechanicworkshop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import static com.automobilegt.carmechanicworkshop.util.Constants.SETTING_PREFERENCES;


public class MainActivity extends AppCompatActivity {

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(SETTING_PREFERENCES, MODE_PRIVATE);
        boolean firstInstallation = mSharedPreferences.getBoolean("installed", false);
        if(!firstInstallation){
            firstInstall();
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void carMakerList(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), CarBrandActivity.class);
            startActivity(intent);
        }
    }

    public void dashboardLights(View view) {
        Intent intent = new Intent(getApplicationContext(), WarningLightActivity.class);
        startActivity(intent);
    }

    //First install called to accept the AULA and Privacy Policy
    public void firstInstall(){
        final Dialog dialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.first_install);
        final CheckBox checkBox = dialog.findViewById(R.id.agree_checkBox);
        Button startButton = dialog.findViewById(R.id.get_started_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkBox.setText(Html.fromHtml(getString(R.string.sample_aula_and_privacy_policy), HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            checkBox.setText(Html.fromHtml(getString(R.string.sample_aula_and_privacy_policy)));
        }
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    mSharedPreferences.edit().putBoolean("installed", true).apply();
                    dialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, R.string.accept_started, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}