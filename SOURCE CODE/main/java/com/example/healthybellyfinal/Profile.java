package com.example.healthybellyfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;


public class Profile extends AppCompatActivity {
    TextView PUserName;
    TextView PEmail;
    TextView PAllergenList;
    FirebaseAuth fAuth;
    private static final String TAG = "SignUp";
    FirebaseFirestore fStore;
    String userID;
    String allergiesList;
    String formatedAllergiesList = "Allergies:\n";
    String[] allergens = new String[8];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button editAllergensButton = findViewById(R.id.PEditAllergens);
        PUserName=findViewById(R.id.PUserName);
        PEmail=findViewById(R.id.PEmail);
        PAllergenList = findViewById(R.id.PAllergenList);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = fStore.collection("Users")
                .document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                PUserName.setText("Username: " + documentSnapshot.getString("username"));
                PEmail.setText("Email: " + documentSnapshot.getString("email"));
                allergiesList = documentSnapshot.getString("allergies");
                if (allergiesList == null){
                    PAllergenList.setText("");
                }
                else {
                    splitString(allergiesList);
                    for (String allergen : allergens) {
                        formatedAllergiesList = formatedAllergiesList + allergen + "\n";
                    }
                    PAllergenList.setText(formatedAllergiesList);
                }
            }
        });

        editAllergensButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }


        });

    }

    private void openEditProfile() {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
        finish();
    }

    public void splitString(String x){
        allergens = x.split(",");
    }
}
