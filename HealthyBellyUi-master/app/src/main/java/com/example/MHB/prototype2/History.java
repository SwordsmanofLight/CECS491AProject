package com.example.MHB.prototype2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class History extends AppCompatActivity
{
    DrawerLayout dLayout;
    ActionBarDrawerToggle aToggle;
    NavigationView nView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("Healthy Belly");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        aToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open, R.string.close);
        nView = (NavigationView) findViewById(R.id.navigation_view);

        nView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.log_out:
                        openMainActivity();
                        break;
                    case R.id.delete_profile:
                        String[] options = {"• Yes, I'm sure."};

                        AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                        builder.setTitle("Are you sure you want to delete your profile?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                openMainActivity();
                            }
                        });
                        builder.show();
                        break;
                    case R.id.about_us:
                        openAboutUs();
                        break;
                    case R.id.help_contact:
                        openHelp();
                        break;
                    case R.id.update_profile:
                        openProfile();
                        break;
                    case R.id.payment:
                        openPayment();
                        break;
                    case R.id.history:
                        openHistory();
                        break;
                    case R.id.search:
                        openSearch();
                        break;
                    case R.id.home:
                        openHome();
                        break;
                }
                return true;
            }
        });

        dLayout.addDrawerListener(aToggle);
        aToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (aToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openMainActivity()
    {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void openAboutUs()
    {
        Intent aboutIntent = new Intent(this, AboutUs.class);
        startActivity(aboutIntent);
    }

    public void openHelp()
    {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }

    public void openProfile()
    {
        Intent profileIntent = new Intent(this, UpdateProfile.class);
        startActivity(profileIntent);
    }

    public void openPayment()
    {
        Intent paymentIntent = new Intent(this, Payment.class);
        startActivity(paymentIntent);
    }

    public void openHistory()
    {
        Intent historyIntent = new Intent(this, History.class);
        startActivity(historyIntent);
    }

    public void openSearch()
    {
        Intent searchIntent = new Intent(this, Search.class);
        startActivity(searchIntent);
    }

    public void openHome()
    {
        Intent searchIntent = new Intent(this, Homepage.class);
        startActivity(searchIntent);
    }
}