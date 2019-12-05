package com.example.MHB.prototype2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity
{
    DrawerLayout dLayout;
    ActionBarDrawerToggle aToggle;
    NavigationView nView;

    ListView results;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    Button searchButton;
    TextInputEditText input;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("Healthy Belly");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        aToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open, R.string.close);
        nView = (NavigationView) findViewById(R.id.navigation_view);
        results = (ListView)findViewById(R.id.results);
        searchButton = (Button)findViewById(R.id.search_button);
        input = (TextInputEditText)findViewById(R.id.textInputEditText);

        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        results.setAdapter(adapter);

        myDb = new DatabaseHelper(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                adapter = null;
                arrayList.clear();

                String value = input.getText().toString();

                Cursor res = myDb.getAllData();

                int count = 0;

                if (!value.equals(""))
                {
                    while (res.moveToNext())
                    {
                        String name = res.getString(1);

                        if (name.toLowerCase().contains(value.toLowerCase()))
                        {
                            count++;

                            String data = "";

                            data += "Name: " + res.getString(1) + " ";
                            data += "Brand: " + res.getString(2);

                            arrayList.add(data);
                        }
                    }
                }

                if (count == 0)
                {
                    arrayList.add("No results...");
                }

                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
                results.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
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