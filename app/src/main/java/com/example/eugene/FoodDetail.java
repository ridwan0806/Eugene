package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eugene.Database.DatabaseHelper;
import com.example.eugene.Database.MyDatabaseHelper;
import com.example.eugene.Model.Food;
import com.example.eugene.Model.Foods;
import com.example.eugene.Model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetail extends AppCompatActivity {

    private TextView addToCartBtn,titleTxt,priceTxt,numberOrderTxt;
    private ImageView plusBtn,minusBtn,picFood;
    int numberOrder = 1;

    String foodId = "";

    FirebaseDatabase database;
    DatabaseReference foods;

    Foods currentFood;
    double subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        bottomNavigation();

        //init firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //init view
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);

        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.picFood);

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder>1){
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        //get foodId from intent
        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty())
        {
            getDetailFood(foodId);
        }

    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentFood = snapshot.getValue(Foods.class);
                Glide.with(getBaseContext()).load(currentFood.getImage()).into(picFood);
                titleTxt.setText(currentFood.getName());
                priceTxt.setText("Rp "+currentFood.getPrice());

                addToCartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseHelper myDB = new DatabaseHelper(FoodDetail.this);

                        int result =  myDB.checkItemExist(foodId);
                        if (result == 0) {
                            Toast.makeText(FoodDetail.this, "item ini sudah ada", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            subtotal = currentFood.getPrice()*(numberOrder);
                            myDB.addToCart(new Order(
                                    "",
                                    foodId,
                                    currentFood.getName(),
                                    String.valueOf(numberOrder),
                                    String.valueOf(currentFood.getPrice()),
                                    String.valueOf(subtotal)
                            ));
                            Toast.makeText(FoodDetail.this, "item berhasil ditambah", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDetail.this,Cart.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDetail.this,HomeActivity.class));
            }
        });
    }
}