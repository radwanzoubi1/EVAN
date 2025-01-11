package com.example.e_van_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   RecyclerView recyclerView;
   ArrayList<CardItem> arrayList;
   FirebaseFirestore db;
   CardAdapter cardAdapter;

    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String ADMIN_PASSWORD = "admin1234";
    EditText loginUsername, loginPassword;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginUsername = findViewById(R.id.EmailLogIN);
        loginPassword = findViewById(R.id.passwordLogIN);
        loginButton = findViewById(R.id.Log_INBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                Intent intent = null;
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    intent = new Intent(MainActivity.this, HomeAdmin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Chick your Email or password", Toast.LENGTH_SHORT).show();

                }



            }
        });




    }
}