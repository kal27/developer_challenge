package developer.test.com.developerchallenge.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import developer.test.com.developerchallenge.R;
import developer.test.com.developerchallenge.ResponseClasses.Flight;
import developer.test.com.developerchallenge.ResponseClasses.Response;
import developer.test.com.developerchallenge.Utils.ApiClient;
import developer.test.com.developerchallenge.Utils.FlightArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ApiClient restClient;
    @BindView(R.id.flightsListView)
    ListView flightsListView;
    @BindView(R.id.fromInput)
    EditText fromInput;
    @BindView(R.id.toInput)
    EditText toInput;
    @BindView(R.id.dateFromInput)
    EditText dateFromInput;
    @BindView(R.id.dateToInput)
    EditText dateToInput;
    @BindView(R.id.search)
    Button search;
    private List<Flight> flights;
    private final String BASE_URL = "https://murmuring-ocean-10826.herokuapp.com";
    private String arrivalAirport = "DUB", destinationAirport="BCN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restClient = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiClient.class);

        loadFlights();

    }

    @OnClick(R.id.search)
    void loadFlights() {
        Call<Response> responseCall = restClient.searchForFlights(fromInput.getText().toString(), toInput.getText().toString()
                , dateFromInput.getText().toString(), dateToInput.getText().toString());

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Response jsonResponse = response.body();
                if (jsonResponse != null) {
                    if (jsonResponse.getFlights().size() != 0) {
                        //only set the values when we're sure the input was valid i.e the response
                        //sent back is valid
                        arrivalAirport = fromInput.getText().toString();
                        destinationAirport = toInput.getText().toString();
                        flights = jsonResponse.getFlights();

                        FlightArrayAdapter flightArrayAdapter = new FlightArrayAdapter(MainActivity.this, flights);
                        flightsListView.setAdapter(flightArrayAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No results for your search. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.flightsListView)
    void onListItemClick(int position) {
        Flight flight = flights.get(position);
        Intent intent = new Intent(MainActivity.this, FlightDetailsActivity.class);
        intent.putExtra("flight", flight);
        intent.putExtra("arrival", arrivalAirport);
        intent.putExtra("destination", destinationAirport);
        startActivity(intent);
    }


}
