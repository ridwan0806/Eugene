package com.example.eugene.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.eugene.Model.Order;
import com.example.eugene.Model.Orders;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "ag5sOrder";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "OrderItems";

    private static final String COLUMN_ID = "_id";

    private static final String COLUMN_PRODUCT_ID = "ProductId";
    private static final String COLUMN_PRODUCT_NAME = "ProductName";
    private static final String COLUMN_QUANTITY = "Quantity";
    private static final String COLUMN_PRICE = "Price";
    private static final String COLUMN_SUBTOTAL = "Subtotal";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_ID + " TEXT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_QUANTITY + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_SUBTOTAL + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int checkItemExist(String row_id)
    {
        int check = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT ProductId FROM OrderItems WHERE ProductId = ?",new String[] {row_id});
        if (result.moveToFirst()) {
            return check;
        }
        else {
            check = 1;
        }

        return check;
    }

    public void addToCart(Orders order)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCT_ID, order.getProductId());
        cv.put(COLUMN_PRODUCT_NAME, order.getProductName());
        cv.put(COLUMN_QUANTITY, order.getQuantity());
        cv.put(COLUMN_PRICE, order.getPrice());
        cv.put(COLUMN_SUBTOTAL, order.getSubtotal());

        db.insert(TABLE_NAME,null,cv);
    }

    @SuppressLint("Range")
    public List<Orders> getAllOrder() {
        List<Orders> orderList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Orders order = new Orders();
                order.setId(cursor.getString(cursor.getColumnIndex("_id")));
                order.setProductId(cursor.getString(cursor.getColumnIndex("ProductId")));
                order.setProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
                order.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Quantity"))));
                order.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price"))));
                order.setSubtotal(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Subtotal"))));
                orderList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orderList;
    }

    public void cleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

    public void updateCart(String row_id, String price, String qty)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRICE,price);
        cv.put(COLUMN_QUANTITY,qty);

        int result = db.update(TABLE_NAME,cv,"_id=?",new String[] {row_id});

        if (result == -1){
            Toast.makeText(context, "Data gagal diubah", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCart(String Row_id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,"_id=?",new String[] {Row_id});

        if (result == -1){
            Toast.makeText(context, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        }
    }
}
