package com.klm.testcase.Tasks;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.klm.testcase.Objects.Weather;
import com.klm.testcase.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindString;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class GetDestinationWeather {
    @BindString(R.string.weather_error) String REQUEST_ERROR;

    private final String BASE_URL = "https://api.forecast.io/forecast/";
    private final String FORECAST_API_KEY = "b2bc2f27d7bbdea60923ae2f667e7026/";
    private final String FORECAST_SETTINGS_CELCIUS = "?units=si";

    Activity c;
    String lat;
    String lon;

    LoadingWeatherDetails listener;

    public GetDestinationWeather(Activity c, String lat, String lon) {
        this.c = c;
        this.lat = lat;
        this.lon = lon;
        listener = (LoadingWeatherDetails)  c;
        ButterKnife.bind(this, c);
    }

    public interface LoadingWeatherDetails {
        void onLoadWeatherCompleted(Weather weather);
    }

    public void request(){

        ServerHelper.get(BASE_URL + FORECAST_API_KEY + lat + "," + lon + FORECAST_SETTINGS_CELCIUS, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONObject currently = response.getJSONObject("currently");

                    String temperature = currently.getString("temperature");
                    temperature = temperature.substring(0, temperature.indexOf(".")) + "°";

                    String extra_info = currently.getString("summary");

                    Weather weatherDetails = new Weather(temperature, extra_info);

                    if (listener != null){
                        listener.onLoadWeatherCompleted(weatherDetails);
                    }

                } catch (JSONException e) {
                    Toast.makeText(c, REQUEST_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(c, REQUEST_ERROR, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
