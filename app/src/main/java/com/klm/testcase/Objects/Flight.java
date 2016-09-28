package com.klm.testcase.Objects;

import com.klm.testcase.Constants;

import java.io.Serializable;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class Flight implements Serializable {
    String flightNumber;
    FlightLeg operatingFlightLeg;

    public Flight(){}

    public String getFlightNumber() {
        return flightNumber;
    }


    public FlightLeg getOperatingFlightLeg() {
        return operatingFlightLeg;
    }

    public String searchAirport(String code){
        String airport = code;
        for (int i = 0; i < Constants.airports.size(); i++){
            if (Constants.airports.get(i).getCode().equals(code)){
                airport = Constants.airports.get(i).getCity() + ", " + Constants.airports.get(i).getCountry();
            }
        }
        return airport;
    }


    public String getLatitude(String code){
        String lat = code;
        for (int i = 0; i < Constants.airports.size(); i++){
            if (Constants.airports.get(i).getCode().equals(code)){
                lat = Constants.airports.get(i).getLat();
            }
        }
        return lat;
    }

    public String getLongitude(String code){
        String lon = code;
        for (int i = 0; i < Constants.airports.size(); i++){
            if (Constants.airports.get(i).getCode().equals(code)){
                lon = Constants.airports.get(i).getLon();
            }
        }
        return lon;
    }
}
