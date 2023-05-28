package com.example.eugene.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Adapter.CartAdapter;
import com.example.eugene.DashboardActivity;
import com.example.eugene.Database.DatabaseHelper;
import com.example.eugene.Model.Orders;
import com.example.eugene.Model.Request;
import com.example.eugene.Model.RequestOrder;
import com.example.eugene.Model.Users;
import com.example.eugene.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests,order,userData;
    FirebaseUser firebaseUser;
    String uId,fullName;
    int branchId;

//    List<Orders> cart = new ArrayList<Orders>();
    List<Orders> orderItem = new ArrayList<>();

    CartAdapter cartAdapter;

    TextView subtotalPrice,subtotalItem,btnSubmitOrder,consumenType;
    EditText customerName,notes;
    RadioGroup orderType;
    RadioButton rdDineIn,rdTakeAway;

    int categoryTypeId;
    int orderTypeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        order = database.getReference("Orders");

        //GET INFO USER
        userData = database.getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uId = firebaseUser.getUid();
        getInfoUser(uId);

        recyclerView = findViewById(R.id.recyclerCartList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        subtotalPrice = findViewById(R.id.txtTotalAll);
        subtotalItem = findViewById(R.id.txtSubtotalItem);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitShowDialog();
            }
        });

        loadListFood();
    }

    private void getInfoUser(String userId) {
        userData.child(uId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                branchId = user.getBranch();
                fullName = user.getFullName();
//                System.out.println(fullName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListFood();
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
//        String str="";
        switch (view.getId()){
            case R.id.rdDineIn:
                if (checked){
                    orderTypeId = 1;
                }
                break;
            case R.id.rdTakeAway:
                if (checked){
                    orderTypeId = 2;
                }
                break;
        }
//        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void submitShowDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View submitLayout = inflater.inflate(R.layout.cart_add_detail_info,null);

        alertDialog.setView(submitLayout);

        customerName = submitLayout.findViewById(R.id.edtCustomerName);
        orderType = submitLayout.findViewById(R.id.rdOrderType);
        rdDineIn = submitLayout.findViewById(R.id.rdDineIn);
        rdTakeAway = submitLayout.findViewById(R.id.rdTakeAway);
        consumenType = submitLayout.findViewById(R.id.cmb_consumenType);
        notes = submitLayout.findViewById(R.id.edtNotes);

        consumenType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryTypeId = 0;
                PopupMenu popup = new PopupMenu(Cart.this, consumenType);
                popup.getMenuInflater().inflate(R.menu.category_consumen, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.umum) {
                            categoryTypeId = 1;
                            consumenType.setText("Umum");
                        } else if (itemId == R.id.online){
                            categoryTypeId = 2;
                            consumenType.setText("Online / Ojol");
                        } else if (itemId == R.id.karyawan){
                            categoryTypeId = 3;
                            consumenType.setText("Karyawan");
                        } else if (itemId == R.id.kerabat){
                            categoryTypeId = 4;
                            consumenType.setText("Kerabat / Tetangga");
                        } else {
                            categoryTypeId = 0;
                        }
                        System.out.println(categoryTypeId);
                        return true;
                    }
                });
                popup.show();
            }
        });

        //SUBMIT ORDER TO FIREBASE
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RequestOrder requestOrder = new RequestOrder(
                        branchId,
                        fullName,
                        customerName.getText().toString(),
                        "timestamp",
                        "Date",
                        orderTypeId,
                        categoryTypeId,
                        "NEW",
                        notes.getText().toString(),
                        Integer.parseInt(subtotalItem.getText().toString()),
                        Double.parseDouble(subtotalPrice.getText().toString()),
                        0,
                        0,
                        1,
                        0,
                        "",
                        "",
                        "",
                        orderItem
                );
                //submit to firebase (using system.CurrentMilli for key)
                order.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(requestOrder);

                //clean cart
                new DatabaseHelper(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Order Saved", Toast.LENGTH_SHORT).show();

                //redirect to home
                Intent home = new Intent(Cart.this, DashboardActivity.class);
                startActivity(home);
                finish();
            }
        });

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Cart.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

//    private void showAlertDialog() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
//        alertDialog.setTitle("One More Step !");
//        alertDialog.setMessage("Enter Customer Name: ");
//
//        final EditText edtCustomerName = new EditText(Cart.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//        );
//        edtCustomerName.setLayoutParams(lp);
//        alertDialog.setView(edtCustomerName);
//        alertDialog.setIcon(R.drawable.shopping_cart);
//
//        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Request request = new Request(
//                        "Ridwan Test",
//                        GET NAME FROM LOGIN FIREBASE
//                        edtCustomerName.getText().toString(),
//                        Double.parseDouble(txtTotalAll.getText().toString()),
//                        cart
//                );
//
//                //submit to firebase (using system.CurrentMilli for key)
//                requests.child(String.valueOf(System.currentTimeMillis()))
//                        .setValue(request);
//
//                //clean cart
//                new DatabaseHelper(getBaseContext()).cleanCart();
//                Toast.makeText(Cart.this, "Order Saved", Toast.LENGTH_SHORT).show();
//
//                //redirect to home
//                Intent home = new Intent(Cart.this, DashboardActivity.class);
//                startActivity(home);
//                finish();
//            }
//        });
//
//        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        alertDialog.show();
//    }

    private void loadListFood() {
//        cart = new DatabaseHelper(this).getAllOrder();
//        cartAdapter = new CartAdapter(this,cart);
//        recyclerView.setAdapter(cartAdapter);

        orderItem = new DatabaseHelper(this).getAllOrder();
        cartAdapter = new CartAdapter(this,orderItem);
        recyclerView.setAdapter(cartAdapter);

        //calculate total all
        int total = 0;
        for (Orders order:orderItem)
            total += (order.getPrice())*(order.getQuantity());
        subtotalPrice.setText(String.valueOf(total));

        int totalItem = 0;
        for (Orders order:orderItem)
            totalItem += (order.getQuantity());
        subtotalItem.setText(String.valueOf(totalItem));
    }

}