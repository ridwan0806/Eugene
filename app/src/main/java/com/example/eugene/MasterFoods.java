package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eugene.Model.Category;
import com.example.eugene.Model.Food;
import com.example.eugene.ViewHolder.MasterFoodViewHolder;
import com.example.eugene.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MasterFoods extends AppCompatActivity {
    RecyclerView recyclerViewMasterFoods;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    FirebaseRecyclerAdapter<Food, MasterFoodViewHolder>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_foods);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadListMasterFoods();
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerViewMasterFoods = findViewById(R.id.rvMasterFoods);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewMasterFoods.setLayoutManager(layoutManager);

        loadListMasterFoods();
    }

    private void loadListMasterFoods() {
        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(foodList.orderByChild("MenuId"), Food.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Food, MasterFoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MasterFoodViewHolder holder, int position, @NonNull Food model) {
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.food_image);
                holder.food_name.setText(model.getName());
                holder.food_price.setText(model.getPrice());
                holder.food_category.setText(model.getMenuId());

                holder.food_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MasterFoods.this, "edit", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.food_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MasterFoods.this, "delete", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public MasterFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_masterfoods, parent, false);

                return new MasterFoodViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerViewMasterFoods.setAdapter(adapter);
    }
}