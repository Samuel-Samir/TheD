package samuel.example.com.thed.ui.fragment;


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
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samuel.example.com.thed.R;
import samuel.example.com.thed.model.Image;
import samuel.example.com.thed.model.Product;
import samuel.example.com.thed.model.ProductResponse;
import samuel.example.com.thed.model.dataBase.ProductContract;
import samuel.example.com.thed.model.dataBase.ProductDbHelper;
import samuel.example.com.thed.rest.ApiClient;
import samuel.example.com.thed.rest.ApiInterface;
import samuel.example.com.thed.ui.adapter.ProductAdapter;

import static android.os.Looper.getMainLooper;

public class ProductListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ProductAdapter  productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
         mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
         swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh) ;
         swipeRefreshLayout.setOnRefreshListener(this);
         swipeRefreshLayout.setRefreshing(true);
         onRefresh();
        return rootView ;
    }

    @Override
    public void onRefresh() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call =apiService.getProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                int statusCode = response.code();
                List<Product> productList = response.body().getData();
                addProductListToDB (productList);

                productAdapter = new ProductAdapter();
                productAdapter.setApiResponse(productList , getActivity());
                mRecyclerView.setAdapter(productAdapter);
                StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(sglm);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getActivity() , "network error ... load from db" ,Toast.LENGTH_SHORT).show();
                getProductListFromDB();
            }
        });

    }


////////////////////////////////////////////////////////////////
    private void addProductListToDB (final List<Product> products)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
                SQLiteDatabase sqLiteDatabase = productDbHelper.getWritableDatabase();
                long index ;
                for (int i=0 ; i<products.size();i++)
                {
                    Product product = products.get(i);
                    ContentValues cv = new ContentValues();
                    cv.put(ProductContract.ProductEntry.COLUMN_Product_NAME, product.getName());
                    cv.put(ProductContract.ProductEntry.COLUMN_Product_DESCRIPTION, product.getProductDescription());
                    cv.put(ProductContract.ProductEntry.COLUMN_Product_PRICE, product.getPrice());
                    cv.put(ProductContract.ProductEntry.COLUMN_PHOTO_LINK,  product.getImage().getLink());
                    cv.put(ProductContract.ProductEntry.COLUMN_PHOTO_WIDTH, product.getImage().getWidth());
                    cv.put(ProductContract.ProductEntry.COLUMN_PHOTO_HEIGH, product.getImage().getHeight());
                    index = sqLiteDatabase.insert(ProductContract.ProductEntry.TABLE_NAME, null, cv);
                    Log.d("db_index = " , String.valueOf(index));

                }

            }
        };
        thread.start();
    }


    private void getProductListFromDB()
    {


        Thread thread = new Thread() {
            @Override
            public void run() {
                final List<Product> data = new ArrayList<Product>();
                ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
                SQLiteDatabase sqLiteDatabase = productDbHelper.getReadableDatabase();

                Cursor cursor =   sqLiteDatabase.query(
                        ProductContract.ProductEntry.TABLE_NAME,
                        null,
                        null ,
                        null,
                        null,
                        null,
                        null
                );

                int productNameIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_Product_NAME);
                int productDescriptionIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_Product_DESCRIPTION);
                int productPriceIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_Product_PRICE);
                int productPhotoLink = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHOTO_LINK);
                int productPhotoWidth = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHOTO_WIDTH);
                int productPhotoHeigh = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHOTO_HEIGH);
                if (cursor.getCount()< 1)
                {

                }
                else {
                    while (cursor.moveToNext()) {
                        Product product = new Product();
                        Image image = new Image();
                        product.setName(cursor.getString(productNameIndex));
                        product.setProductDescription(cursor.getString(productDescriptionIndex));
                        product.setPrice(cursor.getDouble(productPriceIndex));
                        image.setLink(cursor.getString(productPhotoLink));
                        image.setWidth(cursor.getInt(productPhotoWidth));
                        image.setHeight(cursor.getInt(productPhotoHeigh));
                        product.setImage(image);
                        data.add(product);
                    }
                    cursor.close();
                    android.os.Handler handler = new android.os.Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            productAdapter = new ProductAdapter();
                            productAdapter.setApiResponse(data, getActivity());
                            mRecyclerView.setAdapter(productAdapter);
                            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            mRecyclerView.setLayoutManager(sglm);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        };
        thread.start();

    }

/*
    private void getProductListFromDB()
    {
        ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
        SQLiteDatabase sqLiteDatabase = productDbHelper.getReadableDatabase();

        Cursor cursor =   sqLiteDatabase.query(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                null ,
                null,
                null,
                null,
                null
        );

        int productNameIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_Product_NAME);
        int productDescriptionIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_Product_DESCRIPTION);
        int productPriceIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_Product_PRICE);
        int productPhotoLink = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHOTO_LINK);
        int productPhotoWidth = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHOTO_WIDTH);
        int productPhotoHeigh = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHOTO_HEIGH);
        List<Product> data = new ArrayList<Product>();
        while (cursor.moveToNext()){
            Product product = new Product();
            Image image = new Image();
            product.setName( cursor.getString(productNameIndex) );
            product.setProductDescription( cursor.getString(productDescriptionIndex) );
            product.setPrice( cursor.getDouble(productPriceIndex) );
            image.setLink( cursor.getString(productPhotoLink)) ;
            image.setWidth( cursor.getInt(productPhotoWidth));
            image.setHeight( cursor.getInt(productPhotoHeigh));
            product.setImage(image);
            data.add(product);
        }
        cursor.close();
        productAdapter = new ProductAdapter();
        productAdapter.setApiResponse(data , getActivity());
        mRecyclerView.setAdapter(productAdapter);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        swipeRefreshLayout.setRefreshing(false);
    }

 */

}
