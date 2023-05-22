package com.example.eugene;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Adapter.CartAdapter;
import com.example.eugene.Common.Common;
import com.example.eugene.Database.DatabaseHelper;
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

    FirebaseDatabase database;
    DatabaseReference requests;

    List<Order> cart = new ArrayList<>();

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

        loadListFood();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

                //clean cart
                new DatabaseHelper(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Order Saved", Toast.LENGTH_SHORT).show();

                //redirect to home
                Intent home = new Intent(Cart.this,HomeActivity.class);
                startActivity(home);
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
        cart = new DatabaseHelper(this).getAllOrder();
        cartAdapter = new CartAdapter(this,cart);
        recyclerView.setAdapter(cartAdapter);

        //calculate total all
//        int total = 0;
//        for (Order order:cart)
//            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("in","ID");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//        txtTotalAll.setText(fmt.format(total));
    }

}