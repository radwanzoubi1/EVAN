package com.example.e_van_admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.api.Context;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.WatchChange;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeAdmin extends AppCompatActivity {

    public static String TAG ="done";
    CardAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<CardItem> arrayList;
    FirebaseFirestore db;

    String userID;
    CardItem cardItem;


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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Create dummy data for testing
        db  = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<CardItem>();
        Activity activity = HomeAdmin.this; // Your existing Activity object

// Use the activity object as a Context
       // Context context = activity;

// Now you can use the context variable as a Context object
// For example, you can pass it to methods that expect a Context parameter

        adapter = new CardAdapter(this , arrayList);
        recyclerView.setAdapter(adapter);


        DistanceCalculator distanceCalculator = new DistanceCalculator();
        EventChangeListener();





    }
    private void EventChangeListener() {
        db.collection("Request").orderBy("email", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                DocumentSnapshot document = dc.getDocument();

                                String username = document.getString("username");
                                String phone = document.getString("phoneNUmber");
                                String service = document.getString("order");
                                Double lat = document.getDouble("latitude");
                                Double lon = document.getDouble("longitude");
                                double cost ;
                                if (lon != null && lat!=null) {
                                    DistanceCalculator distanceCalculator = new DistanceCalculator();
                                    double dis = distanceCalculator.calculateDistance(lat, lon,32.566160 ,  35.86854);
                                    assert service != null;
                                    cost = calculateCost(service, dis);
                                } else {
                                    cost = 0;
                                    // You could log an error, display an error message, or take an alternative action
                                }



                                // Create a CardItem object

                                 cardItem = new CardItem(username, phone, service,cost);
                                cardItem.setDocumentId(document.getId()); // Set the document ID



                                // Retrieve latitude and longitude from the document


                                cardItem.setLat(lat);
                                cardItem.setLon(lon);

                                // Add the CardItem to your list or adapter
                                arrayList.add(cardItem);
                                Log.d(TAG, "Added cardItem: " + cardItem.toString());
                            }
                        }

                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}

