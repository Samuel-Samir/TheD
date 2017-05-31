package samuel.example.com.thed.model.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import samuel.example.com.thed.model.dataBase.ProductContract.ProductEntry;

/**
 * Created by samuel on 5/31/2017.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="products.dp";
    private static final int DATABASE_VERSION = 1;

    public ProductDbHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PHOTO_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ( "+
                ProductEntry.COLUMN_Product_NAME +" TEXT NOT NULL, "+
                ProductEntry.COLUMN_Product_DESCRIPTION +" TEXT NOT NULL, "+
                ProductEntry.COLUMN_Product_PRICE +" REAL NOT NULL, "+
                ProductEntry.COLUMN_PHOTO_LINK +" TEXT NOT NULL, "+
                ProductEntry.COLUMN_PHOTO_WIDTH +" INTEGER NOT NULL, "+
                ProductEntry.COLUMN_PHOTO_HEIGH  +" INTEGER NOT NULL "+
                ");";

        db.execSQL(SQL_CREATE_PHOTO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME );
        onCreate(db);
    }



}
