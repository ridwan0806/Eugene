package com.example.eugene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;

    ImageView masterFood,masterDrink,masterRawMaterial,masterExpenses;
    ImageView cashier,addOrder,addPurchaseOrder,addRoutineExpense;
    ImageView logout;

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

        // .....

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        masterFood = findViewById(R.id.imgDashboardFood);
        masterDrink = findViewById(R.id.imgDashboardDrink);
        masterRawMaterial = findViewById(R.id.imgDashboardRaw);

        logout = findViewById(R.id.iconLogout);
    }
}