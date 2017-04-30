package developer.test.com.developerchallenge.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import developer.test.com.developerchallenge.R;
import developer.test.com.developerchallenge.ResponseClasses.Flight;
import developer.test.com.developerchallenge.databinding.ActivityFlightDetailsBinding;

public class FlightDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFlightDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_flight_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_flight_details);
        setSupportActionBar(toolbar);

        Flight flight = getIntent().getExtras().getParcelable("flight");
        String arrivalAirport = getIntent().getExtras().getString("arrival");
        String destinationAirport = getIntent().getExtras().getString("destination");

        //one of the requirements was to develop a summary page that would be easily extended
        //that is why data binding was used
        //when the rest api changes i.e it will return more data the only things that will have to
        //be modified will be the Flight class and layout
        //there will be no need to change any code in this activity
        binding.contentFlightDetails.setFlight(flight);
        binding.contentFlightDetails.arrivalAirport.setText(getResources().getString(R.string.arrivalAirport, arrivalAirport.toUpperCase()));
        binding.contentFlightDetails.destinationAirport.setText(getResources().getString(R.string.destinationAirport, destinationAirport.toUpperCase()));

    }


}
