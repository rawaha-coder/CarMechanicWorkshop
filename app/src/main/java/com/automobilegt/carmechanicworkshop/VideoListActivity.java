package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.FIRST_SERVER;
import static com.automobilegt.carmechanicworkshop.util.Constants.SECOND_SERVER;


public class VideoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RepairVideo>>, ListItemClickListener {

    private static final int VIDEO_REQUEST_CODE = 302;
    private static final int VIDEO_LOADER_ID = 4;
    private int logoId;
    private String year;
    private String brandName;
    private String modelName;
    private ProgressBar mProgressBar;
    private ArrayList<RepairVideo> mVideoList;
    private String firstUrl;
    private String secondURL;
    private RVVideoListAdapter mAdapter;
    private TextView emptyView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        brandName = intent.getStringExtra("brand");
        modelName = intent.getStringExtra("model");
        year = intent.getStringExtra("year");
        logoId = intent.getIntExtra("logo", R.drawable.audi);
        setTitle(modelName + " " + year + " Repair Vidoes");

        String brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        String modelFolder = modelName.toLowerCase();
        modelFolder = modelFolder.replaceAll("\\s","");
        emptyView = findViewById(R.id.emptyView);
        mVideoList = new ArrayList<>();
        firstUrl = FIRST_SERVER + brandFolder + "/" + modelFolder + "/" + year + ".json";
        secondURL = SECOND_SERVER + brandFolder + "/" + modelFolder + "/" + year + ".json";

        RecyclerView recyViewCarVideoList = findViewById(R.id.recy_view_video_list_activity);
        mAdapter = new RVVideoListAdapter(this, mVideoList, logoId);
        recyViewCarVideoList.setAdapter(mAdapter);
        recyViewCarVideoList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarVideoList.setLayoutManager(new LinearLayoutManager(this));

        LoaderManager.getInstance(this).restartLoader(VIDEO_LOADER_ID, null, this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(VideoListActivity.this, CarYearActivity.class);
            intent.putExtra("brand", brandName);
            intent.putExtra("model", modelName);
            intent.putExtra("logo", logoId);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
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
        return new RepairVideoLoader(this, firstUrl, secondURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<RepairVideo>> loader, List<RepairVideo> data) {
        mVideoList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()){
            mVideoList.addAll(data);
            mAdapter.notifyDataSetChanged();
        }else {
            emptyView.setVisibility(View.VISIBLE);
            adView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<RepairVideo>> loader) {
        emptyView.setVisibility(View.VISIBLE);
        adView.setVisibility(View.INVISIBLE);
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