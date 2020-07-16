package com.automobilegt.carmechanicworkshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.automobilegt.carmechanicworkshop.adapter.CarModelRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarBrandModel;
import com.automobilegt.carmechanicworkshop.model.CarModelModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class CarModelActivity extends AppCompatActivity {

    private static final int CAR_MODEL_REQUEST_CODE = 300;

    private String folder;
    private String brandName;
    private int logoId;
    private CarBrandModel brand;
    private ProgressBar mProgressBar;

    private ArrayList<CarModelModel> mCarModelList;
    private CarModelRecyViewAdapter adapter;
    private RecyclerView recyViewCarBrandModel;

    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();;
    private DocumentReference mDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model);

        brand = (CarBrandModel) getIntent().getSerializableExtra("brand");

        folder = brand.getFolderLink();
        brandName = brand.getCarBrandName();
        logoId = brand.getCarBrandLogo();

        mDocumentReference = mFirebaseFirestore.document(folder);

        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        setTitle(brandName + " Model List");

        mCarModelList = new ArrayList<CarModelModel>();


        if (folder != null) {
            mDocumentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                List<String> list = (List<String>) documentSnapshot.get("List");
                                for(int i = 0; i < list.size(); i++){
                                    mCarModelList.add(new CarModelModel(list.get(i), logoId, folder));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
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