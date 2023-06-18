package com.example.eugene.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.R;

public class TambahanMenuMakananViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tambahanMakananFoodName,tambahanMakananFoodCategory,tambahanMakananFoodPrice;
    private ItemClickListener itemClickListener;

    public TambahanMenuMakananViewHolder(@NonNull View itemView) {
        super(itemView);
        tambahanMakananFoodName = itemView.findViewById(R.id.txtTambahanMenuMakananName);
        tambahanMakananFoodCategory = itemView.findViewById(R.id.txtTambahanMenuMakananCategory);
        tambahanMakananFoodPrice = itemView.findViewById(R.id.txtTambahanMenuMakananPrice);
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
