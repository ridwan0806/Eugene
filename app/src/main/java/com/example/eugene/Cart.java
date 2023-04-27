package com.example.eugene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eugene.Adapter.CartAdapter;
import com.example.eugene.Database.MyDatabaseHelper;
import com.example.eugene.Model.Order;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;

    MyDatabaseHelper myDb;
//    ArrayList<Order>list;
    ArrayList<String> productId,productName,qty,price,discount;

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerCartList);

        myDb = new MyDatabaseHelper(Cart.this);
        productId = new ArrayList<>();
        productName = new ArrayList<>();
        qty = new ArrayList<>();
        price = new ArrayList<>();
        discount = new ArrayList<>();

        cartAdapter = new CartAdapter(Cart.this,productId,productName,qty,price,discount);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));

        getListCart();
    }

    private void getListCart() {
        Cursor cursor = myDb.readAllData();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "no data display", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                productId.add(cursor.getString(1));
                productName.add(cursor.getString(2));
                qty.add(cursor.getString(3));
                price.add(cursor.getString(4));
                discount.add(cursor.getString(5));
            }
        }
    }
}