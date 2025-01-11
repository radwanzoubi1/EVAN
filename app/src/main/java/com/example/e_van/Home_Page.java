package com.example.e_van;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Home_Page extends AppCompatActivity {
    CardView Battery;
    CardView Fuel;
    CardView CarLocked;
    CardView Tow;
    CardView CarStart;
    CardView Flat_Tair;

    ImageView Profile ;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;
    String userID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Battery = findViewById(R.id.BatteryCard);
        //location check if it on or off in device
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!gpsEnabled && !networkEnabled) {
            // Notify the user that location and internet services are not available
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Location and internet services are disabled. Please enable location and internet services in settings.");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Open the device's settings to enable location and internet services
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (networkInfo == null || !networkInfo.isConnected()) {
            // Notify the user that internet services are not available
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Internet services are disabled. Please enable internet services in settings.");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Open the device's settings to enable internet services
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        Battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Submit_Page.class);
                intent.putExtra("cardType", "Battery");

                startActivity(intent);
                Toast.makeText(Home_Page.this, "Please wait until your location is visible", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Fuel = findViewById(R.id.FuelCard);
        Fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Submit_Page.class);
                intent.putExtra("cardType", "Fuel");
                startActivity(intent);
                Toast.makeText(Home_Page.this, "Please wait until your location is visible", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Flat_Tair = findViewById(R.id.FlatCard);
        Flat_Tair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Submit_Page.class);
                intent.putExtra("cardType", "Flat Tair");
                startActivity(intent);
                Toast.makeText(Home_Page.this, "Please wait until your location is visible", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Tow = findViewById(R.id.TowCard);
        Tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Submit_Page.class);
                intent.putExtra("cardType", "Tow");
                startActivity(intent);
                Toast.makeText(Home_Page.this, "Please wait until your location is visible", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        CarStart = findViewById(R.id.StartCard);
        CarStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Submit_Page.class);
                intent.putExtra("cardType", "Car Start");
                startActivity(intent);
                Toast.makeText(Home_Page.this, "Please wait until your location is visible", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        CarLocked = findViewById(R.id.CarLockedCard);
        CarLocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Submit_Page.class);
                intent.putExtra("cardType", "Car Locked");
                startActivity(intent);
                Toast.makeText(Home_Page.this, "Please wait until your location is visible", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

            Profile = findViewById(R.id.profileImage);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Page.this, Profile_Page.class);
                startActivity(intent);


            }
        });
        //get the name in home page
        TextView name = findViewById(R.id.textView5);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("user").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                name.setText(value.getString("username"));

            }
        });

    }




}