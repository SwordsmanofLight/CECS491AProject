package com.example.MHB.prototype2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login_activity extends AppCompatActivity
{
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("Healthy Belly");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        submitButton = (Button)findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openHomeActivity();
            }
        });

    }

    public void openHomeActivity()
    {
        Intent homeIntent = new Intent(this, Homepage.class);
        startActivity(homeIntent);
    }
}
