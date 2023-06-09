package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.Activity.Cart;
import com.example.eugene.Common.MoneyTextWatcher;
import com.example.eugene.Model.RequestOrder;
import com.example.eugene.ViewHolder.OrderProcessViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;

public class MasterDrinks extends AppCompatActivity {
//    EditText editText;
//    TextView textView;
//    Button button;

//    private FirebaseAuth mAuth;
//    private FirebaseUser firebaseUser;

//    EditText name,email,password;
//    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference orders;

    FirebaseRecyclerAdapter<RequestOrder, OrderProcessViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_drinks);

        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Orders");

//        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_cashier_inprocess,null);
//        progressBar = root.findViewById(R.id.progressBarOrderProcess);

//        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_orderInProcess);
        recyclerView = findViewById(R.id.recyclerOrderProcessTesting);
        layoutManager = new LinearLayoutManager(MasterDrinks.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

//        loadListOrderProcess();

//        editText = findViewById(R.id.etInputNumber);
//        editText.addTextChangedListener(new MoneyTextWatcher(editText));
//        editText.setText("0");

//        textView = findViewById(R.id.tvInputNumber);
//        firebaseUser = mAuth.getCurrentUser();
//        textView.setText(firebaseUser.getEmail());
//        button = findViewById(R.id.btnOk);
//
//        progressDialog = new ProgressDialog(MasterDrinks.this);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("please wait..");
//        progressDialog.setCancelable(false);
//
//        mAuth = FirebaseAuth.getInstance();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                addUserDialog();
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadListOrderProcess();
//        Toast.makeText(getContext(), "testing", Toast.LENGTH_SHORT).show();
    }

    private void loadListOrderProcess() {
        FirebaseRecyclerOptions<RequestOrder> options =
                new FirebaseRecyclerOptions.Builder<RequestOrder>()
                        .setQuery(orders.orderByChild("branchId"),RequestOrder.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<RequestOrder, OrderProcessViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderProcessViewHolder holder, int position, @NonNull RequestOrder model) {
                holder.custName.setText(model.getNameCustomer());
                holder.subtotal.setText(String.valueOf(model.getSubtotalPrice()));
            }

            @NonNull
            @Override
            public OrderProcessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_orderprocess, parent, false);

                return new OrderProcessViewHolder(view);
            }

//            @Override
//            public void onDataChanged() {
//                if (progressBar != null) {
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

//    private void addUserDialog() {
////        mAuth = FirebaseAuth.getInstance();
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MasterDrinks.this);
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        View addUserLayout = inflater.inflate(R.layout.user_add,null);
//
//        alertDialog.setView(addUserLayout);
//
//        name = addUserLayout.findViewById(R.id.txtUsername);
//        email = addUserLayout.findViewById(R.id.txtEmail);
//        password = addUserLayout.findViewById(R.id.txtPassword);
//
//        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                addNewUser();
////                registerNewUser(name.getText().toString(),email.getText().toString(),password.getText().toString());
//            }
//        });
//
//        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(MasterDrinks.this, "canceled", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        alertDialog.show();
//    }

//    private void addNewUser() {
//    }

//    private void registerNewUser(String name, String email, String password) {
//        progressDialog.show();
////        mAuth = FirebaseAuth.getInstance();
//        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    FirebaseUser firebaseUser = task.getResult().getUser();
//                    if (firebaseUser != null){
//                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(name)
//                                .build();
//                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(MasterDrinks.this, "Success Added New User", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    } else {
//                        Toast.makeText(MasterDrinks.this, "Register New User Failed", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(MasterDrinks.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        progressDialog.dismiss();
//    }

//    private void teset(){
//        BigDecimal value = MoneyTextWatcher.parseCurrencyValue(editText.getText().toString());
//        textView.setText(String.valueOf(value));
//    }
}