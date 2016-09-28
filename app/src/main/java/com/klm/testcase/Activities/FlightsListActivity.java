package com.klm.testcase.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.klm.testcase.Adapters.ListFlightsAdapter;
import com.klm.testcase.Constants;
import com.klm.testcase.Objects.Flight;
import com.klm.testcase.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class FlightsListActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    @Bind(R.id.lvList)   ListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Flight> flights = (ArrayList<Flight>) getIntent().getSerializableExtra("flights");

        ListFlightsAdapter adapter = new ListFlightsAdapter(this, flights);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Start new activity to show details about the flight found.
        Flight flight = (Flight) parent.getItemAtPosition(position);
        startActivity(new Intent(this, DetailsActivity.class).putExtra("details", flight));
    }
}
