package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(WarningLightMeaningActivity.this, ListDashboardWarningLightActivity.class);
                intent.putExtra("color", color);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}