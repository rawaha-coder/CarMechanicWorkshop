package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

import static com.automobilegt.carmechanicworkshop.data.CarBrandData.BRAND_LOGO;
import static com.automobilegt.carmechanicworkshop.util.Constants.MECHANIC_WORKSHOP_FOLDER;


public class CarBrandModel implements Serializable {

    private String mCarBrandName;
    private int mCarBrandLogo;
    private String mFolderLink;

    public CarBrandModel() {
    }

    public CarBrandModel(String carBrandName) {
        String brandName = carBrandName.toLowerCase();
        brandName = brandName.replaceAll("\\s","");

        //mCarBrandName = BRAND_NAME.get(brandName);
        mCarBrandName = carBrandName;
        mCarBrandLogo = BRAND_LOGO.get(brandName);
        mFolderLink =  MECHANIC_WORKSHOP_FOLDER + carBrandName + "/CarModel";
    }

    public String getCarBrandName() {
        return mCarBrandName;
    }

    public int getCarBrandLogo() { return mCarBrandLogo; }

    public String getFolderLink() {
        return mFolderLink;
    }

}
