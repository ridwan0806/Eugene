package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Model.Orders;
import com.example.eugene.Model.RequestOrders;
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
    RequestOrders currentOrder;
    TextView orderName,orderKey,editQtyOrder;
    ImageView plusBtn,minusBtn;
    int numberOrder = 1;

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
                currentOrder = snapshot.getValue(RequestOrders.class);
                orderName.setText(currentOrder.getNameCustomer());
                orderKey.setText(orderId);

                List<Orders> orderItem = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()){
//                    System.out.println(ds);
                        if (ds.exists()){
                            for (DataSnapshot dataSnapshot:ds.getChildren()){
                                if (dataSnapshot.exists()){
                                    orderItem.add(dataSnapshot.getValue(Orders.class));
//                                    System.out.println(dataSnapshot);
                                }
                            }
                        }
                }
                
                // RecyclerView orderDetail
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
                                            String orderItemId = adapter.getRef(position).getKey();
                                            String qty = String.valueOf(adapter.getItem(position).getQuantity());
                                            String price = String.valueOf(adapter.getItem(position).getPrice());
                                            editQuantity(orderId,orderItemId,qty,price);
                                        } else if (itemId == R.id.cashier_item_delete_food){
                                            AlertDialog.Builder confirm = new AlertDialog.Builder(OrderDetail.this);
                                            confirm.setCancelable(false);

                                            confirm.setTitle("Peringatan");
                                            confirm.setMessage("Hapus item ini ?");

                                            confirm.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String orderItemId = adapter.getRef(position).getKey();
                                                    deleteItem(orderItemId);
                                                }
                                            });

                                            confirm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            confirm.show();
                                        }
                                        return true;
                                    }
                                });
                                popupMenu.show();
                            }

                            private void deleteItem(String orderItemId) {
                                order.child(orderId).child("orderDetail").child(orderItemId).removeValue();

                                // RE-Calculate Subtotal Item & Price (Order Parent)
                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference newRef = rootRef.child("Orders");
                                ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int newSubtotalItem = 0;
                                        double newSubtotalPrice = 0;
                                        for (DataSnapshot ds:snapshot.child(orderId).child("orderDetail").getChildren()){
                                            int subtotalItem = ds.child("quantity").getValue(int.class);
                                            double subtotalPrice = Double.valueOf(ds.child("subtotal").getValue(Long.class));

                                            newSubtotalItem = newSubtotalItem + subtotalItem;
                                            newSubtotalPrice = newSubtotalPrice + subtotalPrice;
                                        }
                                        // Update Subtotal Item & Price in Database
                                        order.child(orderId).child("subtotalItem").setValue(newSubtotalItem);
                                        order.child(orderId).child("subtotalPrice").setValue(newSubtotalPrice);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                };
                                newRef.addListenerForSingleValueEvent(valueEventListener);
                                Toast.makeText(OrderDetail.this, "Item berhasil dihapus", Toast.LENGTH_SHORT).show();
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

    private void editQuantity(String orderId, String orderItemId, String qty, String price) {
            AlertDialog.Builder editQtyDialogue = new AlertDialog.Builder(OrderDetail.this);
            editQtyDialogue.setCancelable(false);

            editQtyDialogue.setMessage("Ubah Jumlah Pesanan");
            LayoutInflater layoutInflater = this.getLayoutInflater();
            View editQtyLayout = layoutInflater.inflate(R.layout.orderdetail_edit_qty,null);
            editQtyDialogue.setView(editQtyLayout);

            editQtyOrder = editQtyLayout.findViewById(R.id.orderDetail_qty);
            plusBtn = editQtyLayout.findViewById(R.id.orderDetail_BtnAddQty);
            minusBtn = editQtyLayout.findViewById(R.id.orderDetail_BtnDeleteQty);

            editQtyOrder.setText(qty);
            numberOrder = Integer.parseInt(qty);

            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numberOrder = numberOrder + 1;
                    editQtyOrder.setText(String.valueOf(numberOrder));
                }
            });

            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (numberOrder > 1){
                        int newNumberOrder = Integer.parseInt(editQtyOrder.getText().toString());
                        numberOrder = newNumberOrder - 1;
                        editQtyOrder.setText(String.valueOf(numberOrder));
                    }
                }
            });

            editQtyDialogue.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int lastQty = Integer.parseInt(qty);
                    double newSubtotal = 0;

                    if (lastQty == numberOrder){
                        Toast.makeText(OrderDetail.this, "Failed.. Jumlah Masih Sama", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update Qty (Order Item)
                        order.child(orderId).child("orderDetail").child(orderItemId).child("quantity").setValue(numberOrder);

                        // Update Subtotal Price (Order Item)
                        newSubtotal = numberOrder * Double.parseDouble(price);
                        order.child(orderId).child("orderDetail").child(orderItemId).child("subtotal").setValue(newSubtotal);

                        // RE-Calculate Subtotal Item & Price (Order Parent)
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference newRef = rootRef.child("Orders");
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int newSubtotalItem = 0;
                                double newSubtotalPrice = 0;
                                for (DataSnapshot ds:snapshot.child(orderId).child("orderDetail").getChildren()){
                                    int subtotalItem = ds.child("quantity").getValue(int.class);
                                    double subtotalPrice = Double.valueOf(ds.child("subtotal").getValue(Long.class));

                                    newSubtotalItem = newSubtotalItem + subtotalItem;
                                    newSubtotalPrice = newSubtotalPrice + subtotalPrice;
                                }
                                // Update Subtotal Item & Price in Database
                                order.child(orderId).child("subtotalItem").setValue(newSubtotalItem);
                                order.child(orderId).child("subtotalPrice").setValue(newSubtotalPrice);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                throw error.toException();
                            }
                        };
                        newRef.addListenerForSingleValueEvent(valueEventListener);
                        Toast.makeText(OrderDetail.this, "Jumlah berhasil di update", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            editQtyDialogue.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            editQtyDialogue.show();

    }
}