package samuel.example.com.thed.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import samuel.example.com.thed.model.ProductResponse;

/**
 * Created by samuel on 5/30/2017.
 */

public interface ApiInterface {
    @GET("/")
    Call <ProductResponse> getProduct ();
}
