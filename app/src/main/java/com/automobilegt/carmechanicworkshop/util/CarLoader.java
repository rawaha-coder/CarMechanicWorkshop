package com.automobilegt.carmechanicworkshop.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.automobilegt.carmechanicworkshop.controller.RequestQuery;
import com.automobilegt.carmechanicworkshop.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarLoader extends AsyncTaskLoader {
    private String mUrl;
    private String mContextName;
    private int mLogo;

    public CarLoader(@NonNull Context context, String url) {
        super(context);
        mContextName = context.getClass().getName();
        Log.d("Context", context.getClass().getName());
        mUrl = url;
    }
    public CarLoader(@NonNull Context context, String url, int logo) {
        super(context);
        mContextName = context.getClass().getName();
        Log.d("Context", context.getClass().getName());
        mLogo = logo;
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        List<Car> list = new ArrayList<>();
        if (mUrl == null) {
            return null;
        }
        if (mContextName.equals("com.automobilegt.carmechanicworkshop.CarBrandActivity")){
            list = RequestQuery.fetchBrandData(mUrl);
        }else if(mContextName.equals("com.automobilegt.carmechanicworkshop.CarModelActivity")){
            list = RequestQuery.fetchModelData(mUrl, mLogo);
        }else if (mContextName.equals("com.automobilegt.carmechanicworkshop.CarYearActivity")){
            list = RequestQuery.fetchYearData(mUrl, mLogo);
        }
        return list;
    }
}
