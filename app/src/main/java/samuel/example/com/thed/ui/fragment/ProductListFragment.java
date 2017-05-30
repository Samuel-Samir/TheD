package samuel.example.com.thed.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import samuel.example.com.thed.ui.adapter.ProductAdapter;

public class ProductListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call =apiService.getProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                int statusCode = response.code();
                List<Product> productList = response.body().getData();
                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                ProductAdapter  productAdapter = new ProductAdapter();
                productAdapter.setApiResponse(productList , getActivity());
                mRecyclerView.setAdapter(productAdapter);
                StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(sglm);


            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getActivity() , "error" ,Toast.LENGTH_SHORT).show();
            }
        });
        return rootView ;
    }

}
