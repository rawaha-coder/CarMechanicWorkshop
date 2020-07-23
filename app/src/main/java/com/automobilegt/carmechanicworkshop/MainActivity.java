package com.automobilegt.carmechanicworkshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    private static final String SETTING_VALUES_ID = "setting_preferences";
    private static final String TAG = "EmailPassword";
    private boolean firstInstallation = true;

    //Firebase connection
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

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
        if(currentUser == null){
            signInAnonymously();
        }

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
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

    private void signInAnonymously() {
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Welcome to Car Mechanic Workshop App");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Connection to database failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }
}