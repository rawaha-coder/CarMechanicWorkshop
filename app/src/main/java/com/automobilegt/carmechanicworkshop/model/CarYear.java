package com.automobilegt.carmechanicworkshop.model;

import java.io.Serializable;

public class CarYear implements Serializable {
    private String mCarYear;
    private int mCarModelLogo;

    public CarYear() {
    }

    public CarYear(String carYear, int carModelLogo) {
        mCarYear = carYear;
        mCarModelLogo = carModelLogo;
    }

    public String getCarYear() {
        return mCarYear;
    }

    public int getCarModelLogo() {
        return mCarModelLogo;
    }
}
