package com.example.e_van;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_van.DistanceCalculator;
import com.google.protobuf.StringValue;

import java.text.DecimalFormat;
import java.util.Locale;

public class Waiting extends AppCompatActivity {
    private static final String KEY_EXIT_TIME = "KEY_EXIT_TIME";
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_REMAINING_TIME = "remainingTime";
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private long remainingTime;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String Type;
    private String Cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wating);

        Button callButton = findViewById(R.id.CALL);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "0780622242";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        remainingTime = sharedPreferences.getLong(KEY_REMAINING_TIME, 0);
        startTimer();

        // Retrieve order details from the intent
        String cardType = getIntent().getStringExtra("order");
        double latitude = getIntent().getDoubleExtra("latitude", 32.566160);
        double longitude = getIntent().getDoubleExtra("longitude", 35.86854);

        // Set the order title in the TextView
        TextView cardTitle = findViewById(R.id.card_order);
        cardTitle.setText(cardType);

        // Calculate the distance, travel time, and cost
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double distance = distanceCalculator.calculateDistance(latitude, longitude, 32.535940, 35.863331);
        int[] arr = disTime(longitude, latitude);
        double travelTime = arr[0] * 60 + arr[1]; // Convert hours and minutes to minutes
        double cost = calculateCost(cardType, distance);
        Cost = String.valueOf(cost);
        Type = cardType;

        // Display the travel time and cost in TextViews
        TextView hours = findViewById(R.id.hoursText);
        TextView min = findViewById(R.id.minText);
        TextView costTextView = findViewById(R.id.Cost1);
        costTextView.setText(String.format(Locale.getDefault(), "%.1f JD", cost));
    }

    private void startTimer() {
        TextView hourss = findViewById(R.id.hoursText);
        TextView min = findViewById(R.id.minText);

        double latitude = getIntent().getDoubleExtra("latitude", 32.566160);
        double longitude = getIntent().getDoubleExtra("longitude", 35.86854);
        int[] arr = disTime(longitude, latitude);

        if (isTimerRunning) {
            // Timer is already running, no need to start a new one
            return;
        }

        if (remainingTime > 0) {
            timer = new CountDownTimer(remainingTime, 60000L) {
                @SuppressLint("SetTextI18n")
                public void onTick(long millisUntilFinished) {
                    // Update UI with the remaining time
                    long seconds = millisUntilFinished / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    minutes %= 60;
                    seconds %= 60;

                    if (minutes == 0 && seconds == 0 && hours != 0) {
                        hours--; // Decrease hours by 1
                        minutes = 59; // Set minutes to 59
                    }

                    hourss.setText(hours+" ");
                    min.setText(minutes + " ");

                }

                public void onFinish() {
                    Toast.makeText(Waiting.this, "Provider is in your location", Toast.LENGTH_SHORT).show();
                    // Timer has finished, navigate to another activity
                    Intent intent = new Intent(Waiting.this, receipt.class);
                    intent.putExtra("costs", Cost);
                    intent.putExtra("order", Type);
                    startActivity(intent);
                    finish(); // Optional, if you don't want the user to go back to the waiting screen
                }
            };
            timer.start();
            isTimerRunning = true;
        } else {
            timer = new CountDownTimer((arr[0] * 3600000L) + (arr[1] * 60000L) , 60000L) {
                @SuppressLint("SetTextI18n")
                public void onTick(long millisUntilFinished) {
                    // Update UI with the remaining time
                    long seconds = millisUntilFinished / 1000;
                    long minutes = seconds / 60 ;
                    long hours = minutes / 60;
                    minutes %= 60;
                    seconds %= 60;

                    if (minutes == 0 && seconds == 0 && hours != 0) {
                        hours--; // Decrease hours by 1
                        minutes = 59; // Set minutes to 59
                    }

                    hourss.setText(hours + " ");
                    min.setText(minutes + " ");

                }

                public void onFinish() {
                    Toast.makeText(Waiting.this, "Provider is in your location", Toast.LENGTH_SHORT).show();
                    // Timer has finished, navigate to another activity
                    Intent intent = new Intent(Waiting.this, receipt.class);
                    intent.putExtra("costs", Cost);
                    intent.putExtra("order", Type);
                    startActivity(intent);
                    finish();  // Optional, if you don't want the user to go back to the waiting screen
                }
            };
        }

        timer.start();
        isTimerRunning = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        remainingTime = sharedPreferences.getLong(KEY_REMAINING_TIME, 0);
        startTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        remainingTime = sharedPreferences.getLong(KEY_REMAINING_TIME, 0);
        long exitTime = sharedPreferences.getLong(KEY_EXIT_TIME, 0);

        if (exitTime > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - exitTime;
            remainingTime -= elapsedTime;

            if (remainingTime < 0) {
                remainingTime = 0;
            }
        }

        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            isTimerRunning = false;

            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(KEY_REMAINING_TIME, remainingTime);
            editor.putLong(KEY_EXIT_TIME, System.currentTimeMillis());
            editor.apply();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            isTimerRunning = false;

            // Save the remaining time and exit time
            editor.putLong(KEY_REMAINING_TIME, remainingTime);
            editor.putLong(KEY_EXIT_TIME, System.currentTimeMillis());
            editor.apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private int[] disTime(double lon, double lat) {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double time = distanceCalculator.calculateTravelTime(distanceCalculator.calculateDistance(lat, lon, 32.535940, 35.863331), 80);
        time = Math.ceil(time);
        int hours = 0, min = 0;
        int[] arr = new int[2];
        if (time > 60) {
            hours = (int) (time / 60);
            min = (int) (time - (hours * 60));
        } else {
            min = (int) time;
        }
        arr[0] = hours;
        arr[1] = min;

        return arr;
    }

    private double calculateCost(String cardType, double distance) {
        double baseCost = 0;
        double perKmCost = 0.25;

        switch (cardType) {
            case "Car Locked":
                baseCost = 10;
                break;
            case "Car Start":
                baseCost = 9;
                break;
            case "Tow":
                baseCost = 15;
                break;
            case "Flat Tair":
                baseCost = 3;
                break;
            case "Fuel":
                baseCost = 16;
                break;
            case "Battery":
                baseCost = 5;
                break;
        }

        return baseCost + (distance * perKmCost);
    }
}
