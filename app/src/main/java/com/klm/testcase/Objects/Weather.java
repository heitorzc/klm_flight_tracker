package com.klm.testcase.Objects;

import java.io.Serializable;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class Weather implements Serializable {

    String degrees;
    String extra_info;

    public Weather(String degrees, String extra_info) {
        this.degrees = degrees;
        this.extra_info = extra_info;
    }

    public String getDegrees() {
        return degrees;
    }

    public String getExtra_info() {
        return extra_info;
    }
}
