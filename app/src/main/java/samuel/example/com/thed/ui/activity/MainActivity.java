package samuel.example.com.thed.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samuel.example.com.thed.R;
import samuel.example.com.thed.model.Product;
import samuel.example.com.thed.model.ProductResponse;
import samuel.example.com.thed.rest.ApiClient;
import samuel.example.com.thed.rest.ApiInterface;
import samuel.example.com.thed.ui.fragment.ProductListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container ,new ProductListFragment())
                    .commit();
        }
        /*
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call <ProductResponse> call =apiService.getProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                int statusCode = response.code();
                List<Product> productList = response.body().getData();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getBaseContext() , "error" ,Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
