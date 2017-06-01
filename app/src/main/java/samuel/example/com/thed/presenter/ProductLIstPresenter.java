package samuel.example.com.thed.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samuel.example.com.thed.model.ApiInterface;
import samuel.example.com.thed.model.Image;
import samuel.example.com.thed.model.Product;
import samuel.example.com.thed.model.ProductResponse;
import samuel.example.com.thed.model.dataBase.ProductContract;
import samuel.example.com.thed.model.dataBase.ProductDbHelper;
import samuel.example.com.thed.view.ProductLIstView;
import rx.Subscription;

import static android.os.Looper.getMainLooper;

/**
 * Created by samuel on 5/31/2017.
 */

public class ProductLIstPresenter implements Presenter<ProductLIstView>{

    private ProductLIstView  productLIstView;
    private Subscription subscription;
    @Override
    public void attachView(ProductLIstView view) {
        this.productLIstView =view;
    }

    @Override
    public void detachView() {
       this.productLIstView = null;
        if(subscription!=null)
        {
            subscription.unsubscribe();
        }
    }

    public void loadProductList ()
    {
        ApiInterface apiService = ApiInterface.ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call =apiService.getProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> productList = response.body().getData();
                if (productList!=null)
                {
                    productLIstView.showProductList(productList);
                }

                else
                {
                    productLIstView.CallGetDataFromDb();
                }

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

                productLIstView.CallGetDataFromDb();

            }
        });
    }


    public void loadFromDb (final ProductDbHelper productDbHelper )
    {

        Thread thread = new Thread() {
            @Override
            public void run() {
                final List<Product> data = new ArrayList<Product>();
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
                android.os.Handler handler = new android.os.Handler(getMainLooper());

                if (cursor.getCount()< 1)
                {
                    productLIstView.getDataFromDb(null);
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
                    productLIstView.getDataFromDb(data);

                }
            }
        };
        thread.start();
    }



    public void addDataListToDB (final List<Product> products , final ProductDbHelper productDbHelper)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
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


}
