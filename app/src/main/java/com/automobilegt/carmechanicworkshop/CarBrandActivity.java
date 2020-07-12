package com.automobilegt.carmechanicworkshop;

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
import com.automobilegt.carmechanicworkshop.adapter.CarBrandRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrandModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.automobilegt.carmechanicworkshop.util.Constants.AGT_REPAIR_FOLDER;

public class CarBrandActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ArrayList<CarBrandModel> mCarBrandModels;
    private RecyclerView recyViewCarBrand;
    private CarBrandRecyViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);

        setTitle("Car Brand List");

        requestQueue = Volley.newRequestQueue(this);
        mCarBrandModels = new ArrayList<CarBrandModel>();



        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    AGT_REPAIR_FOLDER + "carbrandlist.json" , null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    mCarBrandModels.add(new CarBrandModel(response.getString(i)));

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
            Log.i("CarBrand", "Connect Fail");
        }

        recyViewCarBrand = findViewById(R.id.recy_view_car_brand_activity);
        adapter = new CarBrandRecyViewAdapter(mCarBrandModels);
        recyViewCarBrand.setAdapter(adapter);
        recyViewCarBrand.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarBrand.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarBrand.addOnItemTouchListener(new RecyclerItemClickListener(this, recyViewCarBrand, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intentBrand = new Intent(getApplicationContext(), CarModelActivity.class);
                        intentBrand.putExtra("brand", mCarBrandModels.get(position));
                        startActivity(intentBrand);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
}