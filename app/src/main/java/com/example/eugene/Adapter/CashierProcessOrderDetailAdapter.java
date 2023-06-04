package com.example.eugene.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Model.Orders;
import com.example.eugene.R;

import java.util.List;

public class CashierProcessOrderDetailAdapter extends RecyclerView.Adapter<CashierProcessOrderDetailAdapter.ViewHolder> {

    private Context context;
    private List<Orders> detailOrder;

    public CashierProcessOrderDetailAdapter() {
    }

    public CashierProcessOrderDetailAdapter(Context context, List<Orders> detailOrder) {
        this.context = context;
        this.detailOrder = detailOrder;
    }

    @NonNull
    @Override
    public CashierProcessOrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cashierprocessdetail,parent,false);
        return new CashierProcessOrderDetailAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CashierProcessOrderDetailAdapter.ViewHolder holder, int position) {
        Orders detailItem = detailOrder.get(position);
        holder.foodName.setText(detailItem.getProductName());
        holder.price.setText(String.valueOf(detailItem.getPrice()));
        holder.qty.setText(String.valueOf(detailItem.getQuantity()));
        holder.subtotal.setText(String.valueOf(detailItem.getSubtotal()));
        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodName,price,qty,subtotal;
        ImageView btnMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.txtRVCashierProcessFoodName);
            price = itemView.findViewById(R.id.txtRVCashierProcessFoodPrice);
            qty = itemView.findViewById(R.id.txtRVCashierProcessFoodQty);
            subtotal = itemView.findViewById(R.id.txtRVCashierProcessFoodSubtotal);
            btnMenu = itemView.findViewById(R.id.btnRVCashierProcessAction);
        }
    }
}
