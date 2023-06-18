package com.example.eugene.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.Model.Foods;
import com.example.eugene.Model.Orders;
import com.example.eugene.R;
import com.example.eugene.ViewHolder.TambahanMenuMakananViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahanMenuMakanan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahanMenuMakanan extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Foods, TambahanMenuMakananViewHolder>adapter;
    ProgressBar progressBar;
    DatabaseReference dbFoods,dbOrders;

    String orderKey = "";
    String foodId,foodName,foodPrice;
    ImageView plustBtn,minusBtn;
    TextView qty;
    int numberOrder = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TambahanMenuMakanan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TambahanMenuMakanan.
     */
    // TODO: Rename and change types and number of parameters
    public static TambahanMenuMakanan newInstance(String param1, String param2) {
        TambahanMenuMakanan fragment = new TambahanMenuMakanan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // GET OrderId From OrderDetail Activity
        orderKey = getActivity().getIntent().getStringExtra("OrderId");
//        Log.d("TAG",""+orderKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_tambahan_menu_makanan,container,false);

        dbFoods = FirebaseDatabase.getInstance().getReference("Foods");
        dbOrders = FirebaseDatabase.getInstance().getReference("Orders");
        recyclerView = layout.findViewById(R.id.rv_tambahanMenuMakanan);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = layout.findViewById(R.id.progressBarTambahanMenuMakanan);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadListFood();
    }

    private void loadListFood() {
        FirebaseRecyclerOptions<Foods> options = new FirebaseRecyclerOptions.Builder<Foods>()
                .setQuery(dbFoods.orderByChild("categoryId").equalTo(1),Foods.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Foods, TambahanMenuMakananViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TambahanMenuMakananViewHolder holder, int position, @NonNull Foods model) {
                holder.tambahanMakananFoodName.setText(model.getName());
                holder.tambahanMakananFoodPrice.setText(String.valueOf(model.getPrice()));
                holder.tambahanMakananFoodCategory.setText(String.valueOf(model.getCategoryId()));
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        foodId = adapter.getRef(position).getKey();
                        foodName = adapter.getItem(position).getName();
                        foodPrice = String.valueOf(adapter.getItem(position).getPrice());
//                        Log.d("TAG",""+foodId);
                        tambahanMenuMakanan(orderKey,foodId,foodName,foodPrice);
                    }
                });
            }

            @NonNull
            @Override
            public TambahanMenuMakananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_menutambahan_makanan, parent, false);
                return new TambahanMenuMakananViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void tambahanMenuMakanan(String orderKey, String foodId, String foodName, String foodPrice) {
        AlertDialog.Builder addQty = new AlertDialog.Builder(getContext());
        addQty.setMessage("Masukan Jumlah Pesanan");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View qtyLayout = layoutInflater.inflate(R.layout.orderdetail_edit_qty,null);
        addQty.setView(qtyLayout);

        plustBtn = qtyLayout.findViewById(R.id.orderDetail_BtnAddQty);
        minusBtn = qtyLayout.findViewById(R.id.orderDetail_BtnDeleteQty);
        qty = qtyLayout.findViewById(R.id.orderDetail_qty);

        plustBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                qty.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1){
                    int newNumberOrder = Integer.parseInt(qty.getText().toString());
                    numberOrder = newNumberOrder - 1;
                    qty.setText(String.valueOf(numberOrder));
                }
            }
        });

        addQty.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                Query newRef = rootRef.child("Orders").child(orderKey).child("orderDetail").orderByChild("productId").equalTo(foodId);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists()){
                           // Check if foodId exists..
                           Toast.makeText(getContext(), "Gagal. Item ini sudah ada", Toast.LENGTH_SHORT).show();
                       } else {
                           double subtotal = numberOrder * Double.parseDouble(foodPrice);
                           Orders orders = new Orders();
                           orders.setId("MenuTambahan");
                           orders.setProductId(foodId);
                           orders.setProductName(foodName);
                           orders.setPrice(Double.parseDouble(foodPrice));
                           orders.setQuantity(numberOrder);
                           orders.setSubtotal(subtotal);

                           String newKeyOrderDetail = dbOrders.child(orderKey).child("orderDetail").push().getKey();
                           dbOrders.child(orderKey).child("orderDetail").child(newKeyOrderDetail).setValue(orders);

                           // RE-Calculate Subtotal Item & Price (Order Parent)
//                           DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                           DatabaseReference dbref = rootRef.child("Orders");
                           ValueEventListener valueEventListener = new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   int newSubtotalItem = 0;
                                   double newSubtotalPrice = 0;
                                   for (DataSnapshot ds:snapshot.child(orderKey).child("orderDetail").getChildren()){
                                       int subtotalItem = ds.child("quantity").getValue(int.class);
                                       double subtotalPrice = Double.valueOf(ds.child("subtotal").getValue(Long.class));

                                       newSubtotalItem = newSubtotalItem + subtotalItem;
                                       newSubtotalPrice = newSubtotalPrice + subtotalPrice;
                                   }
                                   // Update Subtotal Item & Price in Database
                                   dbref.child(orderKey).child("subtotalItem").setValue((newSubtotalItem));
                                   dbref.child(orderKey).child("subtotalPrice").setValue((newSubtotalPrice));
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {
                                   throw error.toException();
                               }
                           };
                           dbref.addListenerForSingleValueEvent(valueEventListener);
                           Toast.makeText(getContext(), "Menu tambahan berhasil disimpan", Toast.LENGTH_SHORT).show();
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                };
                newRef.addListenerForSingleValueEvent(valueEventListener);
            }
        });

        addQty.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        addQty.show();
    }
}