package com.klm.testcase.Tasks;

import android.content.Context;

import com.klm.testcase.Objects.Flight;
import com.klm.testcase.Objects.FlightsList;


/**
 * Created by dheringer on 4/25/2016.
 */
public class ServerTask extends TaskManager {

    public ServerTask(TaskListener taskListener) {
        super(taskListener);
    }


    public void getFlightsList(String url) {
        doRequest(url, new FlightsList());
    }

    public void getFlight(String url) {
        doRequest(url, new Flight());
    }

}
