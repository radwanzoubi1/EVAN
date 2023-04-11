package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class signpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signpage);
    }
    public void onRegesterClick(View view) {
        Intent intent = new Intent(signpage.this , MainActivity.class) ;
        startActivity(intent);
        Toast toast=Toast.makeText(getApplicationContext(),"Login please ",Toast.LENGTH_SHORT);

        toast.show();




    }
}