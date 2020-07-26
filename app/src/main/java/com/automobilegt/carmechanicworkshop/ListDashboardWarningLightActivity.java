package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automobilegt.carmechanicworkshop.adapter.WarningLightRecyViewAdapter;
import com.automobilegt.carmechanicworkshop.controller.RecyclerItemClickListener;
import com.automobilegt.carmechanicworkshop.data.GreenSymbolsData;
import com.automobilegt.carmechanicworkshop.data.OrangeSymbolsData;
import com.automobilegt.carmechanicworkshop.data.RedSymbolsData;
import com.automobilegt.carmechanicworkshop.model.WarningLight;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import static com.automobilegt.carmechanicworkshop.model.WarningLight.CreateWarningLightArrayList;

public class ListDashboardWarningLightActivity extends AppCompatActivity {

    private static final int COLOR_REQUEST_CODE = 100;

    private AdView mAdView;
    private ArrayList<WarningLight> mWarningLightArrayList;
    private Integer[] iconId;
    private String[] itemName;
    private String[] itemDescription;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dashboard_warning_light);

        Intent intent = getIntent();
        if(intent.getStringExtra("color") != null){
            color = intent.getStringExtra("color");
        }

        setTitle("Warning Light List");

        final RecyclerView recyViewWarningLight = findViewById(R.id.recy_view_list_warning_light);

        if(color.toString().equals("Red")){
            RedSymbolsData symbols = new RedSymbolsData();
            iconId =  symbols.getSYMBOLS_ICON_ID();
            itemName = symbols.getSYMBOLS_NAME();
            itemDescription = symbols.getSYMBOLS_MEANING();
            mWarningLightArrayList = CreateWarningLightArrayList(iconId, itemName, itemDescription);
        }else if(color.toString().equals("Orange")){
            OrangeSymbolsData Symbols = new OrangeSymbolsData();
            iconId =  Symbols.getSYMBOLS_ICON_ID();
            itemName = Symbols.getSYMBOLS_NAME();
            itemDescription = Symbols.getSYMBOLS_MEANING();
            mWarningLightArrayList = CreateWarningLightArrayList(iconId, itemName, itemDescription);
        }else if(color.toString().equals("Green")){
            GreenSymbolsData Symbols = new GreenSymbolsData();
            iconId =  Symbols.getSYMBOLS_ICON_ID();
            itemName = Symbols.getSYMBOLS_NAME();
            itemDescription = Symbols.getSYMBOLS_MEANING();
            mWarningLightArrayList = CreateWarningLightArrayList(iconId, itemName, itemDescription);
        }

        WarningLightRecyViewAdapter adapter = new WarningLightRecyViewAdapter(mWarningLightArrayList);
        recyViewWarningLight.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewWarningLight.setAdapter(adapter);
        recyViewWarningLight.setLayoutManager(new LinearLayoutManager(this));

        recyViewWarningLight.addOnItemTouchListener(new RecyclerItemClickListener(this, recyViewWarningLight ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), WarningLightMeaningActivity.class);
                        intent.putExtra("image", iconId[+position]);
                        intent.putExtra("description", itemDescription[+position]);
                        intent.putExtra("name", itemName[+position]);
                        intent.putExtra("color", color);
                        startActivityForResult(intent, COLOR_REQUEST_CODE);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // AdMob
        MobileAds.initialize(this, "ca-app-pub-2666553857909586~7667456701");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == COLOR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                color = data.getStringExtra("color");

            }
        }
    }
}