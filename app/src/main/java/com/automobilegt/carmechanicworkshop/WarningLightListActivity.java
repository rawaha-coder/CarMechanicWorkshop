package com.automobilegt.carmechanicworkshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automobilegt.carmechanicworkshop.adapter.RVWarningLightAdapter;
import com.automobilegt.carmechanicworkshop.data.GreenSymbolsData;
import com.automobilegt.carmechanicworkshop.data.OrangeSymbolsData;
import com.automobilegt.carmechanicworkshop.data.RedSymbolsData;
import com.automobilegt.carmechanicworkshop.interfaces.ListItemClickListener;
import com.automobilegt.carmechanicworkshop.model.WarningLight;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

import static com.automobilegt.carmechanicworkshop.model.WarningLight.CreateWarningLightArrayList;

public class WarningLightListActivity extends AppCompatActivity implements ListItemClickListener {

    private static final int COLOR_REQUEST_CODE = 100;

    private ArrayList<WarningLight> mWarningLightArrayList;
    private Integer[] iconId;
    private String[] itemName;
    private String[] itemDescription;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_light_list);

        Intent intent = getIntent();
        if(intent.getStringExtra("color") != null){
            color = intent.getStringExtra("color");
        }

        setTitle("Warning Light List");

        final RecyclerView recyViewWarningLight = findViewById(R.id.recy_view_list_warning_light);

        switch (color) {
            case "Red":
                RedSymbolsData symbols = new RedSymbolsData();
                iconId = symbols.getSYMBOLS_ICON_ID();
                itemName = symbols.getSYMBOLS_NAME();
                itemDescription = symbols.getSYMBOLS_MEANING();
                mWarningLightArrayList = CreateWarningLightArrayList(iconId, itemName, itemDescription);
                break;
            case "Orange": {
                OrangeSymbolsData Symbols = new OrangeSymbolsData();
                iconId = Symbols.getSYMBOLS_ICON_ID();
                itemName = Symbols.getSYMBOLS_NAME();
                itemDescription = Symbols.getSYMBOLS_MEANING();
                mWarningLightArrayList = CreateWarningLightArrayList(iconId, itemName, itemDescription);
                break;
            }
            case "Green": {
                GreenSymbolsData Symbols = new GreenSymbolsData();
                iconId = Symbols.getSYMBOLS_ICON_ID();
                itemName = Symbols.getSYMBOLS_NAME();
                itemDescription = Symbols.getSYMBOLS_MEANING();
                mWarningLightArrayList = CreateWarningLightArrayList(iconId, itemName, itemDescription);
                break;
            }
        }

        RVWarningLightAdapter adapter = new RVWarningLightAdapter(mWarningLightArrayList, this);
        recyViewWarningLight.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyViewWarningLight.setAdapter(adapter);
        recyViewWarningLight.setLayoutManager(new LinearLayoutManager(this));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COLOR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                color = data.getStringExtra("color");
            }
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getApplicationContext(), WarningLightMeaningActivity.class);
        intent.putExtra("image", iconId[+clickedItemIndex]);
        intent.putExtra("description", itemDescription[+clickedItemIndex]);
        intent.putExtra("name", itemName[+clickedItemIndex]);
        intent.putExtra("color", color);
        startActivityForResult(intent, COLOR_REQUEST_CODE);
    }
}