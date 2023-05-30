package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
//    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
//    FirebaseDatabase db;
//    DatabaseReference userList;
//    Users currentUser;
//    String uId;

    ImageView masterFood,masterDrink,masterRawMaterial,masterExpenses;
    ImageView cashier,addOrder,addPurchaseOrder,addRoutineExpense;
    ImageView logout;

    TextView username;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username = findViewById(R.id.textView4);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            username.setText(firebaseUser.getDisplayName());
//            Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
//        uId = firebaseUser.getUid();
//        System.out.println(uId);
//        username.setText(uId);

//        db = FirebaseDatabase.getInstance();
//        userList = db.getReference("Users");

        initView();
        initIntent();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
////        uId = firebaseUser.getUid();
//        if(currentUser != null){
//            Toast.makeText(this, "user already login", Toast.LENGTH_SHORT).show();
////            getDataUser(uId);
//        } else {
//            startActivity(new Intent(this,LoginActivity.class));
////            finish();
//        }
//    }

    @Override
    public void onBackPressed(){
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else {
            Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

//    private void getDataUser(String userId){
//        Toast.makeText(this, "userId is:"+userId, Toast.LENGTH_SHORT).show();
//        userList.child(uId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Users user = snapshot.getValue(Users.class);
//                System.out.println(user.getBranch());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

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

        masterFood = findViewById(R.id.imgDashboardFood);
        masterDrink = findViewById(R.id.imgDashboardDrink);
        masterRawMaterial = findViewById(R.id.imgDashboardRaw);

        logout = findViewById(R.id.iconLogout);
    }
}