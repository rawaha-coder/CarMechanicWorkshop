package com.automobilegt.carmechanicworkshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automobilegt.carmechanicworkshop.adapter.CarModelRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrandModel;
import com.automobilegt.carmechanicworkshop.model.CarModelYear;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CarModelActivity extends AppCompatActivity {

    private static final int CAR_MODEL_REQUEST_CODE = 300;
    private String folder;
    private String brandName;
    private int logoId;
    private CarBrandModel brand;
    private ArrayList<CarModelYear> mCarModelList;
    private RequestQueue requestQueue;
    private CarModelRecyViewAdapter adapter;
    private RecyclerView recyViewCarBrandModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model);

        brand = (CarBrandModel) getIntent().getSerializableExtra("brand");
        folder = brand.getFolderLink();
        brandName = brand.getCarBrandName();
        logoId = brand.getCarBrandLogo();


        setTitle(brandName + " Model List");

        requestQueue = Volley.newRequestQueue(this);
        mCarModelList = new ArrayList<CarModelYear>();


        if (folder != null) {
            try {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                        folder  + "/carmodellist.json", null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        mCarModelList.add(new CarModelYear(response.getString(i), logoId, folder) );
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
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("CarModel", "Connect Fail");
            }

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
                        intentModel.putExtra("model", mCarModelList.get(position));
                        intentModel.putExtra("link", folder);
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

        // check that it is the SecondActivity with an OK result
        if (requestCode == CAR_MODEL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                folder = data.getStringExtra("link");

            }
        }
    }

}