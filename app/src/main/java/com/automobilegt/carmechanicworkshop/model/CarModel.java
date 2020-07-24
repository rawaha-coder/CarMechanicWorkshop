package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

public class CarModel implements Serializable {

    private String mCarModelName;
    private int mCarModelLogo;

    public CarModel() {
    }

    public CarModel(String carModelName, int carModelLogo) {
        mCarModelName = carModelName;
        mCarModelLogo = carModelLogo;
    }

    public String getCarModelName() {
        return mCarModelName;
    }

    public int getCarModelLogo() {
        return mCarModelLogo;
    }
}
