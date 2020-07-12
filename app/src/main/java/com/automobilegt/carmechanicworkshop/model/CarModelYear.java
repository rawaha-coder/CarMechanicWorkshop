package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

public class CarModelYear implements Serializable {

    private String mCarModelName;
    private int mCarModelLogo;
    private String mFolderLink;

    public CarModelYear() {
    }

    public CarModelYear(String carModelName, int carModelLogo, String Link) {

        mCarModelName = carModelName;
        mCarModelLogo = carModelLogo;

        String modelName = carModelName.toLowerCase();
        modelName = modelName.replaceAll("\\s","");

        mFolderLink = Link + "/"  + modelName;
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
