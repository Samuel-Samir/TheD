package samuel.example.com.thed.view;



import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;



import samuel.example.com.thed.R;
import samuel.example.com.thed.model.Product;
import samuel.example.com.thed.model.ProductResponse;
import samuel.example.com.thed.model.dataBase.ProductDbHelper;
import samuel.example.com.thed.ProductAdapter;
import samuel.example.com.thed.presenter.ProductLIstPresenter;

import static android.os.Looper.getMainLooper;

public class ProductListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener , ProductLIstView {

    private RecyclerView mRecyclerView;
    private ProductAdapter  productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProductLIstPresenter presenter;
    private List<Product> productList ;
    public static final String SAVE_INSTANCESTATE_ARGUMENT ="response";

    ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        presenter = new ProductLIstPresenter();
        presenter.attachView(this);
        productAdapter = new ProductAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        onOrientationChange(getResources().getConfiguration().orientation);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh) ;
        swipeRefreshLayout.setOnRefreshListener(this);
        productAdapter.setRecyclerViewCallback(new ProductAdapter.RecyclerViewCallback() {
            @Override
            public void onItemClick(int position) {
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                Bundle bundle =new Bundle();
                bundle.putParcelable("sasa",productList.get(position));
                productDetailsFragment.setArguments(bundle);
                ((FragmentActivity)getActivity()).getSupportFragmentManager()
                        .beginTransaction().addToBackStack("displayPhotoFragment").replace(R.id.container ,productDetailsFragment)
                        .commit();
            }
        });

        if(savedInstanceState==null)
        {
            onRefresh();

        }
        else if (savedInstanceState!=null){

            ProductResponse productResponse ;
            productResponse =  (ProductResponse) savedInstanceState.getParcelable(SAVE_INSTANCESTATE_ARGUMENT);
            if(productResponse.getData()!=null)
            {
                productList = productResponse.getData();
                productAdapter.setApiResponse(productList );
            }

        }
        return rootView ;
    }

    public void onOrientationChange(int orientation){
        int landScape=3;
        int portrait= 2;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        if (widthPixels>=1023 || heightPixels>=1023)
        {
            landScape=4;
            portrait=3;
        }

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(portrait, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(sglm);
            mRecyclerView.setAdapter(productAdapter);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(landScape, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(sglm);
            mRecyclerView.setAdapter(productAdapter);
        }
    }
    @Override
    public void onRefresh() {
        Log.e("sasas - > " , "5");
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadProductList();
    }

    @Override
    public void showProductList(List<Product> productList) {
        this.productList = productList ;
        productAdapter.setApiResponse(productList );

        swipeRefreshLayout.setRefreshing(false);

        ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
        // presenter.addDataListToDB(productList ,productDbHelper);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ProductResponse productResponse =new ProductResponse();
        productResponse.setData(productList);
        outState.putParcelable(SAVE_INSTANCESTATE_ARGUMENT ,  productResponse);
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
