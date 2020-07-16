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
import android.widget.ProgressBar;

import com.automobilegt.carmechanicworkshop.adapter.CarYearRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.model.CarModelModel;
import com.automobilegt.carmechanicworkshop.model.CarYearModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;


public class CarYearActivity extends AppCompatActivity {

    private static final int CAR_YEAR_REQUEST_CODE = 301;

    private CarModelModel model;

    private String folder;
    private String modelName;
    private String carModelLink;
    private int logoId;

    private ProgressBar mProgressBar;

    private ArrayList<CarYearModel> mModelYearlList;
    private CarYearRecyViewAdapter adapter;
    private RecyclerView recyViewCarModelYear;

    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();;
    private DocumentReference mDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_year);

        model = (CarModelModel) getIntent().getSerializableExtra("model");

        carModelLink = getIntent().getStringExtra("link");

        folder = model.getFolderLink();
        modelName = model.getCarModelName();
        logoId = model.getCarModelLogo();

        mDocumentReference = mFirebaseFirestore.document(folder);

        mProgressBar = findViewById(R.id.cmw_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        setTitle(modelName + " Years List");

        mModelYearlList = new ArrayList<CarYearModel>();

        if(folder != null) {
            mDocumentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                List<String> list = (List<String>) documentSnapshot.get("List");
                                for(int i = 0; i < list.size(); i++){
                                    mModelYearlList.add(new CarYearModel(list.get(i), logoId, folder));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
        }

        recyViewCarModelYear = findViewById(R.id.recy_view_model_year_activity);
        adapter = new CarYearRecyViewAdapter(mModelYearlList);
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