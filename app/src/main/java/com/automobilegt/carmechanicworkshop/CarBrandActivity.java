package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
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

import com.automobilegt.carmechanicworkshop.adapter.CarBrandRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrand;
import com.automobilegt.carmechanicworkshop.util.CarBrandLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.AUTOMOBILEGT_URL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;

public class CarBrandActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CarBrand>> {

    private static final int BRAND_LOADER_ID = 1;
    private List<CarBrand> mCarBrandList;
    private CarBrandRecyViewAdapter mAdapter;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);

        setTitle("Car Brand List");

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Fields initialization
        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mCarBrandList = new ArrayList<>();


        LoaderManager.getInstance(this).restartLoader(BRAND_LOADER_ID, null, this);


        RecyclerView recyViewCarBrand = findViewById(R.id.recy_view_car_brand_activity);
        mAdapter = new CarBrandRecyViewAdapter(mCarBrandList);
        recyViewCarBrand.setAdapter(mAdapter);
        recyViewCarBrand.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarBrand.setLayoutManager(new LinearLayoutManager(this));

        recyViewCarBrand.addOnItemTouchListener(new RecyclerItemClickListener(this, recyViewCarBrand, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intentBrand = new Intent(getApplicationContext(), CarModelActivity.class);
                        intentBrand.putExtra("brand", mCarBrandList.get(position).getCarBrandName());
                        intentBrand.putExtra("logo", mCarBrandList.get(position).getCarBrandLogo());
                        startActivity(intentBrand);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

//    private void getBrandList(){
//        mDocumentReference.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists()){
//                            List<String> list = (List<String>) documentSnapshot.get("list");
//                            assert list != null;
//                            for(int i = 0; i < list.size(); i++){
//                                mCarBrandList.add(new CarBrand(list.get(i)));
//                            }
//                        }
//                        mAdapter.notifyDataSetChanged();
//                        mProgressBar.setVisibility(View.INVISIBLE);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }

    @NonNull
    @Override
    public Loader<List<CarBrand>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CarBrandLoader(this,AUTOMOBILEGT_URL + COLLECTION + "/" + CAR_BRAND + ".json");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CarBrand>> loader, List<CarBrand> carBrandList) {
        mCarBrandList.clear();
        mProgressBar.setVisibility(View.GONE);
        if (carBrandList != null && !carBrandList.isEmpty()){
            mCarBrandList.addAll(carBrandList);
            mAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "No brand found ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<CarBrand>> loader) {
        Toast.makeText(this, "No brand found ", Toast.LENGTH_SHORT).show();
    }
}