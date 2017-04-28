package developer.test.com.developerchallenge.Utils;


import developer.test.com.developerchallenge.ResponseClasses.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClient {

    @GET("/en/api/2/flights/from/{from}/to/{to}/{fromDate}/{toDate}/250/unique/?limit=15")
    public Call<Response> searchForFlights(@Path("from") String from, @Path("to") String to,
                                           @Path("fromDate") String fromDate, @Path("toDate") String toDate);
}
