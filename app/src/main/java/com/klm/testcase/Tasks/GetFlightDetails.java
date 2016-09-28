package com.klm.testcase.Tasks;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.klm.testcase.Objects.Flight;
import com.klm.testcase.R;
import com.klm.testcase.Views.CustomProgressDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class GetFlightDetails {
    @BindString(R.string.error_flight_request) String ERROR_FLIGHT_REQUEST;
    @BindString(R.string.flight_not_found)             String FLIGHT_NOT_FOUND;
    @BindString(R.string.time_at)                     String TIME_AT;

    private final String BASE_URL = "https://fox.klm.com/fox/json/flightstatuses?";
    Activity c;
    String flight;
    String date;
    CustomProgressDialog pd;

    LoadingFlightDetails listenerByFlightNumber;
    LoadingListFlightDetails listenerByOriginDestination;

    public GetFlightDetails(Activity c, String flight, String date) {
        this.c = c;
        this.flight = flight;
        this.date = date;
        listenerByFlightNumber = (LoadingFlightDetails)  c;
        listenerByOriginDestination = (LoadingListFlightDetails) c;
        ButterKnife.bind(this, c);
    }

    public interface LoadingFlightDetails {
        void onSearchFlightCompleted(Flight flight);
    }

    public interface LoadingListFlightDetails {
        void onSearchListFlightsCompleted(ArrayList<Flight> flights);
    }


    public void requestByFlightNumber(){
        pd = new CustomProgressDialog(c);
        pd.show();

        ServerHelper.get(BASE_URL + "departureDate="+ date + "&flightNumber=" + flight, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray flights = response.getJSONArray("flights");
                    JSONObject obj_flights = flights.getJSONObject(0);
                    JSONObject operatingFlightLeg = obj_flights.getJSONObject("operatingFlightLeg");
                    JSONObject departsFrom = operatingFlightLeg.getJSONObject("departsFrom");
                    JSONObject arrivesOn = operatingFlightLeg.getJSONObject("arrivesOn");

                    String depart_code = departsFrom.getString("IATACode");
                    String arrive_code = arrivesOn.getString("IATACode");
                    String depart_time = operatingFlightLeg.getString("scheduledDepartureDateTime");
                    String arrival_time = operatingFlightLeg.getString("scheduledArrivalDateTime");
                    String flight_status = operatingFlightLeg.getString("flightStatus").replace("_", " ");

                   // Flight flightDetails = new Flight(flight, depart_code, arrive_code, formatDate(depart_time), formatDate(arrival_time), flight_status);

                   /* if (listenerByFlightNumber != null){
                        listenerByFlightNumber.onSearchFlightCompleted(flightDetails);
                    }*/

                }
                catch (JSONException e) {
                    Toast.makeText(c, ERROR_FLIGHT_REQUEST, Toast.LENGTH_SHORT).show();
                }

                pd.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(c, FLIGHT_NOT_FOUND, Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

        });
    }

    public void requestByOriginAndDestination(){
        pd = new CustomProgressDialog(c);
        pd.show();

        ServerHelper.get(BASE_URL + "originAirportCode=" + flight + "&destinationAirportCode=" + date, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    ArrayList<Flight> flightsDetails = new ArrayList<>();
                    JSONArray flights = response.getJSONArray("flights");
                    String carrier_code;
                    String flight_number;
                    String origin;
                    String destination;
                    String departure_time;
                    String arrival_time;
                    String status;

                    for (int i = 0; i < flights.length(); i++){
                        carrier_code = flights.getJSONObject(i).getJSONObject("carrier").getString("code");
                        flight_number = flights.getJSONObject(i).getString("flightNumber");
                        origin = flights.getJSONObject(i).getJSONObject("operatingFlightLeg").getJSONObject("departsFrom").getString("IATACode");
                        destination = flights.getJSONObject(i).getJSONObject("operatingFlightLeg").getJSONObject("arrivesOn").getString("IATACode");
                        departure_time = flights.getJSONObject(i).getJSONObject("operatingFlightLeg").getString("scheduledDepartureDateTime");
                        arrival_time = flights.getJSONObject(i).getJSONObject("operatingFlightLeg").getString("scheduledArrivalDateTime");
                        status = flights.getJSONObject(i).getJSONObject("operatingFlightLeg").getString("flightStatus").replace("_", " ");

                       // flightsDetails.add(new Flight(carrier_code + flight_number, origin, destination, formatDate(departure_time), formatDate(arrival_time), status));
                    }

                    if (listenerByOriginDestination != null){
                        listenerByOriginDestination.onSearchListFlightsCompleted(flightsDetails);
                    }

                } catch (JSONException e) {
                    Toast.makeText(c, ERROR_FLIGHT_REQUEST, Toast.LENGTH_SHORT).show();
                }

                pd.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(c, FLIGHT_NOT_FOUND, Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(c, FLIGHT_NOT_FOUND, Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    public String formatDate(String date){

        String onlyDate = date.substring(0, date.indexOf("T"));
        String onlyTime = date.substring(date.indexOf("T") + 1, 16);
        String formatedDate = onlyDate + " " + TIME_AT + " " + onlyTime;

        return formatedDate;
    }

}
