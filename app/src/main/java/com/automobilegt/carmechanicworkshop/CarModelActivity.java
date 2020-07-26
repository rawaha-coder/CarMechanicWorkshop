package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
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
import com.automobilegt.carmechanicworkshop.adapter.CarModelRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarModel;
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
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;

public class CarModelActivity extends AppCompatActivity {

    private static final int CAR_MODEL_REQUEST_CODE = 300;
    private static final String TAG = "carmodelactivity";

    private AdView mAdView;
    private String brandFolder;
    private String brandName;
    private int logoId;
    private ProgressBar mProgressBar;
    private ArrayList<CarModel> mCarModelList;
    private CarModelRecyViewAdapter adapter;
    private RecyclerView recyViewCarBrandModel;

    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model);

        // AdMob initialization
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // Fields initialization
        Intent intent = getIntent();
        brandName = intent.getStringExtra("brand");
        logoId = intent.getIntExtra("logo", R.drawable.audi);

        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.document(COLLECTION + "/" + CAR_BRAND + "/" + brandName + "/" + CAR_MODEL);

        brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");


        setTitle(brandName + " Model List");

        mRequestQueue = Volley.newRequestQueue(this);
        mCarModelList = new ArrayList<CarModel>();

        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    AUTOMOBILEGT_URL + COLLECTION + "/" + brandFolder + "/" + CAR_MODEL + ".json", null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    mCarModelList.add(new CarModel(response.getString(i), logoId));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                            if(mCarModelList != null){
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error != null){
                        getModelList();
                    }
                }
            });
            mRequestQueue.add(jsonArrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        recyViewCarBrandModel = findViewById(R.id.recy_view_car_model_activity);
        adapter = new CarModelRecyViewAdapter(mCarModelList);
        recyViewCarBrandModel.setAdapter(adapter);
        recyViewCarBrandModel.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarBrandModel.setLayoutManager(new LinearLayoutManager(this));

        recyViewCarBrandModel.addOnItemTouchListener(new RecyclerItemClickListener(this, recyViewCarBrandModel, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intentModel = new Intent(getApplicationContext(), CarYearActivity.class);
                        intentModel.putExtra("model", mCarModelList.get(position).getCarModelName());
                        intentModel.putExtra("brand", brandName);
                        intentModel.putExtra("logo", logoId);
                        startActivityForResult(intentModel, CAR_MODEL_REQUEST_CODE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAR_MODEL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get String data from Intent
                brandName = data.getStringExtra("brand");
                logoId = data.getIntExtra("logo", R.drawable.audi);
            }
        }
    }

    private void getModelList(){
        mDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            List<String> list = (List<String>) documentSnapshot.get("list");
                            for(int i = 0; i < list.size(); i++){
                                mCarModelList.add(new CarModel(list.get(i), logoId));
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