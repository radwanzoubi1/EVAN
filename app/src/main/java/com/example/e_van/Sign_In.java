package com.example.e_van;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.example.e_van.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;



public class Sign_In extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;


    ActivitySignInBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.usernameSignIN.getText().toString();
                String number = binding.phoneSignIN.getText().toString();
                String email = binding.emailSignIN.getText().toString().trim();
                String password = binding.passwordSignIN.getText().toString();
                if (email.isEmpty() || name.isEmpty() || number.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Sign_In.this, "Your data is not complete", Toast.LENGTH_SHORT).show();

                } else {


                    firebaseAuth
                            .createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid())
                                            .set(new HelperClass(name, email, password, number ));
                                    startActivity(new Intent(Sign_In.this, Login_Page.class));
                                    Toast.makeText(Sign_In.this, "Login Now ", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Sign_In.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
}