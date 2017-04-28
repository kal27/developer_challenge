package developer.test.com.developerchallenge.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import developer.test.com.developerchallenge.R;
import developer.test.com.developerchallenge.ResponseClasses.Flight;

public class FlightDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Flight details");
        setSupportActionBar(toolbar);

        Flight flight = getIntent().getExtras().getParcelable("flight");

        Toast.makeText(this, flight.getDateFrom(), Toast.LENGTH_SHORT).show();


    }

}
