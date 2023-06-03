package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.eugene.Model.RequestOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetail extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference order;

    String orderId = "";
    RequestOrder currentOrder;
    TextView orderName,orderKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderKey = findViewById(R.id.txtOrderDetailOrderId);
        orderName = findViewById(R.id.txtOrderDetailNameCust);

        database = FirebaseDatabase.getInstance();
        order = database.getReference("Orders");

        if (getIntent() != null)
            orderId = getIntent().getStringExtra("OrderId");
        if (!orderId.isEmpty())
        {
//            orderKey.setText(orderId);
            getDetailOrder(orderId);
        }
    }

    private void getDetailOrder(String orderId) {
        order.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentOrder = snapshot.getValue(RequestOrder.class);
                orderName.setText(currentOrder.getNameCustomer());
                orderKey.setText(orderId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}