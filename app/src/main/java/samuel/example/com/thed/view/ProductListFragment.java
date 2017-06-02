package samuel.example.com.thed.view;



import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;



import samuel.example.com.thed.R;
import samuel.example.com.thed.model.NetworkChangeReceiver;
import samuel.example.com.thed.model.container.Product;
import samuel.example.com.thed.model.container.ProductResponse;
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
    public static final String BUNDLE_ARGUMENT ="bundle";
    public static final String PRODUCT_FRAGMENT ="ProductListFragment";
    public static final String PRODUCT_FRAGMENT_TAG ="ProductListFragmentTag";




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
                bundle.putParcelable(BUNDLE_ARGUMENT,productList.get(position));
                productDetailsFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container ,productDetailsFragment ,PRODUCT_FRAGMENT)
                        .addToBackStack(PRODUCT_FRAGMENT_TAG)
                        .commit();
            }
        });

        NetworkChangeReceiver.setNetworkAvailable(new NetworkChangeReceiver.NetworkAvailable() {
            @Override
            public void networkIsAvailable() {
               onRefresh();
            }
        });

        if(savedInstanceState==null)
        {
            onRefresh();

        }
        else if (savedInstanceState!=null){

            ProductResponse productResponse ;
            productResponse =   savedInstanceState.getParcelable(SAVE_INSTANCESTATE_ARGUMENT);
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
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(portrait, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(sglm);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(landScape, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(sglm);
        }
        mRecyclerView.setAdapter(productAdapter);

    }
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadProductList();
    }

    public void addDataToRecyclerView (List<Product> productList)
    {
        this.productList = productList ;
        productAdapter.setApiResponse(productList );
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showProductList(List<Product> productList) {

        addDataToRecyclerView(productList);
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
                    Toast.makeText(getContext() , getResources().getString(R.string.error),Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        else
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    addDataToRecyclerView(productList);

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
