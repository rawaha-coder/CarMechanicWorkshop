package com.automobilegt.carmechanicworkshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.automobilegt.carmechanicworkshop.adapter.CarBrandRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrandModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "testActivity";

    private ArrayList<CarBrandModel> mCarBrandModels;
    private RecyclerView recyViewCarBrand;
    private CarBrandRecyViewAdapter adapter;

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();;
    private DocumentReference mDocumentReference = mFirebaseFirestore.document("repair/CarBrandList");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        setTitle("Car Brand List");

        mCarBrandModels = new ArrayList<CarBrandModel>();


        mDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            List<String>  makerList = (List<String>) documentSnapshot.get("brandList");
                            for(int i = 0; i < makerList.size(); i++){
                                mCarBrandModels.add(new CarBrandModel(makerList.get(i)));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        recyViewCarBrand = findViewById(R.id.recycleview_car_brand_activity);
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



    public ArrayList<String> readData(){
       final ArrayList<String>  mList = new ArrayList<>();
        mDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            List<String>  makerList = (List<String>) documentSnapshot.get("brandList");
                            for(int i = 0; i < makerList.size(); i++){
                                mCarBrandModels.add(new CarBrandModel(makerList.get(i)));
                            }

                        }
                    }
                });

        return mList;
    }

    public void addData(){
        Map<String, Object> docData = new HashMap<>();

        docData.put("brandList", Arrays.asList("Ford", "Hyundai", "Kia", "Nissan", "Toyota", "Aston Martin", "Land Rover"));

        mDocumentReference.set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TestActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TestActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}