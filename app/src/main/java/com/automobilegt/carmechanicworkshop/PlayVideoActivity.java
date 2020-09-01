package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.automobilegt.carmechanicworkshop.model.CarVideoModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class PlayVideoActivity extends AppCompatActivity {

    private AdView mAdView;

    private CarVideoModel mVideo;
    private TextView videoTitleTextView;
    private TextView videoMessageTextView;
    private int logoId;
    private String year;
    private String brandName;
    private String modelName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // get Intent values
        mVideo = (CarVideoModel) getIntent().getSerializableExtra("video");

        brandName = getIntent().getStringExtra("brand");
        modelName = getIntent().getStringExtra("model");
        year = getIntent().getStringExtra("year");
        logoId = getIntent().getIntExtra("logo", R.drawable.audi);

        videoTitleTextView = findViewById(R.id.video_title_text_view);
        videoMessageTextView = findViewById(R.id.video_message_text_view);

        videoTitleTextView.setText(mVideo.getVideoTitle());
        videoMessageTextView.setText(mVideo.getVideoDescription());

    }

    public void goPlayVideo(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mVideo.getVideoLink())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PlayVideoActivity.this, VideoListActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("brand", brandName);
                intent.putExtra("model", modelName);
                intent.putExtra("logo", logoId);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}