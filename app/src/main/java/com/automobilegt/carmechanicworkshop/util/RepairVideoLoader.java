package com.automobilegt.carmechanicworkshop.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.automobilegt.carmechanicworkshop.controller.RequestQuery;
import com.automobilegt.carmechanicworkshop.model.RepairVideo;

import java.util.List;

public class RepairVideoLoader extends AsyncTaskLoader <List<RepairVideo>>{
    private String firstUrl;
    private String secondURL;

    public RepairVideoLoader(@NonNull Context context, String firstUrl, String secondURL) {
        super(context);
        this.firstUrl = firstUrl;
        this.secondURL = secondURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<RepairVideo> loadInBackground() {
        if (firstUrl == null || secondURL == null) {
            return null;
        }
        return RequestQuery.fetchRepairVideoData(firstUrl, secondURL);
    }
}
