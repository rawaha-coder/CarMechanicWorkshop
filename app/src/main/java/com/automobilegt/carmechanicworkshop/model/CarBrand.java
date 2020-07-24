package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

import static com.automobilegt.carmechanicworkshop.data.BrandLogo.LOGO_ID;

public class CarBrand implements Serializable {

    private String mCarBrandName;
    private int mCarBrandLogo;

    public CarBrand() {
    }

    public CarBrand(String carBrandName) {

        mCarBrandName = carBrandName;
        mCarBrandLogo = LOGO_ID.get(carBrandName);
    }

    public String getCarBrandName() {
        return mCarBrandName;
    }

    public int getCarBrandLogo() {
        return mCarBrandLogo;
    }

}
