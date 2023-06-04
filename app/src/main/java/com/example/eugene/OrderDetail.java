package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.eugene.Adapter.CashierProcessOrderDetailAdapter;
import com.example.eugene.Model.OrderItems;
import com.example.eugene.Model.Orders;
import com.example.eugene.Model.RequestOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference order;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    String orderId = "";
    RequestOrder currentOrder;
    TextView orderName,orderKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderKey = findViewById(R.id.txtOrderDetailOrderId);
        orderName = findViewById(R.id.txtOrderDetailNameCust);

        recyclerView = findViewById(R.id.recyclerOrderProcessOrderItem);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        order = database.getReference("Orders");

        if (getIntent() != null)
            orderId = getIntent().getStringExtra("OrderId");
        if (!orderId.isEmpty())
        {
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

                List<Orders> orderItem = new ArrayList<>();
//                System.out.println(orderItem); CEK LOOPING

                for (DataSnapshot ds : snapshot.getChildren()){
                        if (ds.exists()){
                            for (DataSnapshot dataSnapshot:ds.getChildren()){
                                if (dataSnapshot.exists()){
                                    orderItem.add(dataSnapshot.getValue(Orders.class));
//                                    System.out.println(orderItem); CEK LOOPING
                                }
                            }
                        }
                }

                final CashierProcessOrderDetailAdapter adapter = new CashierProcessOrderDetailAdapter(OrderDetail.this,orderItem);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}