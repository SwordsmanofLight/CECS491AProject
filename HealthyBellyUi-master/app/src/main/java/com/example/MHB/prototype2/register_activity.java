package com.example.MHB.prototype2;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class register_activity extends AppCompatActivity
{
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("Healthy Belly");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLoginActivity();
            }
        });
    }

    public void openLoginActivity()
    {
        Intent loginIntent = new Intent(this, login_activity.class);
        startActivity(loginIntent);
    }

}