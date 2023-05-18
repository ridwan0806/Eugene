package com.example.eugene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    ImageView masterFood,masterDrink,masterRawMaterial,masterExpenses;
    ImageView cashier,addOrder,addPurchaseOrder,addRoutineExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initView();
        initIntent();
    }

    private void initIntent() {
        masterFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masterFood = new Intent(DashboardActivity.this,MasterFoods.class);
                startActivity(masterFood);
//                Toast.makeText(DashboardActivity.this, "ini master food", Toast.LENGTH_SHORT).show();
            }
        });

        masterDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "ini master drink", Toast.LENGTH_SHORT).show();
            }
        });

        masterRawMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "ini master RawMaterial", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        masterFood = findViewById(R.id.imgDashboardFood);
        masterDrink = findViewById(R.id.imgDashboardDrink);
        masterRawMaterial = findViewById(R.id.imgDashboardRaw);
    }
}