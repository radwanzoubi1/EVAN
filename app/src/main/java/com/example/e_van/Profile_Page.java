package com.example.e_van;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile_Page extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    TextView Profile_name, Profile_Email, Profile_Number;
    Button logout;


//    private void clearBackStack() {
//        Intent intent = new Intent(getApplicationContext(), Profile_Page.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        logout = findViewById(R.id.Logout_INBtn);
        Profile_name = findViewById(R.id.profileName);
        Profile_Email = findViewById(R.id.profileEmail);
        Profile_Number = findViewById(R.id.profilePhoneNUmber);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

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
                                Intent intent = new Intent();
                                String n = value.getString("username");
                                String e = value.getString("email");
                                String p = value.getString("phoneNumber");
                                intent.putExtra("Username" ,n);
                                intent.putExtra("Email" ,e);
                                intent.putExtra("phoneNumber" ,p);
                                Profile_name.setText(value.getString("username"));
                                Profile_Email.setText(value.getString("email"));
                                Profile_Number.setText(value.getString("phoneNumber"));
                            }
                        });
                    } else {
                        // handle the error
                    }
                }
            });
        } else {
            // handle the error
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Toast.makeText(Profile_Page.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                finish();

                startActivity(new Intent(Profile_Page.this, Login_Page.class));

            }
        });

        TextView emailButton = findViewById(R.id.conactEmail);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        TextView callButton = findViewById(R.id.contactPhone);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+962780622242")); // Replace with your phone number
                startActivity(intent);
            }
        });
        TextView aboutAs = findViewById(R.id.aboutAs);
        aboutAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_Page.this, AboutUs.class));

            }
        });

    }

    private void sendEmail() {
        String recipient = "evan.project12@gmail.com";
        String subject = "form User";
        String body = "";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + recipient));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "This is a test email sent from my Android app.");

        startActivity(Intent.createChooser(intent, "Send email"));
    }


}


