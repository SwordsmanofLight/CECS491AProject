package com.example.MHB.prototype2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button loginButton;
    private Button registerButton;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("Healthy Belly");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLoginActivity();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openRegisterActivity();
            }
        });

        addData();
    }

    public void addData()
    {
        myDb.insertData("3424005",
                "Hersheys Milk Chocolate",
                "Hersheys",
                "1.55 OZ",
                "milk chocolate (sugar, milk , chocolate, cocoa butter, lactose , milk fat, soy lecithin, pgpr, emulsifier, vanillin, artificial flavor)"
        );

        myDb.insertData("25000061516",
                "Apple Juice",
                "Minute Maid",
                "12 OZ",
                "Water, concentrated apple juice, Vitamin C"
        );

        myDb.insertData("28400589895",
                "Cheetos Flamin Hot",
                "Frito-Lay",
                "8.5 OZ",
                "Enriched Corn Meal (Corn Meal, Ferrous Sulfate, Niacin, Thiamin Mononitrate, Riboflavin, And Folic Acid), Vegetable Oil (Corn, Canola, and/or Sunflower Oil), Flamin Hot Seasoning (Maltodextrin Made from Corn , Salt, Sugar, Monosodium Glutamate, Yeast Extract, Citric Acid, Artificial Color Red 40 Lake, Yellow 6 Lake, Yellow 6, Yellow 5 , Sunflower Oil, Cheddar Cheese Milk, Cheese Cultures, Salt, Enzymes , Onion Powder, Whey, Whey Protein Concentrate, Garlic Powder, Natural Flavor, Buttermilk, Sodium Diacetate, Disodium Inosinate, Disodium Guanylate), and Salt."
        );

        myDb.insertData("40000000310",
                "M&M Plain",
                "M&M",
                "1.69 oz",
                "Milk Chocolate (Sugar, Chocolate, Cocoa Butter, Skim Milk, Milkfat, Lactose, Soy Lecithin, Salt, Artificial Flavors), Sugar, Cornstarch, Corn Syrup, Gum Acacia, Coloring, Dextrin."
        );

        myDb.insertData("44000015923",
                "Mini Oreo Cookies",
                "Nabisco",
                "8 oz",
                "Unbleached Enriched Flour (Wheat Flour, Niacin, Reduced Iron, Thiamine Mononitrate {Vitamin B1}, Riboflavin {Vitamin B2}, Folic Acid), Sugar, Palm and/or Canola Oil, Cocoa (processed with Alkali), Dextrose, High Fructose Corn Syrup, Baking Soda, Cornstarch, Salt, Soy Lecithin, Chocolate, Vanillin - an Artificial Flavor."
        );

        myDb.insertData("52000135282",
                "Gatorade Fierce Strawberry",
                "Gatorade",
                "28 OZ",
                "WATER, SUGAR, DEXTROSE, CITRIC ACID, SALT, SODIUM CITRATE, MONOPOTASSIUM PHOSPHATE, GUM ARABIC, NATURAL AND ARTIFICIAL FLAVOR, PURPLE SWEET POTATO JUICE (COLOR), GLYCEROL ESTER OF ROSIN."
        );

        myDb.insertData("70462098358",
                "Sour Patch Kids",
                "Mondelez",
                "3.5 OZ",
                "Sugar, invert sugar, corn syrup, modified corn starch, tartaric acid, citric acid, natural and artificial flavoring, yellow 6, red 40, yellow 5 and blue 1."
        );

        myDb.insertData("71100005509",
                "Ranch Dressing",
                "Hidden Valley",
                "8 OZ",
                "Vegetable oil (soybean and/or canola), water, egg yolk, sugar, salt, cultured nonfat buttermilk, natural flavors (soy), less than 1% of: spices (mustard), dried garlic, dried onion, vinegar, phosphoric acid, xanthan gum, modified food starch, monosodium glutamate, artificial flavors, disodium phosphate, sorbic acid and calcium disodium edta as preservatives, disodium inosinate, disodium guanylate. Contains: Egg, Milk, Soy."
        );

        myDb.insertData("76183643631",
                "Kiwi Strawberry",
                "Snapple",
                "64 oz",
                "Filtered Water, Sugar, Kiwi Juice Concentrate, Citric Acid, Strawberry Juice Concentrate, Acacia Gum, Natural Flavors, Vegetable Juice Concentrate (for Color), Ester Gum."
        );

        myDb.insertData("616641649869",
                "Skittles",
                "Wrigley Company",
                "1LB",
                "Sugar, corn syrup, hydrogenated palm kernel oil, Citric Acid, Tapioca Dextrin, Modified Corn Starch, Natural and Artificial Flavors, Colors, Sodium Citrate, Carnauba Wax."
        );

        myDb.insertData("757528005047",
                "Takis Fuego",
                "Barcel",
                "9 oz",
                "CORN MASA FLOUR (PROCESSED WITH LIME), SOYBEAN AND/OR PALM AND/OR CANOLA OIL, IODIZED SALT, SUGAR, NATURAL AND ARTIFICIAL FLAVOR, CITRIC ACID, SOY PROTEIN, YEAST, MONOSODIUM GLUTAMATE, MALTODEXTRIN SODIUM DIACETATE, PARTIALLY HYDROGENATED SOYBEAN OIL, ARTIFICIAL COLOR, ONION POWDER, HOT CHILI PEPPER (CHILE), SODIUM BICARBONATE, SODIUM GUANYLATE, SODIUM INOSINATE, SILICON DIOXIDE (ANTICAKING)"
        );
    }

    public void openLoginActivity()
    {
        Intent loginIntent = new Intent(this, login_activity.class);
        startActivity(loginIntent);
    }

    public void openRegisterActivity()
    {
        Intent registerIntent = new Intent(this, register_activity.class);
        startActivity(registerIntent);
    }
}