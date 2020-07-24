package com.automobilegt.carmechanicworkshop.controller;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.automobilegt.carmechanicworkshop.model.CarBrandModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.automobilegt.carmechanicworkshop.util.Constants.AUTOMOBILEGT_URL;
import static com.automobilegt.carmechanicworkshop.util.Constants.CAR_BRAND;

public class DataFlow {

    private String mTag;
    private String mCollection;
    private RequestQueue mRequestQueue;
    private String mBrandListReference;
    private ArrayList<String> mBrandList;
    private Context mContext;
    private FirebaseFirestore mFirebaseFirestore;


    public void loadData(String mCollection) {
        Log.d(mTag, "Collection:" + mCollection);

        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    AUTOMOBILEGT_URL + mCollection + "/" + CAR_BRAND + ".json", null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    mBrandList.add(response.getString(i));
                                    Log.d(mTag, response.getString(0));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(mTag, e.getMessage().toString());
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(mTag, error.getMessage().toString());

                }
            });
            mRequestQueue.add(jsonArrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(mTag, "Connect Fail");
        }

    }

    private void updateData(String doc, ArrayList<String> arrayList) {
        Log.d(mTag, "Collection:" + doc);

        DocumentReference mDocumentReference = mFirebaseFirestore.document(doc);

        Map<String, Object> obj = new HashMap<>();
        obj.put("list", arrayList);

        if(arrayList != null && !TextUtils.isEmpty(doc)){
            mDocumentReference.update(obj)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mContext, "Brand List Updated Success", Toast.LENGTH_SHORT).show();

                            Log.d(mTag, "show list");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Brand List Updated Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
