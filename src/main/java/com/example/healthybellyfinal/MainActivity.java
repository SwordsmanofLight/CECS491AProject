package com.example.healthybellyfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private Button signinbutton;
    private Button signupbutton;
    private EditText username;
    private EditText password;
    private FirebaseAuth mAuth;

    private String email;
    private String psword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView uname,pword;

        mAuth=FirebaseAuth.getInstance();


        //username is email now
        username=findViewById(R.id.UserName);
        password=findViewById(R.id.Password);

        signinbutton=(Button) findViewById(R.id.login);
        signinbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    email=username.getText().toString();
                    psword=password.getText().toString();

                    Signin(email,psword);

                }

        });
        signupbutton=(Button) findViewById(R.id.signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openSignup();
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        //openCamera(currentUser);
    }

    public void Signin(String username,String password)
    {
        mAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                       if(task.isSuccessful())
                       {
                           FirebaseUser user= mAuth.getCurrentUser();
                           openCamera();
                       }
                       else
                       {


                       }

                    }

                });
    }



    public void openCamera()
    {

        Intent intent = new Intent(this,Camera.class);
        startActivity(intent);

    }
    public void openSignup()
    {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }


}
