package com.example.eugene.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.R;

public class OrderProcessViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView custName;
    public TextView subtotal;
    public ImageView btnStatus;

    private ItemClickListener itemClickListener;

    public OrderProcessViewHolder(@NonNull View itemView) {
        super(itemView);
        custName = itemView.findViewById(R.id.txtOrderProcessName);
        subtotal = itemView.findViewById(R.id.txtOrderProcessSubtotal);
        btnStatus = itemView.findViewById(R.id.btnCashierProcessStatus);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getBindingAdapterPosition(),false);
    }
}
