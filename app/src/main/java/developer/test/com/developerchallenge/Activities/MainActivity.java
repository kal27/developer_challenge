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
import developer.test.com.developerchallenge.Utils.ValidationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ApiClient restClient;
    @BindView(R.id.flightsListView)
    ListView flightsListView;
    @BindView(R.id.arrAirport)
    EditText arrAirport;
    @BindView(R.id.destAirport)
    EditText destAirport;
    @BindView(R.id.dateFromInput)
    EditText dateFromInput;
    @BindView(R.id.dateToInput)
    EditText dateToInput;
    @BindView(R.id.search)
    Button search;
    private List<Flight> flights;
    private final String BASE_URL = "https://murmuring-ocean-10826.herokuapp.com";
    private String arrivalAirport = "DUB", destinationAirport = "BCN";
    private Call<Response> responseCall;

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

    }

    @OnClick(R.id.search)
    void loadFlights() {
        String arrCode = arrAirport.getText().toString();
        String destCode = destAirport.getText().toString();
        String dateFrom = dateFromInput.getText().toString();
        String dateTo = dateToInput.getText().toString();

        if (!((ValidationUtils.validateAirportCode(arrCode, this)) && (ValidationUtils.validateAirportCode(arrCode, this)))) {
            return;
        }
        if (!((ValidationUtils.validateDate(dateFrom, dateFromInput)) && (ValidationUtils.validateDate(dateTo, dateToInput)))) {
            return;
        }

        responseCall = restClient.searchForFlights(arrCode, destCode, dateFrom, dateTo);
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response jsonResponse = response.body();
                if (jsonResponse != null) {
                    if (jsonResponse.getFlights().size() != 0) {
                        //only set the values when we're sure the input was valid i.e the response
                        //sent back is valid
                        arrivalAirport = arrAirport.getText().toString();
                        destinationAirport = destAirport.getText().toString();

                        flights = jsonResponse.getFlights();
                        setAdapter(flights);

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


    private void setAdapter(List<Flight> flights) {
        FlightArrayAdapter flightArrayAdapter = new FlightArrayAdapter(MainActivity.this, flights);
        flightsListView.setAdapter(flightArrayAdapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (responseCall != null) {
            responseCall.cancel();
        }
    }
}
