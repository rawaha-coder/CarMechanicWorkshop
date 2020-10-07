package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
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

import com.automobilegt.carmechanicworkshop.adapter.RVCarAdapter;
import com.automobilegt.carmechanicworkshop.interfaces.ListItemClickListener;
import com.automobilegt.carmechanicworkshop.model.Car;
import com.automobilegt.carmechanicworkshop.util.CarLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_MODEL;
import static com.automobilegt.carmechanicworkshop.util.Constants.FIRST_SERVER;
import static com.automobilegt.carmechanicworkshop.util.Constants.SECOND_SERVER;

public class CarModelActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Car>>, ListItemClickListener {

    private static final int CAR_MODEL_REQUEST_CODE = 300;
    private static final int MODEL_LOADER_ID = 2;
    private String brandName;
    private int logoId;
    private ProgressBar mProgressBar;
    private List<Car> mModelList;
    private String firstUrl;
    private String secondURL;
    private RVCarAdapter mAdapter;
    private TextView emptyView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Intent intent = getIntent();
        brandName = intent.getStringExtra("brand");
        logoId = intent.getIntExtra("logo", R.drawable.audi);
        setTitle(brandName);
        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        String brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        emptyView = findViewById(R.id.emptyView);
        mModelList = new ArrayList<>();
        firstUrl = FIRST_SERVER + brandFolder + "/" + CAR_MODEL + ".json";
        secondURL = SECOND_SERVER + brandFolder + "/" + CAR_MODEL + ".json";

        LoaderManager.getInstance(this).restartLoader(MODEL_LOADER_ID, null, this);

        RecyclerView recyViewCarBrandModel = findViewById(R.id.recy_view_car_model_activity);
        mAdapter = new RVCarAdapter(mModelList, this);
        recyViewCarBrandModel.setAdapter(mAdapter);
        recyViewCarBrandModel.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarBrandModel.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAR_MODEL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                brandName = data.getStringExtra("brand");
                logoId = data.getIntExtra("logo", R.drawable.audi);
            }
        }
    }

    @NonNull
    @Override
    public Loader<List<Car>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CarLoader(this, firstUrl, secondURL, logoId );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Car>> loader, List<Car> modelList) {
        mModelList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (modelList != null && !modelList.isEmpty()){
            mModelList.addAll(modelList);
            mAdapter.notifyDataSetChanged();
        }else {
            emptyView.setVisibility(View.VISIBLE);
            adView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Car>> loader) {
        emptyView.setVisibility(View.VISIBLE);
        adView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intentModel = new Intent(getApplicationContext(), CarYearActivity.class);
        intentModel.putExtra("model", mModelList.get(clickedItemIndex).getBrandModelYear());
        intentModel.putExtra("brand", brandName);
        intentModel.putExtra("logo", logoId);
        startActivityForResult(intentModel, CAR_MODEL_REQUEST_CODE);
    }

}