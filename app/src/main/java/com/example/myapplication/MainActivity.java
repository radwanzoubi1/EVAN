package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(this , signpage.class);
        startActivity(intent);


    }

    public void loginClick(View view){
        Intent intent = new Intent(this , homepage.class);
        startActivity(intent);


    }
}