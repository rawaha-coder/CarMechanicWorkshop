package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automobilegt.carmechanicworkshop.adapter.RVVideoListAdapter;
import com.automobilegt.carmechanicworkshop.interfaces.ListItemClickListener;
import com.automobilegt.carmechanicworkshop.model.RepairVideo;
import com.automobilegt.carmechanicworkshop.util.RepairVideoLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.AUTOMOBILEGT_URL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_MODEL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_YEAR;
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;


public class VideoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RepairVideo>>, ListItemClickListener {

    private static final int VIDEO_REQUEST_CODE = 302;
    private static final int VIDEO_LOADER_ID = 4;
    private int logoId;
    private String year;
    private String brandName;
    private String modelName;
    private ProgressBar mProgressBar;
    private ArrayList<RepairVideo> mVideoList;
    private RVVideoListAdapter mAdapter;
    private String requestUrl;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        // AdMob initialization
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //fields initialization
        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        brandName = intent.getStringExtra("brand");
        modelName = intent.getStringExtra("model");
        year = intent.getStringExtra("year");
        logoId = intent.getIntExtra("logo", R.drawable.audi);
        setTitle(modelName + " " + year + " Repair Vidoes");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection(COLLECTION + "/" + CAR_BRAND + "/" + brandName + "/" + CAR_MODEL + "/" + modelName + "/" + CAR_YEAR + "/" + year);

        String brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        String modelFolder = modelName.toLowerCase();
        modelFolder = modelFolder.replaceAll("\\s","");

        requestUrl = AUTOMOBILEGT_URL + COLLECTION + "/" + brandFolder + "/" + modelFolder + "/" + year + ".json";
        mVideoList = new ArrayList<>();

        RecyclerView recyViewCarVideoList = findViewById(R.id.recy_view_video_list_activity);
        mAdapter = new RVVideoListAdapter(this, mVideoList, logoId);
        recyViewCarVideoList.setAdapter(mAdapter);
        recyViewCarVideoList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarVideoList.setLayoutManager(new LinearLayoutManager(this));

        LoaderManager.getInstance(this).restartLoader(VIDEO_LOADER_ID, null, this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VideoListActivity.this, CarYearActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get data from Intent
                year = data.getStringExtra("year");
                brandName = data.getStringExtra("brand");
                modelName = data.getStringExtra("model");
                logoId = data.getIntExtra("logo", R.drawable.audi);

            }
        }
    }

    @NonNull
    @Override
    public Loader<List<RepairVideo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RepairVideoLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<RepairVideo>> loader, List<RepairVideo> data) {
        mVideoList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()){
            mVideoList.addAll(data);
            mAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "No Year found ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<RepairVideo>> loader) {
        Toast.makeText(this, "No Video found ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intentVideo = new Intent(getApplicationContext(), PlayVideoActivity.class);
        intentVideo.putExtra("video", mVideoList.get(clickedItemIndex));
        intentVideo.putExtra("year", year);
        intentVideo.putExtra("model", modelName);
        intentVideo.putExtra("brand", brandName);
        intentVideo.putExtra("logo", logoId);
        startActivityForResult(intentVideo, VIDEO_REQUEST_CODE);
    }
}