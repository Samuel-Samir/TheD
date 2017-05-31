package samuel.example.com.thed.model.dataBase;

import android.provider.BaseColumns;

/**
 * Created by samuel on 5/31/2017.
 */

public class ProductContract {

    public static final class ProductEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_Product_NAME="name";
        public static final String COLUMN_Product_DESCRIPTION="productDescription";
        public static final String COLUMN_Product_PRICE="price";
        public static final String COLUMN_PHOTO_LINK="link";
        public static final String COLUMN_PHOTO_HEIGH="height";
        public static final String COLUMN_PHOTO_WIDTH="width";
    }
}
