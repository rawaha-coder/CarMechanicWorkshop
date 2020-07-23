package com.automobilegt.carmechanicworkshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.automobilegt.carmechanicworkshop.adapter.VideoListRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarVideoModel;
import com.automobilegt.carmechanicworkshop.model.CarYearModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class VideoListActivity extends AppCompatActivity {

    private static final int VIDEO_REQUEST_CODE = 302;

    private AdView mAdView;
    private String folder;
    private int logoId;
    private String year;
    private ProgressBar mProgressBar;
    private String carYearLink;

    private CarYearModel mCarYear;
    private ArrayList<CarVideoModel> mVideoList;
    private VideoListRecyViewAdapter adapter;
    private RecyclerView recyViewCarVideoList;

    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();;
    private CollectionReference mCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mCarYear = (CarYearModel) getIntent().getSerializableExtra("year");
        carYearLink = getIntent().getStringExtra("link");
        folder = mCarYear.getFolderLink();
        year = mCarYear.getCarYear();
        logoId = mCarYear.getCarModelLogo();

        mCollectionReference = mFirebaseFirestore.collection(folder);

        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        setTitle(year + " Vidoes List");

        mVideoList = new ArrayList<CarVideoModel>();

        recyViewCarVideoList = findViewById(R.id.recy_view_video_list_activity);


        if(folder != null){
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                         String title =  snapshot.getString("title");
                         String description =  snapshot.getString("description");
                         String link =  snapshot.getString("link");

                         mVideoList.add(new CarVideoModel(title, description, link));

                 }
                    adapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }


        adapter = new VideoListRecyViewAdapter(mVideoList, logoId);
        recyViewCarVideoList.setAdapter(adapter);
        recyViewCarVideoList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarVideoList.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarVideoList.addOnItemTouchListener(new RecyclerItemClickListener(this,  recyViewCarVideoList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intentVideo = new Intent(getApplicationContext(), PlayVideoActivity.class);
                        intentVideo.putExtra("video", mVideoList.get(position));
                        intentVideo.putExtra("link", folder);
                        startActivityForResult(intentVideo, VIDEO_REQUEST_CODE);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VideoListActivity.this, CarYearActivity.class);
                intent.putExtra("link", carYearLink);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                folder = data.getStringExtra("link");

            }
        }
    }
}