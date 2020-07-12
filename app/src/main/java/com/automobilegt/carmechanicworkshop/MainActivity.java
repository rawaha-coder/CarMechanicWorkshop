package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String SETTING_VALUES_ID = "setting_preferences";
    private boolean firstInstallation = true;
    private Button mCarRepairButton;
    private Button mCarDashboardButton;
    private Button mAskMechanicButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(SETTING_VALUES_ID, MODE_PRIVATE);
        firstInstallation = sharedPreferences.getBoolean("installed", false);

        if (!firstInstallation){
            Intent intentIstalled = getIntent();
            firstInstallation = intentIstalled.getBooleanExtra("installed", false);
            sharedPreferences.edit().putBoolean("installed", firstInstallation).apply();
            if(!firstInstallation){
                Intent intent = new Intent(MainActivity.this, FirstInstall.class);
                startActivity(intent);
            }
        }

    }

    public void carMakerList(View view) {
        Intent intent = new Intent(getApplicationContext(), CarBrandActivity.class);
        startActivity(intent);
    }

    public void dashboardLights(View view) {
        Intent intent = new Intent(getApplicationContext(), DashboardWarningLightActivity.class);
        startActivity(intent);
    }

    public void askMechanic(View view) {
        Intent intent = new Intent(getApplicationContext(), AskMechanicActivity.class);
        startActivity(intent);
    }
}