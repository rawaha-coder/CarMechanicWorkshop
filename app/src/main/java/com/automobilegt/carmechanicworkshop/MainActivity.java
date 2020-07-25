package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private static final int FIRST_INSTAL_REQUEST_CODE = 001;

    private AdView mAdView;

    private static final String SETTING_VALUES_ID = "setting_preferences";
    private static final String TAG = "EmailPassword";
    SharedPreferences sharedPreferences;
    private boolean firstInstallation = false;

    //Firebase connection
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences(SETTING_VALUES_ID, MODE_PRIVATE);
        firstInstallation = sharedPreferences.getBoolean("installed", false);

        if(!firstInstallation){
                Log.d(TAG, "go to firstinstal activity");
                Intent intent = new Intent(MainActivity.this, FirstInstall.class);
                startActivityForResult(intent, FIRST_INSTAL_REQUEST_CODE);
        }


        if(currentUser == null){
            signInAnonymously();
        }

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == FIRST_INSTAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get String data from Intent
                firstInstallation = data.getBooleanExtra("installed", true);
                sharedPreferences.edit().putBoolean("installed", firstInstallation).apply();
            }
        }
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