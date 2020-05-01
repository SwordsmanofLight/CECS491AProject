package com.example.healthybellyfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    ArrayList<String> selection = new ArrayList<String>();
    private static final String KEY_Allergies = "allergies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Button submitButton = findViewById(R.id.SubmitChanges);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                openProfile();
            }
        });
    }

    private void updateProfile() {
        String userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference
                .update(KEY_Allergies, selectionToString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openProfile() {
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
        finish();
    }

    public String selectionToString() {

        if (selection.size() > 0){
            StringBuilder selectionString= new StringBuilder(selection.get(0));
            for(int i = 1; i < selection.size(); i++){
                selectionString.append(",").append(selection.get(i));
            }
            return selectionString.toString();}
        else
            return "NONE";
    }

    public void selectItems(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.MilkCheck:
                if (checked)
                    selection.add("Dairy");
                else
                    selection.remove("Dairy");
                break;
            case R.id.EggCheck:
                if (checked)
                    selection.add("Eggs");
                else
                    selection.remove("Eggs");
                break;
            case R.id.TreeNutCheck:
                if (checked)
                    selection.add("Tree Nuts");
                else
                    selection.remove("Tree Nuts");
                break;
            case R.id.PeanutCheck:
                if (checked)
                    selection.add("Peanuts");
                else
                    selection.remove("Peanuts");
                break;
            case R.id.ShellFishCheck:
                if (checked)
                    selection.add("Shell Fish");
                else
                    selection.remove("Shell Fish");
                break;
            case R.id.WheatCheck:
                if (checked)
                    selection.add("Wheat");
                else
                    selection.remove("Wheat");
                break;
            case R.id.SoyCheck:
                if (checked)
                    selection.add("Soy");
                else
                    selection.remove("Soy");
                break;
            case R.id.FishCheck:
                if (checked)
                    selection.add("Fish");
                else
                    selection.remove("Fish");
                break;
        }
    }
}
