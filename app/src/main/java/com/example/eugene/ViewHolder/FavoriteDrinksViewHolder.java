package com.example.eugene.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.R;

public class FavoriteDrinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtDrinkName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public FavoriteDrinksViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDrinkName = itemView.findViewById(R.id.favDrinksName);
        imageView = itemView.findViewById(R.id.favDrinksPic);

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
