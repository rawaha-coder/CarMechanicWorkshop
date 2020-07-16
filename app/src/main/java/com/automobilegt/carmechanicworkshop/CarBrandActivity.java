package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.automobilegt.carmechanicworkshop.adapter.CarBrandRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrandModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

import static com.automobilegt.carmechanicworkshop.util.Constants.MECHANIC_WORKSHOP_FOLDER;


public class CarBrandActivity extends AppCompatActivity {


    private ArrayList<CarBrandModel> mCarBrandList;
    private RecyclerView recyViewCarBrand;
    private CarBrandRecyViewAdapter adapter;
    private ProgressBar mProgressBar;

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();;
    private DocumentReference mDocumentReference = mFirebaseFirestore.document(MECHANIC_WORKSHOP_FOLDER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);

        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        setTitle("Car Brand List");

        mCarBrandList = new ArrayList<CarBrandModel>();


        mDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            List<String> list = (List<String>) documentSnapshot.get("List");
                            for(int i = 0; i < list.size(); i++){
                                mCarBrandList.add(new CarBrandModel(list.get(i)));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });


        recyViewCarBrand = findViewById(R.id.recy_view_car_brand_activity);
        adapter = new CarBrandRecyViewAdapter(mCarBrandList);
        recyViewCarBrand.setAdapter(adapter);
        recyViewCarBrand.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewCarBrand.setLayoutManager(new LinearLayoutManager(this));


        recyViewCarBrand.addOnItemTouchListener(new RecyclerItemClickListener(this, recyViewCarBrand, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intentBrand = new Intent(getApplicationContext(), CarModelActivity.class);
                        intentBrand.putExtra("brand", mCarBrandList.get(position));
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