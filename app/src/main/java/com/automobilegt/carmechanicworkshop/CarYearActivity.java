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
import com.automobilegt.carmechanicworkshop.adapter.CarModelRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarModelYear;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class CarYearActivity extends AppCompatActivity {

    private static final int CAR_YEAR_REQUEST_CODE = 301;
    private CarModelYear model;
    private String folder;
    private String modelName;
    private String carModelLink;
    private int logoId;
    private ArrayList<CarModelYear> mModelYearlList;
    private RequestQueue requestQueue;
    private CarModelRecyViewAdapter adapter;
    private RecyclerView recyViewCarModelYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_year);


        model = (CarModelYear) getIntent().getSerializableExtra("model");
        carModelLink = getIntent().getStringExtra("link");
        folder = model.getFolderLink();
        modelName = model.getCarModelName();
        logoId = model.getCarModelLogo();

        setTitle(modelName + " Years List");

        requestQueue = Volley.newRequestQueue(this);
        mModelYearlList = new ArrayList<CarModelYear>();

        if(folder != null) {
            try{
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                        folder + "/caryearlist.json", null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i=0; i<response.length(); i++) {
                                    try {
                                        mModelYearlList.add(new CarModelYear(response.getString(i), logoId, folder) );
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

        recyViewCarModelYear = findViewById(R.id.recy_view_model_year_activity);
        adapter = new CarModelRecyViewAdapter(mModelYearlList);
        recyViewCarModelYear.setAdapter(adapter);
        recyViewCarModelYear.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarModelYear.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarModelYear.addOnItemTouchListener(new RecyclerItemClickListener(this,  recyViewCarModelYear ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intentYear = new Intent(getApplicationContext(), VideoListActivity.class);
                        intentYear.putExtra("year", mModelYearlList.get(position));
                        intentYear.putExtra("link", folder);
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
                intent.putExtra("link", carModelLink);
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
                folder = data.getStringExtra("link");

            }
        }
    }
}