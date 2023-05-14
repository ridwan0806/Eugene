package com.example.eugene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Database.DatabaseHelper;
import com.example.eugene.Database.MyDatabaseHelper;

public class EditCart extends AppCompatActivity {
    TextView idCart,foodId,name,price,qty,discount,btnUpdate;
    ImageView btnPlus,btnMin;
    String id,idFood,nameFood,priceFood,qtyFood,subtotalFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);

        foodId = findViewById(R.id.txtProductIdCartEdit);
        name = findViewById(R.id.txtFoodNameCart);
        price = findViewById(R.id.edtPriceCart);
        qty = findViewById(R.id.edtQtyCart);
        discount = findViewById(R.id.txtDiscountCartEdit);
        idCart = findViewById(R.id.txtIdCart);

        btnPlus = findViewById(R.id.btnPlusEditCart);
        btnMin = findViewById(R.id.btnMinUpdateCart);

        btnUpdate = findViewById(R.id.btnUpdateCart);

        //get data from RecyclerView Cart
        if (getIntent().hasExtra("id")
                && getIntent().hasExtra("foodId")
                && getIntent().hasExtra("nameFood")
                && getIntent().hasExtra("price")
                && getIntent().hasExtra("qty")
                && getIntent().hasExtra("subtotal"))
        {
            id = getIntent().getStringExtra("id");
            idFood = getIntent().getStringExtra("foodId");
            nameFood = getIntent().getStringExtra("nameFood");
            priceFood = getIntent().getStringExtra("price");
            qtyFood = getIntent().getStringExtra("qty");
            subtotalFood = getIntent().getStringExtra("subtotal");

            idCart.setText(id);
            foodId.setText(idFood);
            name.setText(nameFood);
            price.setText(priceFood);
            qty.setText(qtyFood);
            discount.setText(subtotalFood);
        }

        final int[] numberOrder = {Integer.parseInt(qtyFood)};

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder[0] = numberOrder[0] + 1;
                qty.setText(String.valueOf(numberOrder[0]));
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder[0] >1){
                    numberOrder[0] = numberOrder[0] - 1;
                }
                qty.setText(String.valueOf(numberOrder[0]));
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDb = new DatabaseHelper(EditCart.this);
                myDb.updateCart(id,
                        price.getText().toString().trim(),
                        qty.getText().toString().trim()
                );
                Intent showCart = new Intent(EditCart.this,Cart.class);
                startActivity(showCart);
                finish();
            }
        });
    }

}