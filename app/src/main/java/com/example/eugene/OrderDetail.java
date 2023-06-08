package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Model.Orders;
import com.example.eugene.Model.RequestOrder;
import com.example.eugene.ViewHolder.OrderDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    FirebaseRecyclerAdapter<Orders, OrderDetailViewHolder>adapter;

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

    @Override
    protected void onStart() {
        super.onStart();
        getDetailOrder(orderId);
    }

    private void getDetailOrder(String orderId) {
        order.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentOrder = snapshot.getValue(RequestOrder.class);
                orderName.setText(currentOrder.getNameCustomer());
                orderKey.setText(orderId);

                List<Orders> orderItem = new ArrayList<>();

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
                
                // CREATE RECYCLER VIEW ORDER ITEM(S)..
                FirebaseRecyclerOptions<Orders> options =
                        new FirebaseRecyclerOptions.Builder<Orders>()
                                .setQuery(order.child(orderId).child("orderDetail"),Orders.class)
                                .build();
                adapter = new FirebaseRecyclerAdapter<Orders, OrderDetailViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderDetailViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Orders model) {
                        holder.foodNameDetailItem.setText(model.getProductName());
                        holder.qtyDetailItem.setText(String.valueOf(model.getQuantity()));
                        holder.priceDetailItem.setText(String.valueOf(model.getPrice()));
                        holder.subtotalDetailItem.setText(String.valueOf(model.getSubtotal()));

                        holder.btnMenuDetailItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popupMenu = new PopupMenu(OrderDetail.this,holder.btnMenuDetailItem);
                                popupMenu.getMenuInflater().inflate(R.menu.cashier_process_status_detail_item,popupMenu.getMenu());
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        int itemId = menuItem.getItemId();
                                        if (itemId == R.id.cashier_item_edit_price){
                                            Toast.makeText(OrderDetail.this, "edit price", Toast.LENGTH_SHORT).show();
                                        } else if (itemId == R.id.cashier_item_edit_qty){
                                            Toast.makeText(OrderDetail.this, "edit qty", Toast.LENGTH_SHORT).show();
                                        } else if (itemId == R.id.cashier_item_delete_food){
                                            // ADD CONFIRM DIALOGUE HERE
                                            deleteItem(position);
                                        }
                                        return true;
                                    }
                                });
                                popupMenu.show();
                            }

                            private void deleteItem(int position) {
//                                String query = String.valueOf(order.child(orderId).child("orderDetail").child(String.valueOf(position)));
//                                System.out.println(query); CHECK RESULT..
                                order.child(orderId).child("orderDetail").child(String.valueOf(position)).removeValue();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.viewholder_cashierprocessdetail, parent, false);
                        return new OrderDetailViewHolder(view);
                    }
                };
                adapter.startListening();
                recyclerView.setAdapter(adapter);
              // END RECYCLER VIEW ORDER ITEM(S)..
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}