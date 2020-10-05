package com.automobilegt.carmechanicworkshop.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.automobilegt.carmechanicworkshop.controller.RequestQuery;
import com.automobilegt.carmechanicworkshop.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarLoader extends AsyncTaskLoader {
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
    public Object loadInBackground() {
        List<Car> list = new ArrayList<>();
        if (firstUrl == null || secondURL == null) {
            return null;
        }
        if (mContextName.equals("com.automobilegt.carmechanicworkshop.CarBrandActivity")){
            list = RequestQuery.fetchBrandData(firstUrl, secondURL);
        }else if(mContextName.equals("com.automobilegt.carmechanicworkshop.CarModelActivity")){
            list = RequestQuery.fetchModelData(firstUrl, secondURL, mLogo);
        }else if (mContextName.equals("com.automobilegt.carmechanicworkshop.CarYearActivity")){
            list = RequestQuery.fetchYearData(firstUrl, secondURL, mLogo);
        }
        return list;
    }
}
