package com.klm.testcase.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.klm.testcase.Constants;
import com.klm.testcase.Objects.Airport;
import com.klm.testcase.R;
import com.klm.testcase.Tasks.GetAirportsList;

import java.util.ArrayList;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class SplashScreen extends AppCompatActivity implements GetAirportsList.LoadingAirportsList{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);   		 requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Load list of airports from
        //https://raw.githubusercontent.com/jpatokal/openflights/master/data/airports.dat
        new GetAirportsList(this).request();

    }


    /**
     * Method called when the task GetAirportsList is completed.
     */
    @Override
    public void onCompleted(ArrayList<Airport> list) {
        //Copy airport list to Constants class to be used by anothers activities.
        Constants.airports = list;
        //Create a String Array with origins and destinations suggestions.
        Constants.airports_locations = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            Constants.airports_locations[i] = list.get(i).getCode() + ", " + list.get(i).getCity() + ", " + list.get(i).getCountry();
        }

        //Start MainActivity
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }

}
