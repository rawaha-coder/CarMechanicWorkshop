package com.automobilegt.carmechanicworkshop.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class RepairVideo implements Serializable, Comparable<RepairVideo> {

    private String mVideoTitle;
    private String mVideoDescription;
    private String mVideoLink;

    public RepairVideo(String videoTitle, String videoDescription, String videoLink) {
        mVideoTitle = videoTitle;
        mVideoDescription = videoDescription;
        mVideoLink = videoLink;
    }

    public String getVideoTitle() {
        return mVideoTitle;
    }

    public String getVideoDescription() {
        return mVideoDescription;
    }

    public String getVideoLink() {
        return mVideoLink;
    }

    @NonNull
    @Override
    public String toString() {
        return mVideoTitle;
    }


    @Override
    public int compareTo(RepairVideo repairVideo) {
        return this.mVideoTitle.compareTo(repairVideo.mVideoTitle);
    }
}
