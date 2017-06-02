package samuel.example.com.thed.view;

import java.util.List;

import samuel.example.com.thed.model.container.Product;

/**
 * Created by samuel on 5/31/2017.
 */

public interface ProductLIstView  {

    void showProductList (List<Product> productList);
    void CallGetDataFromDb ();
    void getDataFromDb (List<Product> productList);

}
