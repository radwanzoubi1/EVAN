package com.example.e_van;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//=========
import com.example.e_van.databinding.ActivitySignInBinding;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
//=========
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Submit_Page extends AppCompatActivity {



    public String email;
    public String username;
    public String phoneNumber;

    public String order ;

    public void setOrder(String s){
        this.order = s;

    }
    public String getOrder(){
        return order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String Tag = "Location";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private SettingsClient mSettingsClient;
    public LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;

    private Location lastLocation;

    Context context;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;




    TextView tv_lat, tv_lon, tv_address;
    Double d_lat, d_lon;

    String fetched_address = "";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_submit_page);
        tv_lat = (TextView) findViewById(R.id.AtitudeText);
        tv_lon = (TextView) findViewById(R.id.LongtudeText);
        tv_address = (TextView) findViewById(R.id.AddressText);



        context = getApplicationContext();
        checkLocationPermission();
        String email = getIntent().getStringExtra("Email");
        String username = getIntent().getStringExtra("Username");
        String phoneNUmber = getIntent().getStringExtra("phoneNumber");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Request");
        DocumentReference documentRef = collectionRef.document();

        String cardType = getIntent().getStringExtra("cardType");



        init();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Submit_Page submitPage = new Submit_Page();
        submitPage.setOrder(cardType);
        if (firebaseAuth.getCurrentUser() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("user").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                submitPage.setPhoneNumber(value.getString("phoneNumber"));
                                submitPage.setEmail(value.getString("email"));
                                submitPage.setUsername(value.getString("username"));
                            }
                        });
                    } else {

                    }
                }
            });
        } else {

        }


        TextView cardTitle = findViewById(R.id.card_title);
        cardTitle.setText(cardType);
        Button submit = findViewById(R.id.submit_go);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put("email", submitPage.getEmail());
                data.put("username", submitPage.getUsername());
                data.put("phoneNUmber", submitPage.getPhoneNumber());
                data.put("longitude",  d_lon);
                data.put("latitude", d_lat);
                data.put("order", cardType);


                documentRef.set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(Submit_Page.this, Waiting.class);
                                intent.putExtra("latitude", lastLocation.getLatitude());
                                intent.putExtra("longitude", lastLocation.getLongitude());
                                intent.putExtra("order" , cardType);
                                startActivity(intent);
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentRef.getId());
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(submitPage, "Try again", Toast.LENGTH_SHORT).show();

                                Log.w(TAG, "Error adding document", e);


                            }
                        });


            }
        });


    }

    //===== step1 =====
    //======Location Permission code ======
    private void checkLocationPermission() {
        Log.d(Tag, "inside check location");

        if (ContextCompat.checkSelfPermission(Submit_Page.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Submit_Page.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(Submit_Page.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(Submit_Page.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }


        }
    }
    //===== step2 =====

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Submit_Page.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(Tag, "Permission GRANTED");
                        init();  //code for after request for location is GRANTED ...... // uncomment It later

                        // Request location updates when permission is granted
                        startLocationUpdates();
                    }
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    //===== step3 =====
    private void startLocationUpdates() {

        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(locationSettingsResponse -> {
                    Log.d(Tag, "Location settings are OK");


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                })
                .addOnFailureListener(e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    Log.d(Tag, "inside error ->" + statusCode);
                });


    }

    //===== step4 =====
    public void stopLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())

                .addOnCompleteListener(task -> {
                    Log.d(Tag, "stop location update");
                });
    }
    ////===== step5 =====

    @SuppressLint("SetTextI18n")
    private void receiveLocation(LocationResult locationResult) {
        lastLocation = locationResult.getLastLocation();
        assert lastLocation != null;
        Log.d(Tag, "latitude : " + lastLocation.getLatitude());
        Log.d(Tag, "longitude : " + lastLocation.getLongitude());
        Log.d(Tag, "Altitude : " + lastLocation.getAltitude());
        // string for show on screen
        String s_lat = String.format(Locale.ROOT, "%.6f", lastLocation.getLatitude());
        String s_lon = String.format(Locale.ROOT, "%.6f", lastLocation.getLongitude());
        // double value to use it
        d_lat = lastLocation.getLatitude();
        d_lon = lastLocation.getLongitude();

        tv_lat.setText("" + s_lat);
        tv_lon.setText(", " + s_lon);

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(d_lat, d_lon, 1);
            fetched_address = addresses.get(0).getAddressLine(0);
            Log.d(Tag, "" + fetched_address);
            tv_address.setText(fetched_address + "");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    ////===== step5 =====
    public void init() {
//        onRequestPermissionsResult();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                receiveLocation(locationResult);
            }
        };


        mLocationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                .setMinUpdateIntervalMillis(500)
                .setMinUpdateDistanceMeters(1)
                .setWaitForAccurateLocation(true).build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addAllLocationRequests(Collections.singleton(mLocationRequest));
        mLocationSettingsRequest = builder.build();
        startLocationUpdates();

    }

    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }












}


