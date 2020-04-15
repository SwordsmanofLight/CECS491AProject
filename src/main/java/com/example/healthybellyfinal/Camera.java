package com.example.healthybellyfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Camera extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.profile:
                openProfile();
                Toast.makeText(Camera.this,"profile selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                openaboutUs();
                Toast.makeText(Camera.this,"about us selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                openSettings();
                Toast.makeText(Camera.this,"settings selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(Camera.this,"logout selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(Camera.this,"search selecte",Toast.LENGTH_SHORT).show();
                openSearch();
                break;
        }
        return false;
    }

    public void openaboutUs()
    {
        Intent intent =new Intent(this,AboutUs.class);
        startActivity(intent);
    }
    public void openSearch()
    {
        Intent intent= new Intent(this,Search.class);
        startActivity(intent);
    }
    public void openProfile()
    {
        Intent intent= new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void openSettings()
    {
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);

    }




}
