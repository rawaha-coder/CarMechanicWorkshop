package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AskMechanicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_mechanic);

        setTitle("Ask A Mechanic");

        TextView askMechanicTextView = findViewById(R.id.ask_mechanic_text_view);
        askMechanicTextView.setText(getString(R.string.askMechanicMessage));
    }

    public void goAskMechanic(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://trk.justanswer.com/SHM4")));
    }
}