package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.automobilegt.carmechanicworkshop.adapter.VideoListRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarVideoModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.automobilegt.carmechanicworkshop.util.Constants.AUTOMOBILEGT_URL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_MODEL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_YEAR;
import static com.automobilegt.carmechanicworkshop.util.Constants.COLLECTION;


public class VideoListActivity extends AppCompatActivity {

    private static final int VIDEO_REQUEST_CODE = 302;

    private AdView mAdView;

    private int logoId;
    private String year;
    private String brandName;
    private String modelName;
    private String brandFolder;
    private String modelFolder;
    private ProgressBar mProgressBar;

    private ArrayList<CarVideoModel> mVideoList;
    private VideoListRecyViewAdapter adapter;
    private RecyclerView recyViewCarVideoList;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

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
        year = intent.getStringExtra("year");
        logoId = intent.getIntExtra("logo", R.drawable.audi);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection(COLLECTION + "/" + CAR_BRAND + "/" + brandName + "/" + CAR_MODEL + "/" + modelName + "/" + CAR_YEAR + "/" + year);

        brandFolder = brandName.toLowerCase();
        brandFolder = brandFolder.replaceAll("\\s","");

        modelFolder = modelName.toLowerCase();
        modelFolder = modelFolder.replaceAll("\\s","");


        setTitle(brandName + " " + modelName + " " + year + " Repair Vidoes");

        mRequestQueue = Volley.newRequestQueue(this);
        mVideoList = new ArrayList<CarVideoModel>();

            try{
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                        AUTOMOBILEGT_URL + COLLECTION + "/" + brandFolder + "/" + modelFolder + "/" + year + ".json", null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i=0; i<response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        mVideoList.add(new CarVideoModel(jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("link")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                if(mVideoList != null){
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){
                            getVideoList();
                        }
                    }
                });
                mRequestQueue.add(jsonArrayRequest);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "fail to connect", Toast.LENGTH_SHORT).show();
            }


        recyViewCarVideoList = findViewById(R.id.recy_view_video_list_activity);
        adapter = new VideoListRecyViewAdapter(mVideoList, logoId);
        recyViewCarVideoList.setAdapter(adapter);
        recyViewCarVideoList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarVideoList.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarVideoList.addOnItemTouchListener(new RecyclerItemClickListener(this,  recyViewCarVideoList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intentVideo = new Intent(getApplicationContext(), PlayVideoActivity.class);
                        intentVideo.putExtra("video", mVideoList.get(position));
                        intentVideo.putExtra("year", year);
                        intentVideo.putExtra("model", modelName);
                        intentVideo.putExtra("brand", brandName);
                        intentVideo.putExtra("logo", logoId);
                        startActivityForResult(intentVideo, VIDEO_REQUEST_CODE);
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

    private void getVideoList(){
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                    String title =  snapshot.getString("title");
                    String description =  snapshot.getString("description");
                    String link =  snapshot.getString("link");
                    mVideoList.add(new CarVideoModel(title, description, link));
                }
                adapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}