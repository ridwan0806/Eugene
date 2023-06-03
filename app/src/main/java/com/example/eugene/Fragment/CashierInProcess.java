package com.example.eugene.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.manager.RequestManagerFragment;
import com.example.eugene.Activity.Cart;
import com.example.eugene.FoodDetail;
import com.example.eugene.FoodList;
import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.MasterDrinks;
import com.example.eugene.Model.Food;
import com.example.eugene.Model.RequestOrder;
import com.example.eugene.OrderDetail;
import com.example.eugene.R;
import com.example.eugene.ViewHolder.MasterFoodViewHolder;
import com.example.eugene.ViewHolder.OrderProcessViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CashierInProcess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CashierInProcess extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference orders;

    FirebaseRecyclerAdapter<RequestOrder, OrderProcessViewHolder>adapter;

    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CashierInProcess() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cashier_inprocess.
     */
    // TODO: Rename and change types and number of parameters
    public static CashierInProcess newInstance(String param1, String param2) {
        CashierInProcess fragment = new CashierInProcess();
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cashier_inprocess,container,false);

        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Orders");
        progressBar = root.findViewById(R.id.progressBarOrderProcess);

        recyclerView = root.findViewById(R.id.recycler_orderInProcess);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadListProcess();
    }

    private void loadListProcess() {
        FirebaseRecyclerOptions<RequestOrder> options =
                new FirebaseRecyclerOptions.Builder<RequestOrder>()
                        .setQuery(orders.orderByChild("time"),RequestOrder.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<RequestOrder, OrderProcessViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderProcessViewHolder holder, int position, @NonNull RequestOrder model) {
                holder.custName.setText(model.getNameCustomer().toUpperCase(Locale.ROOT));

                NumberFormat formatter = new DecimalFormat("#,###");
                double subtotal = model.getSubtotalPrice();
                holder.subtotal.setText(formatter.format(subtotal));

                holder.btnStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(getContext(),holder.btnStatus);
                        popupMenu.getMenuInflater().inflate(R.menu.cashier_process_status, popupMenu.getMenu());

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                int itemId = menuItem.getItemId();
                                if (itemId == R.id.cashierProcessComplete) {
                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                } else if (itemId == R.id.cashierProcessCancel) {
                                    Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(getContext(), ""+model.getNameCustomer(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getContext(), OrderDetail.class);
                        i.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public OrderProcessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_orderprocess, parent, false);

                return new OrderProcessViewHolder(view);
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

}