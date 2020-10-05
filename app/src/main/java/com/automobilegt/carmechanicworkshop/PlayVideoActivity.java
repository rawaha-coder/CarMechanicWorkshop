package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.automobilegt.carmechanicworkshop.model.RepairVideo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class PlayVideoActivity extends AppCompatActivity {

    private RepairVideo mVideo;
    private int logoId;
    private String year;
    private String brandName;
    private String modelName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mVideo = (RepairVideo) getIntent().getSerializableExtra("video");

        brandName = getIntent().getStringExtra("brand");
        modelName = getIntent().getStringExtra("model");
        year = getIntent().getStringExtra("year");
        logoId = getIntent().getIntExtra("logo", R.drawable.audi);

        TextView videoTitleTextView = findViewById(R.id.video_title_text_view);
        TextView videoMessageTextView = findViewById(R.id.video_message_text_view);

        videoTitleTextView.setText(mVideo.getVideoTitle());
        videoMessageTextView.setText(mVideo.getVideoDescription());

    }

    public void goPlayVideo(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mVideo.getVideoLink())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(PlayVideoActivity.this, VideoListActivity.class);
            intent.putExtra("year", year);
            intent.putExtra("brand", brandName);
            intent.putExtra("model", modelName);
            intent.putExtra("logo", logoId);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}