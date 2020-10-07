package com.automobilegt.carmechanicworkshop.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.automobilegt.carmechanicworkshop.controller.RequestQuery;
import com.automobilegt.carmechanicworkshop.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarLoader extends AsyncTaskLoader<List<Car>> {
    private String firstUrl;
    private String secondURL;
    private String mContextName;
    private int mLogo;

    public CarLoader(@NonNull Context context, String firstUrl, String secondURL) {
        super(context);
        mContextName = context.getClass().getName();
        this.firstUrl = firstUrl;
        this.secondURL = secondURL;
    }
    public CarLoader(@NonNull Context context, String firstUrl, String secondURL, int logo) {
        super(context);
        mContextName = context.getClass().getName();
        this.firstUrl = firstUrl;
        this.secondURL = secondURL;
        mLogo = logo;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Car> loadInBackground() {
        List<Car> list = new ArrayList<>();
        if (firstUrl == null || secondURL == null) {
            return null;
        }
        switch (mContextName) {
            case "com.automobilegt.carmechanicworkshop.CarBrandActivity":
                list = RequestQuery.fetchBrandData(firstUrl, secondURL);
                break;
            case "com.automobilegt.carmechanicworkshop.CarModelActivity":
                list = RequestQuery.fetchModelData(firstUrl, secondURL, mLogo);
                break;
            case "com.automobilegt.carmechanicworkshop.CarYearActivity":
                list = RequestQuery.fetchYearData(firstUrl, secondURL, mLogo);
                break;
        }
        return list;
    }
}
