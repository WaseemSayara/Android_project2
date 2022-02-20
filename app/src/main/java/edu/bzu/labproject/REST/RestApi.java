package edu.bzu.labproject.REST;

import java.util.List;

import edu.bzu.labproject.Models.House;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {
    final String BASE_URL = "https://run.mocky.io/v3/fbd0c48c-aff2-483b-be50-727a21f99222/";
    final String GET_URL = "data.json?alt=media&token=503ffc5e-9131-4572-ab9f-49910746c63f&fbclid=IwAR1cQNskCN9NPELPbKkRsWvfNiSlY1lu4wZRcQdaD3bGjZ8C-3HzieMc--8";

    @GET(value = GET_URL)
    Call<List<House>> getHouses();
}
