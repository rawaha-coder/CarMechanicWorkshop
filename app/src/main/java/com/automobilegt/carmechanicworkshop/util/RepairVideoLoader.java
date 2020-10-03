package com.automobilegt.carmechanicworkshop.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.automobilegt.carmechanicworkshop.controller.RequestQuery;

public class RepairVideoLoader extends AsyncTaskLoader {
    private String mUrl;

    public RepairVideoLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return RequestQuery.fetchRepairVideoData(mUrl);
    }
}
