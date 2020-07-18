package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

public class CarModelModel implements Serializable {

    private String mCarModelName;
    private int mCarModelLogo;
    private String mFolderLink;

    public CarModelModel() {
    }

    public CarModelModel(String carModelName, int carModelLogo, String link) {
        mCarModelName = carModelName;
        mCarModelLogo = carModelLogo;
        mFolderLink = link + "/" + carModelName + "/year";
    }

    public String getCarModelName() {
        return mCarModelName;
    }

    public int getCarModelLogo() {
        return mCarModelLogo;
    }

    public String getFolderLink() {
        return mFolderLink;
    }
}
