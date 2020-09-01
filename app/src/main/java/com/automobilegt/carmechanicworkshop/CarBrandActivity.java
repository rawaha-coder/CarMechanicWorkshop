package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automobilegt.carmechanicworkshop.adapter.CarBrandRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrand;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.AUTOMOBILEGT_URL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;


public class CarBrandActivity extends AppCompatActivity {

    private AdView mAdView;
    private ArrayList<CarBrand> mCarBrandList;
    private RecyclerView recyViewCarBrand;
    private CarBrandRecyViewAdapter adapter;
    private ProgressBar mProgressBar;
    private RequestQueue mRequestQueue;

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);

        setTitle("Car Brand List");

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Fields initialization
        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mCarBrandList = new ArrayList<CarBrand>();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.document(COLLECTION + "/" + CAR_BRAND);
        mRequestQueue = Volley.newRequestQueue(this);

        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    AUTOMOBILEGT_URL + COLLECTION + "/" + CAR_BRAND + ".json", null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    mCarBrandList.add(new CarBrand(response.getString(i)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                            if(mCarBrandList != null){
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error != null){
                        getBrandList();
                    }
                }
            });
            mRequestQueue.add(jsonArrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyViewCarBrand = findViewById(R.id.recy_view_car_brand_activity);
        adapter = new CarBrandRecyViewAdapter(mCarBrandList);
        recyViewCarBrand.setAdapter(adapter);
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

    private void getBrandList(){
        mDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            List<String> list = (List<String>) documentSnapshot.get("list");
                            for(int i = 0; i < list.size(); i++){
                                mCarBrandList.add(new CarBrand(list.get(i)));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}