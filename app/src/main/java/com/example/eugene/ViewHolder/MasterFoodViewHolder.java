package com.example.eugene.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.MasterDrinks;
import com.example.eugene.R;

public class MasterFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//    private static final String TAG = "MasterFoodViewHolder";
    public TextView food_name,food_price,food_category;
    public ImageView food_image,btn_menu;

    public MasterFoodViewHolder(@NonNull View itemView) {
        super(itemView);
        food_name = itemView.findViewById(R.id.masterFoodName);
        food_price = itemView.findViewById(R.id.masterFoodPrice);
        food_category = itemView.findViewById(R.id.masterFoodCat);

        food_image = itemView.findViewById(R.id.masterFoodImage);
        btn_menu = itemView.findViewById(R.id.masterFoodBtnMenu);

        btn_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
