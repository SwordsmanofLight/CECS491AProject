package com.example.healthybellyfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    private static final String KEY_Username = "username";
    private static final String KEY_Email = "email";
    private static final String KEY_Allergies = "allergies";
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<String> selection = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        Button register = (Button) findViewById(R.id.Register);
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
                    Toast.makeText(getApplicationContext(),"Email format incorrect!", Toast.LENGTH_SHORT).show();
                    EmailID.setText(null);
                    correct=false;
                }
                if(!Pass.equals(Cpass))
                {
                    Toast.makeText(getApplicationContext(),"Passwords don't match!",Toast.LENGTH_SHORT).show();
                    PasswordID.setText(null);
                    correct=false;
                }
                if(Name.matches("")||Pass.matches("")||Cpass.matches("")||Email.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Fill out all fields!",Toast.LENGTH_SHORT).show();
                    correct=false;
                }
                if(Pass.length() < 6)
                {
                    Toast.makeText(getApplicationContext(),"Password must be 6 or more characters!",Toast.LENGTH_SHORT).show();
                    correct=false;
                }
                if (Name.length() > 15) {
                    Toast.makeText(getApplicationContext(),"Username must be less than 15 characters!",Toast.LENGTH_SHORT).show();
                    correct=false;
                }

                if(correct)
                {
                    createAccount (Email,Pass,Name);
                    openSignin();
                }
            }
        });
    }


    public void createAccount(final String Email, String Pass, final String Name)
    {
      mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task)
          {
             if(task.isSuccessful())
             {
                 Toast.makeText(SignUp.this, "Profile Made",
                         Toast.LENGTH_SHORT).show();
                 String userID = mAuth.getCurrentUser().getUid();
                 DocumentReference documentReference = db.collection("Users").document(userID);
                 Map<String, Object> newUser = new HashMap<>();
                 newUser.put(KEY_Username, Name);
                 newUser.put(KEY_Email, Email);
                 newUser.put(KEY_Allergies, selectionToString());
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

    public void openSignin()
    {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

    }

    boolean emailchecker(CharSequence Email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }


    public void selectItems(View view){
        boolean checked = ((CheckBox) view).isChecked();
            switch (view.getId()){
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
