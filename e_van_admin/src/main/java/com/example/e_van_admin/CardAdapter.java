package com.example.e_van_admin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.EventListener;
import java.util.List;

import io.grpc.Context;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    HomeAdmin context;
     List<CardItem> cardItems;

    FirebaseFirestore db;

    CardAdapter adapter;

    String lat;
    String lon ;




    public void setCardItems(List<CardItem> cardItems) {
        this.cardItems = cardItems;
    }



    public CardAdapter(HomeAdmin context, List<CardItem> cardItems) {

        this.context = context;
        this.cardItems = cardItems;
    }



//    public CardAdapter(HomeAdmin homeAdmin, ArrayList<CardItem> arrayList) {
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItem item = cardItems.get(position);

        // Bind data to TextViews
        holder.textName.setText(item.getName());
        holder.textPhone.setText(item.getPhone());
        holder.textService.setText(item.getService());
        @SuppressLint("DefaultLocale") String cost = String.format("%.1f JD", item.getCost());

        holder.textCost.setText(cost);

        // Set click listeners for buttons
        holder.buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the location data from the card item
                Double lat = item.getLat();
                Double lon = item.getLon();

                // Create an Intent to launch Google Maps with the location data
                String uriString = "google.navigation:q=" + lat + "," + lon;
                Uri locationUri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                // Start the activity with the created Intent
                v.getContext().startActivity(mapIntent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        holder.buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CardItem removedItem = cardItems.get(position);
                    String documentId = removedItem.getDocumentId(); // Retrieve the document ID of the CardItem

                    // Get the document reference using the document ID
                    DocumentReference documentRef = db.collection("Request").document(documentId);

                    // Get the document snapshot
                    documentRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    // Check if the document exists
                                    if (documentSnapshot.exists()) {
                                        // Delete the document using the document snapshot
                                        documentSnapshot.getReference()
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        // Remove the card item from the list
                                                        cardItems.remove(position);

                                                        // Reindex the remaining card items
                                                        for (int i = position; i < cardItems.size(); i++) {
                                                            CardItem item = cardItems.get(i);
                                                            item.setIndex(i); // Update the index of the card item
                                                        }

                                                        // Notify the adapter about the item removal
                                                        notifyItemRemoved(position);

                                                        // Display a toast message for successful deletion
                                                        Toast.makeText(context, "Order completed", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Handle failure to delete the document
                                                        Toast.makeText(context, "Failed to complete order", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        // Handle the case where the document doesn't exist
                                        Toast.makeText(context, "Document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure to retrieve the document
                                    Toast.makeText(context, "Failed to retrieve document", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });







        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = holder.textPhone.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPhone;
        TextView textService;
        TextView textCost;
        Button buttonLocation;
        Button buttonFinish;
        Button buttonCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPhone = itemView.findViewById(R.id.textPhone);
            textService = itemView.findViewById(R.id.textService);
            textCost = itemView.findViewById(R.id.textCost);
            buttonLocation = itemView.findViewById(R.id.buttonLocation);
            buttonFinish = itemView.findViewById(R.id.buttonFinish);
            buttonCall = itemView.findViewById(R.id.buttonCall);
        }
    }
}
