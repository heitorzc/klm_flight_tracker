package com.klm.testcase.Objects;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class Airport {

    String city;
    String country;
    String code;
    String lat;
    String lon;

    public Airport(String city, String country, String code, String lat, String lon) {
        this.city = city;
        this.country = country;
        this.code = code;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

}