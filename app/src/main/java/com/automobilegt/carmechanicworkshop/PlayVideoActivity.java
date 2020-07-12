package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.automobilegt.carmechanicworkshop.model.CarVideoModel;

public class PlayVideoActivity extends AppCompatActivity {

    private CarVideoModel mVideo;
    private TextView videoTitleTextView;
    private TextView videoMessageTextView;
    private String videoLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mVideo = (CarVideoModel) getIntent().getSerializableExtra("video");
        videoLink = getIntent().getStringExtra("link");
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
                intent.putExtra("link", videoLink);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}