package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

public class CarYearModel implements Serializable {

    private String mCarYear;
    private int mCarModelLogo;
    private String mFolderLink;

    public CarYearModel() {
    }

    public CarYearModel(String year, int carModelLogo, String link) {
        mCarYear = year;
        mCarModelLogo = carModelLogo;
        mFolderLink = link + "/" + year;
    }

    public String getCarYear() {
        return mCarYear;
    }

    public int getCarModelLogo() {
        return mCarModelLogo;
    }

    public String getFolderLink() {
        return mFolderLink;
    }
}
