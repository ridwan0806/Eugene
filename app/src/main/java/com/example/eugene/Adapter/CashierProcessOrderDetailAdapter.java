package com.example.eugene.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.subtotal.setText(String.valueOf(detailItem.getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return detailOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodName,subtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.txtRVCashierProcessFoodName);
            subtotal = itemView.findViewById(R.id.txtRVCashierProcessSubtotalPrice);
        }
    }
}
