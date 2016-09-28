package com.klm.testcase.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.klm.testcase.Adapters.NewsAdapter;
import com.klm.testcase.Constants;
import com.klm.testcase.Objects.Flight;
import com.klm.testcase.Objects.News;
import com.klm.testcase.Objects.Weather;
import com.klm.testcase.R;
import com.klm.testcase.Tasks.GetDestinationImage;
import com.klm.testcase.Tasks.GetDestinationNews;
import com.klm.testcase.Tasks.GetDestinationWeather;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class DetailsActivity extends AppCompatActivity implements GetDestinationWeather.LoadingWeatherDetails, GetDestinationNews.LoadingDestinationNews{
    @Bind(R.id.ivPhoto)        ImageView ivPhoto;
    @Bind(R.id.tvDegrees)      TextView tvDegrees;
    @Bind(R.id.tvWeather)      TextView tvExtra;
    @Bind(R.id.tvDeparture)    TextView tvDeparture;
    @Bind(R.id.tvArrival)      TextView tvArrival;
    @Bind(R.id.tvDepartDate)   TextView tvDepartDate;
    @Bind(R.id.tvArrivalDate)  TextView tvArrivalDate;
    @Bind(R.id.tvStatus)       TextView tvStatus;
    @Bind(R.id.lvNews)         ListView lvNews;
    @Bind(R.id.tvBack)         TextView tvBack;

    Flight details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        details = (Flight) getIntent().getSerializableExtra("details");

        initUI();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        initUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initUI(){

        tvDeparture.setText(details.getOperatingFlightLeg().getDepartsFrom().getIATACode());
        tvArrival.setText(details.getOperatingFlightLeg().getArrivesOn().getIATACode());
        tvDepartDate.setText(details.getOperatingFlightLeg().getScheduledDepartureDateTime());
        tvArrivalDate.setText(details.getOperatingFlightLeg().getScheduledArrivalDateTime());
        tvStatus.setText(details.getOperatingFlightLeg().getFlightStatus());

        String destination_code = details.getOperatingFlightLeg().getArrivesOn().getIATACode();
        String destination_lat = details.getLatitude(destination_code);
        String destination_lon = details.getLongitude(destination_code);
        String destination_city = details.searchAirport(destination_code);

        //Load destination photo.
        new GetDestinationImage(this, destination_code, ivPhoto).execute();

        //Load info about weather from Forecast.io
        new GetDestinationWeather(this, destination_lat, destination_lon).request();

        //Load news about destination from Achemy
        new GetDestinationNews(this, destination_city).request();

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Method called when all data about weather is fetched.
     */
    @Override
    public void onLoadWeatherCompleted(Weather weather) {
        tvDegrees.setText(weather.getDegrees());
        tvExtra.setText(weather.getExtra_info());
    }

    @Override
    public void onLoadNewsCompleted(final ArrayList<News> news) {
        NewsAdapter adapter = new NewsAdapter(this, news);
        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = news.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
