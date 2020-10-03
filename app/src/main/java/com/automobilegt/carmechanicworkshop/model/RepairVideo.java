package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

public class RepairVideo implements Serializable {

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
}
