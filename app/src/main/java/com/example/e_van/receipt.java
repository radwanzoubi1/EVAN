package com.example.e_van;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class receipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        String cardType = getIntent().getStringExtra("order");
        Double cost = Double.valueOf(getIntent().getStringExtra("costs"));
        @SuppressLint("DefaultLocale") String c= String.format("%.1f JD", cost);
        TextView order = findViewById(R.id.card_order);
        TextView costs = findViewById(R.id.Cost1);
        order.setText(cardType);
        costs.setText(c);

        Button feedbackButton = findViewById(R.id.Feedback);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedbackFormUrl = "https://docs.google.com/forms/d/e/1FAIpQLSdjfLsBI1MDnELR84xWNouBIKC-8fCU59Dg-RWOuW90HH9wPQ/viewform?usp=sf_link";
                Uri uri = Uri.parse(feedbackFormUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Button orderAgainButton = findViewById(R.id.home);
        orderAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(receipt.this, Home_Page.class);
                startActivity(intent);
                finish();
            }
        });

    }
}