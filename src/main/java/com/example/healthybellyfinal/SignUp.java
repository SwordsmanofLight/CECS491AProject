package com.example.healthybellyfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    private static final String KEY_Username = "username";
    private static final String KEY_Email = "email";
    private static final String KEY_UID1 = "uid1";
    private static final String KEY_UID2 = "uid2";
    private Button register;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        register=(Button) findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText UsernameID=findViewById(R.id.UserName);
                EditText PasswordID=findViewById(R.id.Password);
                EditText CpasswordID=findViewById(R.id.ConfirmPassword);
                EditText EmailID=findViewById(R.id.Email);
                String Name=UsernameID.getText().toString();
                String Pass=PasswordID.getText().toString();
                String Cpass=CpasswordID.getText().toString();
                String Email=EmailID.getText().toString();
                boolean correct;
                correct=true;

                if(!emailchecker(Email))
                {
                    Toast.makeText(getApplicationContext(),"email incorrect", Toast.LENGTH_SHORT).show();
                    EmailID.setText(null);
                    correct=false;
                }
                if(!Pass.equals(Cpass))
                {
                    Toast.makeText(getApplicationContext(),"Passwords dont match",Toast.LENGTH_SHORT).show();
                    PasswordID.setText(null);
                    correct=false;
                }
                if(Name.matches("")||Pass.matches("")||Cpass.matches("")||Email.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"fill out all fields",Toast.LENGTH_SHORT).show();
                    correct=false;
                }

                if(correct==true)
                {
                    createAccount (Email,Pass,Name);
                    //storeInDB(Email,Name);
                    openSignin();


                }
            }
        });
    }

/*    private void storeInDB(String Email, String Name) {
        FirebaseUser user = mAuth.getCurrentUser();
        Map<String, Object> newUser = new HashMap<>();
        newUser.put(KEY_Username, Name);
        newUser.put(KEY_Email, Email);
        String userID1 = user.getUid();
        mAuth = FirebaseAuth.getInstance();
        String userID2 = mAuth.getCurrentUser().getUid();
        newUser.put(KEY_UID1, userID1);
        newUser.put(KEY_UID2, userID2);
        db.collection("Users").document(Email).set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUp.this, "Username Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }*/

    public void createAccount(final String Email, String Pass, final String Name)
    {
      mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task)
          {
             if(task.isSuccessful())
             {
                 Toast.makeText(SignUp.this, "Authentication failed.",
                         Toast.LENGTH_SHORT).show();
                 String useerID = mAuth.getCurrentUser().getUid();
                 DocumentReference documentReference = db.collection("Users").document(useerID);
                 Map<String, Object> newUser = new HashMap<>();
                 newUser.put(KEY_Username, Name);
                 newUser.put(KEY_Email, Email);
                 documentReference.set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Log.d(TAG, "Profile Made-------------" + Name);
                     }
                 });
             }
          }

      });

    }


    public void openSignin()
    {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

    }

    boolean emailchecker(CharSequence Email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }



}
