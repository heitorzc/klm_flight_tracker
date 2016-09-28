package com.klm.testcase.Tasks;

import android.app.Activity;
import android.widget.Toast;

import com.klm.testcase.Constants;
import com.klm.testcase.Objects.Airport;
import com.klm.testcase.R;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class GetAirportsList {
    @BindString(R.string.request_error) String REQUEST_ERROR;

    private static final String AIRPORTS_REQUEST_URL = "https://raw.githubusercontent.com/jpatokal/openflights/master/data/airports.dat";

    Activity c;
    LoadingAirportsList listener;

    public GetAirportsList(Activity c) {
        this.c = c;
        listener = (LoadingAirportsList)  c;
        ButterKnife.bind(this, c);
    }

    public interface LoadingAirportsList {
        void onCompleted(ArrayList<Airport> list);
    }

    public void request(){

        ServerHelper.get(AIRPORTS_REQUEST_URL, null, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                String[] lines = responseString.replace("\"", "").split("\\r?\\n");
                String[] data;
                String code;
                ArrayList<Airport> airports = new ArrayList<>();

                for (int i = 0; i < lines.length; i++){

                    data = lines[i].split(",");

                    //Get IATACode if not empty. Otherwise, get 4 digits code.
                    code = (!data[4].trim().isEmpty()) ? data[4] : data[5];

                    //index 2 = City; index 3 = country; index 4 = IATACode; index 6 = latitude; index 7 = longitude
                    airports.add(new Airport(data[2], data[3], code, data[6], data[7]));
                }

                if (listener != null){
                    listener.onCompleted(airports);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(c, REQUEST_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
