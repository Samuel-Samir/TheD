package samuel.example.com.thed.model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import samuel.example.com.thed.model.ProductResponse;

/**
 * Created by samuel on 5/30/2017.
 */

public interface ApiInterface {
    @GET("/")
    Call <ProductResponse> getProduct ();

     class ApiClient {

        public static final String BASE_URL = "https://limitless-forest-98976.herokuapp.com";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
