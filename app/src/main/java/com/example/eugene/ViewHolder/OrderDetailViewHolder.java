package com.example.eugene.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.R;

public class OrderDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView foodNameDetailItem,priceDetailItem,qtyDetailItem,subtotalDetailItem;
    public ImageView btnMenuDetailItem;
    private ItemClickListener itemClickListener;

    public OrderDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        foodNameDetailItem = itemView.findViewById(R.id.txtRVCashierProcessFoodName);
        priceDetailItem = itemView.findViewById(R.id.txtRVCashierProcessFoodPrice);
        qtyDetailItem = itemView.findViewById(R.id.txtRVCashierProcessFoodQty);
        subtotalDetailItem = itemView.findViewById(R.id.txtRVCashierProcessFoodSubtotal);
        btnMenuDetailItem = itemView.findViewById(R.id.btnRVCashierProcessAction);
//        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getBindingAdapterPosition(),false);
    }
}
