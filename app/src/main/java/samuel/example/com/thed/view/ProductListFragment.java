package samuel.example.com.thed.view;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samuel.example.com.thed.R;
import samuel.example.com.thed.model.Image;
import samuel.example.com.thed.model.Product;
import samuel.example.com.thed.model.ProductResponse;
import samuel.example.com.thed.model.dataBase.ProductContract;
import samuel.example.com.thed.model.dataBase.ProductDbHelper;
import samuel.example.com.thed.model.ApiInterface;
import samuel.example.com.thed.ProductAdapter;
import samuel.example.com.thed.presenter.ProductLIstPresenter;

import static android.os.Looper.getMainLooper;

public class ProductListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener , ProductLIstView {

    private RecyclerView mRecyclerView;
    private ProductAdapter  productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProductLIstPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        presenter = new ProductLIstPresenter();
        presenter.attachView(this);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh) ;
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
        return rootView ;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadProductList();
    }

    @Override
    public void showProductList(List<Product> productList) {
        productAdapter = new ProductAdapter();
        productAdapter.setApiResponse(productList , getActivity());
        mRecyclerView.setAdapter(productAdapter);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        swipeRefreshLayout.setRefreshing(false);
        ProductDbHelper productDbHelper = new ProductDbHelper(getContext());

        presenter.addDataListToDB(productList ,productDbHelper);
    }

    @Override
    public void CallGetDataFromDb() {
        ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
        presenter.loadFromDb(productDbHelper);
    }

    @Override
    public void getDataFromDb(final List<Product> productList) {
        android.os.Handler handler = new android.os.Handler(getMainLooper());

        if (productList==null)
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext() , "Data base empty" ,Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        else
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showProductList(productList);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
