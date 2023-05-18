package com.example.eugene.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.R;

public class MasterFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView food_name,food_price,food_category;
    public ImageView food_image,food_edit,food_delete;

    private ItemClickListener itemClickListener;

    public MasterFoodViewHolder(@NonNull View itemView) {
        super(itemView);
        food_name = itemView.findViewById(R.id.masterFoodName);
        food_price = itemView.findViewById(R.id.masterFoodPrice);
        food_category = itemView.findViewById(R.id.masterFoodCat);

        food_image = itemView.findViewById(R.id.masterFoodImage);
        food_delete = itemView.findViewById(R.id.masterFoodBtnDelete);
        food_edit = itemView.findViewById(R.id.masterFoodBtnEdit);
    }

    @Override
    public void onClick(View view) {

    }
}
