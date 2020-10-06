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

import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_YEAR;
import static com.automobilegt.carmechanicworkshop.util.Constants.FIRST_SERVER;
import static com.automobilegt.carmechanicworkshop.util.Constants.SECOND_SERVER;


public class CarYearActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Car>>, ListItemClickListener {

    private static final int CAR_YEAR_REQUEST_CODE = 301;
    public static final int YEAR_LOADER_ID = 3;
    private String brandName;
    private String modelName;
    private int logoId;
    private ProgressBar mProgressBar;
    private List<Car> mYearList;
    private String firstUrl;
    private String secondURL;
    private RVCarAdapter mAdapter;
    private TextView emptyView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_year);

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
        logoId = intent.getIntExtra("logo", R.drawable.audi);

        String brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        String modelFolder = modelName.toLowerCase();
        modelFolder = modelFolder.replaceAll("\\s","");

        setTitle(brandName + " " + modelName);
        emptyView = findViewById(R.id.emptyView);
        mYearList = new ArrayList<>();
        firstUrl = FIRST_SERVER + brandFolder + "/" + modelFolder + "/" + CAR_YEAR + ".json";
        secondURL = SECOND_SERVER + brandFolder + "/" + modelFolder + "/" + CAR_YEAR + ".json";

        LoaderManager.getInstance(this).restartLoader(YEAR_LOADER_ID, null, this);

        RecyclerView recyViewCarModelYear = findViewById(R.id.recy_view_model_year_activity);
        mAdapter = new RVCarAdapter(mYearList, this);
        recyViewCarModelYear.setAdapter(mAdapter);
        recyViewCarModelYear.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarModelYear.setLayoutManager(new LinearLayoutManager(this));

    }

    @NonNull
    @Override
    public Loader<List<Car>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CarLoader(this, firstUrl, secondURL, logoId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Car>> loader, List<Car> data) {
        mYearList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()){
            mYearList.addAll(data);
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
        Intent intentYear = new Intent(getApplicationContext(), VideoListActivity.class);
        intentYear.putExtra("year", mYearList.get(clickedItemIndex).getBrandModelYear());
        intentYear.putExtra("model", modelName);
        intentYear.putExtra("brand", brandName);
        intentYear.putExtra("logo", logoId);
        startActivityForResult(intentYear, CAR_YEAR_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(CarYearActivity.this, CarModelActivity.class);
            intent.putExtra("brand", brandName);
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
        if (requestCode == CAR_YEAR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                brandName = data.getStringExtra("brand");
                modelName = data.getStringExtra("model");
                logoId = data.getIntExtra("logo", R.drawable.audi);

            }
        }
    }
}