package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class loginpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        TextView cliclhere = findViewById(R.id.click_here);
        Button button = findViewById(R.id.signupbtn);


    }
//    public void onClick(View view){
//        Intent intent = new Intent(this , signpage.class);
//        startActivity(intent);
//        finish();
//    }









}