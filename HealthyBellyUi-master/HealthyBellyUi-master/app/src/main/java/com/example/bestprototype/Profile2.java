package com.example.bestprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile2 extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);


        button=(Button)findViewById(R.id.button14);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSearch();
            }
        });

        button=(Button)findViewById(R.id.button14);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSearch();
            }
        });
        button=(Button)findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openHistoric();
            }
        });
        button=(Button)findViewById(R.id.button15);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSettings();
            }
        });

        button=(Button)findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openCamera();
            }
        });



    }


    public void openSearch()
    {
        Intent intent =new Intent(this,Search.class);
        startActivity(intent);
    }

       public void openHistoric()
    {
        Intent intent =new Intent(this,Historic.class);
        startActivity(intent);
    }
    public void openSettings()
    {
        Intent intent =new Intent(this,Settings.class);
        startActivity(intent);
    }

    public void openCamera()
    {
        Intent intent =new Intent(this,Camera.class);
        startActivity(intent);
    }


}
