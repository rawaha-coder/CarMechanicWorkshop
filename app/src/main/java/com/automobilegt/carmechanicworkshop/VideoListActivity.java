package com.automobilegt.carmechanicworkshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automobilegt.carmechanicworkshop.adapter.VideoListRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarModelYear;
import com.automobilegt.carmechanicworkshop.model.CarVideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VideoListActivity extends AppCompatActivity {

    private static final int VIDEO_REQUEST_CODE = 302;
    private String folder;
    private int logoId;
    private String year;
    private String carYearLink;
    private CarModelYear modelYear;
    private ArrayList<CarVideoModel> mVideoList;
    private RequestQueue requestQueue;
    private VideoListRecyViewAdapter adapter;
    private RecyclerView recyViewCarVideoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        modelYear = (CarModelYear) getIntent().getSerializableExtra("year");
        carYearLink = getIntent().getStringExtra("link");
        folder = modelYear.getFolderLink();
        year = modelYear.getCarModelName();
        logoId = modelYear.getCarModelLogo();

        setTitle(year + " Vidoes List");

        requestQueue = Volley.newRequestQueue(this);
        mVideoList = new ArrayList<CarVideoModel>();

        recyViewCarVideoList = findViewById(R.id.recy_view_video_list_activity);

        if(folder != null) {
            try{
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                        folder + ".json", null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i=0; i<response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        mVideoList.add(new CarVideoModel(jsonObject.getString("title"), jsonObject.getString("message"), jsonObject.getString("link")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "fail to connect", Toast.LENGTH_SHORT).show();
            }
        }


        adapter = new VideoListRecyViewAdapter(mVideoList, logoId);
        recyViewCarVideoList.setAdapter(adapter);
        recyViewCarVideoList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarVideoList.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarVideoList.addOnItemTouchListener(new RecyclerItemClickListener(this,  recyViewCarVideoList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intentVideo = new Intent(getApplicationContext(), PlayVideoActivity.class);
                        intentVideo.putExtra("video", mVideoList.get(position));
                        intentVideo.putExtra("link", folder);
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
                intent.putExtra("link", carYearLink);
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
        if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                folder = data.getStringExtra("link");

            }
        }
    }
}