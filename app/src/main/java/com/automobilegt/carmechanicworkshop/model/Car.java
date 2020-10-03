package com.automobilegt.carmechanicworkshop.model;

import static com.automobilegt.carmechanicworkshop.data.BrandLogo.LOGO_ID;

public class Car {
    private String mBrandModelYear;
    private int mLogo;

    public Car(String brandModelYear) {
        mBrandModelYear = brandModelYear;
        mLogo = LOGO_ID.get(brandModelYear);
    }

    public Car(String brandModelYear, int logo) {
        mBrandModelYear = brandModelYear;
        mLogo = logo;
    }

    public String getBrandModelYear() {
        return mBrandModelYear;
    }

    public int getLogo() {
        return mLogo;
    }
}
