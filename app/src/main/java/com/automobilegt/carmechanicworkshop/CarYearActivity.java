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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.automobilegt.carmechanicworkshop.adapter.RVCarAdapter;
import com.automobilegt.carmechanicworkshop.interfaces.ListItemClickListener;
import com.automobilegt.carmechanicworkshop.model.Car;
import com.automobilegt.carmechanicworkshop.util.CarLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.AUTOMOBILEGT_URL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_MODEL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_YEAR;
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;


public class CarYearActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Car>>, ListItemClickListener {

    private static final int CAR_YEAR_REQUEST_CODE = 301;
    public static final int YEAR_LOADER_ID = 3;

    private String brandName;
    private String modelName;
    private int logoId;
    private ProgressBar mProgressBar;
    private List<Car> mYearList;
    private RVCarAdapter mAdapter;
    private String requestUrl;
    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_year);

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //fields initialization
        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        brandName = intent.getStringExtra("brand");
        modelName = intent.getStringExtra("model");
        logoId = intent.getIntExtra("logo", R.drawable.audi);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.document(COLLECTION + "/" + CAR_BRAND + "/" + brandName + "/" + CAR_MODEL + "/" + modelName + "/" + CAR_YEAR);

        String brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        String modelFolder = modelName.toLowerCase();
        modelFolder = modelFolder.replaceAll("\\s","");

        setTitle(brandName + " " + modelName);

        mRequestQueue = Volley.newRequestQueue(this);
        mYearList = new ArrayList<>();
        requestUrl = AUTOMOBILEGT_URL + COLLECTION + "/" + brandFolder + "/" + modelFolder + "/" + CAR_YEAR + ".json";

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
        return new CarLoader(this, requestUrl, logoId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Car>> loader, List<Car> data) {
        mYearList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()){
            mYearList.addAll(data);
            mAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "No Year found ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Car>> loader) {
        Toast.makeText(this, "No Year found ", Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CarYearActivity.this, CarModelActivity.class);
                intent.putExtra("brand", brandName);
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
        if (requestCode == CAR_YEAR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get String data from Intent
                brandName = data.getStringExtra("brand");
                modelName = data.getStringExtra("model");
                logoId = data.getIntExtra("logo", R.drawable.audi);

            }
        }
    }


//        recyViewCarModelYear.addOnItemTouchListener(new RecyclerItemClickListener(this, recyViewCarModelYear,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        Intent intentYear = new Intent(getApplicationContext(), VideoListActivity.class);
//                        intentYear.putExtra("year", mYearList.get(position).getBrandModelYear());
//                        intentYear.putExtra("model", modelName);
//                        intentYear.putExtra("brand", brandName);
//                        intentYear.putExtra("logo", logoId);
//                        startActivityForResult(intentYear, CAR_YEAR_REQUEST_CODE);
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );
}