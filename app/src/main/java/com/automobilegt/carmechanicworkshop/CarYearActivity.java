package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.automobilegt.carmechanicworkshop.adapter.CarYearRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarYear;
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
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_MODEL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_YEAR;
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;


public class CarYearActivity extends AppCompatActivity {

    private static final int CAR_YEAR_REQUEST_CODE = 301;
    private static final String TAG = "caryearactivity";

    private AdView mAdView;

    private String brandName;
    private String modelName;
    private int logoId;
    private String brandFolder;
    private String modelFolder;

    private ProgressBar mProgressBar;

    private ArrayList<CarYear> mCarYearList;
    private CarYearRecyViewAdapter adapter;
    private RecyclerView recyViewCarModelYear;

    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_year);

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //fields initialization
        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        brandName = intent.getStringExtra("brand");
        modelName = intent.getStringExtra("model");
        logoId = intent.getIntExtra("logo", R.drawable.audi);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.document(COLLECTION + "/" + CAR_BRAND + "/" + brandName + "/" + CAR_MODEL + "/" + modelName + "/" + CAR_YEAR);

        brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        modelFolder = modelName.toLowerCase();
        modelFolder = modelFolder.replaceAll("\\s","");


        setTitle(modelName + " Years List");

        mRequestQueue = Volley.newRequestQueue(this);
        mCarYearList = new ArrayList<CarYear>();

        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    AUTOMOBILEGT_URL + COLLECTION + "/" + brandFolder + "/" + modelFolder + "/" + CAR_YEAR + ".json", null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    mCarYearList.add(new CarYear(response.getString(i), logoId));
                                    Log.d(TAG, response.getString(i) + " from AutomobileGt");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, e.getMessage().toString());
                                }
                            }
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "Hide progressbar");
                            if(mCarYearList != null){
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.getMessage() + " from AutomobileGt: " + AUTOMOBILEGT_URL + COLLECTION + "/" + brandFolder + "/" + modelFolder + "/" + CAR_YEAR + ".json");
                    if(error != null){
                        getYearList();
                    }
                }
            });
            mRequestQueue.add(jsonArrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Connect Fail");
        }

        recyViewCarModelYear = findViewById(R.id.recy_view_model_year_activity);
        adapter = new CarYearRecyViewAdapter(mCarYearList);
        recyViewCarModelYear.setAdapter(adapter);
        recyViewCarModelYear.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarModelYear.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarModelYear.addOnItemTouchListener(new RecyclerItemClickListener(this,  recyViewCarModelYear ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intentYear = new Intent(getApplicationContext(), VideoListActivity.class);
                        intentYear.putExtra("year", mCarYearList.get(position).getCarYear());
                        intentYear.putExtra("model", modelName);
                        intentYear.putExtra("brand", brandName);
                        intentYear.putExtra("logo", logoId);
                        startActivityForResult(intentYear, CAR_YEAR_REQUEST_CODE);
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

        // check that it is the SecondActivity with an OK result
        if (requestCode == CAR_YEAR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                brandName = data.getStringExtra("brand");
                modelName = data.getStringExtra("model");
                logoId = data.getIntExtra("logo", R.drawable.audi);

            }
        }
    }

    private void getYearList(){
        mDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            List<String> list = (List<String>) documentSnapshot.get("list");
                            for(int i = 0; i < list.size(); i++){
                                mCarYearList.add(new CarYear(list.get(i), logoId));
                                Log.d(TAG, list.get(i) + " from Firebase Firestore");
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