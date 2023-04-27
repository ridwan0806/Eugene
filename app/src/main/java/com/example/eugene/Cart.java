package com.example.eugene;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Adapter.CartAdapter;
import com.example.eugene.Common.Common;
import com.example.eugene.Database.MyDatabaseHelper;
import com.example.eugene.Model.Order;
import com.example.eugene.Model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

//    MyDatabaseHelper myDb;
    FirebaseDatabase database;
    DatabaseReference requests;

    List<Order> cart = new ArrayList<>();
    ArrayList<String> productId,productName,qty,price,discount;

    CartAdapter cartAdapter;

    TextView txtTotalAll,btnSubmitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.recyclerCartList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalAll = findViewById(R.id.txtTotalAll);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

//        myDb = new MyDatabaseHelper(Cart.this);
//        productId = new ArrayList<>();
//        productName = new ArrayList<>();
//        qty = new ArrayList<>();
//        price = new ArrayList<>();
//        discount = new ArrayList<>();

//        cartAdapter = new CartAdapter(Cart.this,list);
//        recyclerView.setAdapter(cartAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));

//        getListCart();
        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One More Step !");
        alertDialog.setMessage("Enter Customer Name: ");

        final EditText edtCustomerName = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtCustomerName.setLayoutParams(lp);
        alertDialog.setView(edtCustomerName);
        alertDialog.setIcon(R.drawable.shopping_cart);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Common.currentUser.getName(),
                        edtCustomerName.getText().toString(),
                        txtTotalAll.getText().toString(),
                        cart
                );

                //submit to firebase (using system.CurrentMilli for key)
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListFood() {
        cart = new MyDatabaseHelper(this).getAllOrder();
        cartAdapter = new CartAdapter(this,cart);
        recyclerView.setAdapter(cartAdapter);

        //calculate total all
        int total = 0;
        for (Order order:cart)
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalAll.setText(fmt.format(total));
    }

//    private void getListCart() {
//        Cursor cursor = myDb.readAllData();
//        if (cursor.getCount() == 0)
//        {
//            Toast.makeText(this, "no data display", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        else
//        {
//            while (cursor.moveToNext())
//            {
//                productId.add(cursor.getString(1));
//                productName.add(cursor.getString(2));
//                qty.add(cursor.getString(3));
//                price.add(cursor.getString(4));
//                discount.add(cursor.getString(5));
//            }
//        }
//    }
}