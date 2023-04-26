package com.example.eugene.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.eugene.Model.Order;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ag5sOrders";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "OrderDetail";

    private static final String COLUMN_ID = "_id";

    private static final String COLUMN_PRODUCT_ID = "ProductId";
    private static final String COLUMN_PRODUCT_NAME = "ProductName";
    private static final String COLUMN_QUANTITY = "Quantity";
    private static final String COLUMN_PRICE = "Price";
    private static final String COLUMN_DISCOUNT = "Discount";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PRODUCT_ID + " TEXT, " +
                        COLUMN_PRODUCT_NAME + " TEXT, " +
                        COLUMN_QUANTITY + " TEXT, " +
                        COLUMN_PRICE + " TEXT, " +
                        COLUMN_DISCOUNT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addToCart(Order order)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCT_ID, order.getProductId());
        cv.put(COLUMN_PRODUCT_NAME, order.getProductName());
        cv.put(COLUMN_QUANTITY, order.getQuantity());
        cv.put(COLUMN_PRICE, order.getPrice());
        cv.put(COLUMN_DISCOUNT, order.getDiscount());
        db.insert(TABLE_NAME,null,cv);
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor =  db.rawQuery(query,null);
        }
        return cursor;
    }
}
