package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eugene.Adapter.CategoryAdapter;
import com.example.eugene.Common.Common;
import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.Model.Category;
import com.example.eugene.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference category;

    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //init firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        txtFullName = findViewById(R.id.txtUserName);
        txtFullName.setText(Common.currentUser.getName());

        recycler_menu = findViewById(R.id.recyclerViewCategory);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycler_menu.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

            FirebaseRecyclerOptions<Category> options =
                    new FirebaseRecyclerOptions.Builder<Category>()
                            .setQuery(category, Category.class)
                            .build();

            adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {
                    holder.txtMenuName.setText(model.getName());
                    Glide.with(getBaseContext()).load(model.getImage()).into(holder.imageView);

                    Category clickItem = model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent foodList = new Intent(HomeActivity.this,FoodList.class);
                            foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                            startActivity(foodList);
//                            Toast.makeText(HomeActivity.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @NonNull
                @Override
                public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.viewholder_category, parent, false);

                    return new MenuViewHolder(view);
                }
            };
            adapter.startListening();
            recycler_menu.setAdapter(adapter);
    }

}