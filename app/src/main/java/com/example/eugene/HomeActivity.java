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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eugene.Adapter.CategoryAdapter;
import com.example.eugene.Common.Common;
import com.example.eugene.Interface.ItemClickListener;
import com.example.eugene.Model.Category;
import com.example.eugene.Model.Food;
import com.example.eugene.ViewHolder.FavoriteDrinksViewHolder;
import com.example.eugene.ViewHolder.FavoriteFoodsViewHolder;
import com.example.eugene.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference category,favoriteFoods,favoriteDrinks;

    TextView txtFullName;

    RecyclerView recyclerCategory,recyclerFavoriteFoods,recyclerFavoriteDrinks;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> categoryAdapter;
    FirebaseRecyclerAdapter<Food,FavoriteFoodsViewHolder> favoriteFoodsAdapter;
    FirebaseRecyclerAdapter<Food,FavoriteDrinksViewHolder> favoriteDrinksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initLoadCategory();
        initLoadFavoriteFoods();
        initLoadFavoriteDrinks();
        bottomNavigation();
    }

    private void initLoadFavoriteDrinks() {
        //init firebase
        database = FirebaseDatabase.getInstance();
        favoriteDrinks = database.getReference("Foods");

        recyclerFavoriteDrinks = findViewById(R.id.recyclerViewFavoriteDrinks);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerFavoriteDrinks.setLayoutManager(layoutManager);

        getFavoriteDrinks();
    }

    private void getFavoriteDrinks() {
        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(favoriteDrinks.orderByChild("IsFavoriteDrink").equalTo("1"),Food.class)
                        .build();

        favoriteDrinksAdapter = new FirebaseRecyclerAdapter<Food, FavoriteDrinksViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FavoriteDrinksViewHolder holder, int position, @NonNull Food model) {
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.imageView);
                holder.txtDrinkName.setText(model.getName());

                final Food local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(HomeActivity.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(HomeActivity.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",favoriteDrinksAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }

            @NonNull
            @Override
            public FavoriteDrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_fav_drinks, parent, false);

                return new FavoriteDrinksViewHolder(view);
            }
        };
        favoriteDrinksAdapter.startListening();
        recyclerFavoriteDrinks.setAdapter(favoriteDrinksAdapter);
    }

    private void initLoadFavoriteFoods() {
        //init firebase
        database = FirebaseDatabase.getInstance();
        favoriteFoods = database.getReference("Foods");

        recyclerFavoriteFoods = findViewById(R.id.recyclerViewFavoriteFoods);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerFavoriteFoods.setLayoutManager(layoutManager);

        getFavoriteFoods();
    }

    private void getFavoriteFoods() {
        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(favoriteFoods.orderByChild("IsFavoriteFood").equalTo("1"),Food.class)
                        .build();

        favoriteFoodsAdapter = new FirebaseRecyclerAdapter<Food, FavoriteFoodsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FavoriteFoodsViewHolder holder, int position, @NonNull Food model) {
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.imageView);
                holder.txtFoodName.setText(model.getName());

                final Food local = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(HomeActivity.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(HomeActivity.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",favoriteFoodsAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }

            @NonNull
            @Override
            public FavoriteFoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_fav_foods, parent, false);

                return new FavoriteFoodsViewHolder(view);
            }
        };
        favoriteFoodsAdapter.startListening();
        recyclerFavoriteFoods.setAdapter(favoriteFoodsAdapter);
    }

    private void initLoadCategory() {
        //init firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        txtFullName = findViewById(R.id.txtUserName);
//        txtFullName.setText(Common.currentUser.getName());

        recyclerCategory = findViewById(R.id.recyclerViewCategory);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerCategory.setLayoutManager(layoutManager);

        getCategoryFoods();
    }

    private void getCategoryFoods() {
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(category, Category.class)
                        .build();

        categoryAdapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {
                holder.txtMenuName.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.imageView);

                Category clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(HomeActivity.this,FoodList.class);
                        foodList.putExtra("CategoryId",categoryAdapter.getRef(position).getKey());
                        startActivity(foodList);
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
        categoryAdapter.startListening();
        recyclerCategory.setAdapter(categoryAdapter);
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Cart.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCategoryFoods();
        getFavoriteFoods();
        getFavoriteDrinks();
    }

}