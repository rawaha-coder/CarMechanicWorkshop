package com.automobilegt.carmechanicworkshop.model;

import java.util.ArrayList;

public class WarningLight {
    private int mSymboleResId;
    private String mSymbolTitle;
    private String mSymboleMeaning;

    public WarningLight(int symboleResId, String symbolTitle, String symboleMeaning) {
        mSymboleResId = symboleResId;
        mSymbolTitle = symbolTitle;
        mSymboleMeaning = symboleMeaning;
    }

    public static ArrayList<WarningLight> CreateWarningLightArrayList(Integer[] symbolIcoId, String[] symbolName, String[] symbolDescrition){
        ArrayList<WarningLight> symbolList = new ArrayList<WarningLight>();
        for (int i=0; i<symbolIcoId.length; i++){
            symbolList.add(new WarningLight(symbolIcoId[i], symbolName[i], symbolDescrition[i]));
        }
        return symbolList;
    }

    public int getSymboleResId() {
        return mSymboleResId;
    }

    public String getSymbolTitle() {
        return mSymbolTitle;
    }

    public String getSymboleMeaning() {
        return mSymboleMeaning;
    }
}
