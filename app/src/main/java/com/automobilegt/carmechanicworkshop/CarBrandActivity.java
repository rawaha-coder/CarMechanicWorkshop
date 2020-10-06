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

import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;
import static com.automobilegt.carmechanicworkshop.util.Constants.FIRST_SERVER;
import static com.automobilegt.carmechanicworkshop.util.Constants.SECOND_SERVER;

public class CarBrandActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Car>>, ListItemClickListener {

    private static final int BRAND_LOADER_ID = 1;
    private List<Car> mCarList;
    private String firstUrl;
    private String secondURL;
    private RVCarAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView emptyView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);
        setTitle("Car Brand");

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
        emptyView = findViewById(R.id.emptyView);
        mCarList = new ArrayList<>();
        firstUrl = FIRST_SERVER + CAR_BRAND + ".json";
        secondURL = SECOND_SERVER  + CAR_BRAND + ".json";

        LoaderManager.getInstance(this).restartLoader(BRAND_LOADER_ID, null, this);

        RecyclerView recyViewCarBrand = findViewById(R.id.recy_view_car_brand_activity);
        mAdapter = new RVCarAdapter(mCarList, this);
        recyViewCarBrand.setAdapter(mAdapter);
        recyViewCarBrand.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarBrand.setLayoutManager(new LinearLayoutManager(this));
    }

    @NonNull
    @Override
    public Loader<List<Car>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CarLoader(this,firstUrl, secondURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Car>> loader, List<Car> carList) {
        mCarList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (carList != null && !carList.isEmpty()){
            mCarList.addAll(carList);
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
        Intent intentBrand = new Intent(getApplicationContext(), CarModelActivity.class);
        intentBrand.putExtra("brand", mCarList.get(clickedItemIndex).getBrandModelYear());
        intentBrand.putExtra("logo", mCarList.get(clickedItemIndex).getLogo());
        startActivity(intentBrand);
    }
}