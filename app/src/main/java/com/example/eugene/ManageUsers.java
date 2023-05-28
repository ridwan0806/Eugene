package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ManageUsers extends AppCompatActivity {
    EditText edtname,edtemail,edtpassword;
    Button submit;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        
        edtname = findViewById(R.id.txtUsername);
        edtemail = findViewById(R.id.txtEmail);
        edtpassword = findViewById(R.id.txtPassword);
        submit = findViewById(R.id.btnSubmitNewUser);

        mAuth = FirebaseAuth.getInstance();
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(edtname.getText().toString(),edtemail.getText().toString(),edtpassword.getText().toString());
            }
        });
    }

    private void registerUser(String name, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null){
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ManageUsers.this, "success added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ManageUsers.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageUsers.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}