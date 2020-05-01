package com.example.healthybellyfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

public class Search extends AppCompatActivity {

    private Button search;
    private String finalApiCall;
    long inputBarcode;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String allergiesList;
    String userID;
    final private String UNSAFEHEADER = "The following has been detected as unsafe for you to eat because of the following ingredients: ";

    final private String [] milkAllergies = {"Butter", "Butter fat", "butter oil", "butter acid",
            "butter ester", "Buttermilk", "Casein", "Casein hydrolysate","Caseinates", "Cheese",
            "Cottage cheese", "Cream", "Curds", "Custard", "Diacetyl",
            "Ghee","Half-and-half", "Lactalbumin", "lactalbumin phosphate", "Lactoferrin",
            "Lactose", "Lactulose", "Milk", "Milk protein hydrolysate", "Pudding", "Recaldent",
            "Rennet casein", "Sour cream", "Sour milk solids", "Tagatose", "Whey",
            "Whey protein hydrolysate", "Yogurt"};
    final private String [] eggAllergies = {"Albumin", "Albumen", "Egg", "Eggnog", "Lysozyme",
            "Mayonnaise", "Meringue", "Ovalbumin", "Surimi"};
    final private String [] treeNutAllergies = {"Almond", "Artificial nuts", "Beechnut", "Black walnut hull extract", "Brazil nut", "Butternut",
            "Cashew", "Chestnut", "Chinquapin nut", "Coconut", "Filbert", "Hazelnut", "Gianduja",
            "Ginkgo nut", "Hickory nut", "Litchi", "Lichee", "Lychee nut", "Macadamia nut", "Marzipan", "Almond paste",
            "Nangai nut", "Natural nut extract", "Nut", "Nuts", "Nut butters", "Nut distillates", "Nut meal",
            "Nut meat", "Nut milk", "Nut oils", "Nut paste", "Nut pieces", "Pecan", "Pesto",
            "Pili nut", "Pine nut", "Indian nut", "Pignoli nut", "Pigñolia", "Pignon nut", "Piñon nut",
            "Pinyon nut", "Pistachio", "Praline", "Shea nut", "Walnut", "Walnut hull extract"};
    final private String [] peanutAllergies = {"Arachis oil",
            "peanut oil", "Artificial nuts", "Beer nuts", "Goobers", "Ground nuts",
            "Lupin", "Lupine", "Mandelonas", "Mixed nuts", "Monkey nuts", "Nut meat", "Nut pieces",
            "Peanut", "Peanuts", "Peanut butter", "Peanut flour", "Peanut protein hydrolysate", "Arachis oils",
            "peanut oils", "Artificial nut", "Beer nut", "Goober", "Ground nut", "Lupins",
            "Lupines", "Mandelona", "Mixed nut", "Monkey nut", "Nut meats", "Nut piece",
            "Peanut protein hydrolysates"};
    final private String [] shellFishAllergies = {"Barnacle", "Crab", "Crawfish", "Crawdad", "Crayfish",
            "Ecrevisse", "Krill", "Lobster", "Langouste", "Langoustine", "Moreton bay bugs", "Scampi", "Shellfish",
            "Tomalley", "Prawns", "Shrimp", "Crevette"};
    final private String [] wheatAllergies = {"Bread crumbs",
            "Bulgur", "Cereal extract", "Club wheat", "Couscous", "Cracker meal", "Durum", "Einkorn",
            "Emmer", "Farina", "Farro", "Flour", "Freekeh", "Hydrolyzed wheat protein", "Kamut", "Matzoh",
            "Matzo", "Matzah", "Matza", "Pasta", "Seitan", "Semolina", "Spelt", "Sprouted wheat", "Triticale", "Vital wheat gluten",
            "Wheat", "Wheat bran hydrolysate", "Wheat germ oil", "Wheat grass", "Wheat protein isolate", "Whole wheat berries"};
    final private String [] soyAllergies = {"Soy oil",
            "Edamame", "Miso", "Natto", "Shoyu", "Soy", "Soya", "Soybean", "Curd", "Granules",
            "Soy", "Soy protein", "Soy sauce", "Tamari", "Tempeh", "Textured vegetable protein", "TVP", "Tofu"};
    final private String [] fishAllergies = {"Anchovies",
            "Bass", "Catfish", "Cod", "Fish", "Fish oil", "Flounder", "Grouper", "Haddock", "Hake", "Halibut",
            "Herring", "Mahi mahi", "Perch", "Pike", "Pollock", "Salmon", "Scrod", "Sole", "Snapper",
            "Swordfish", "Tilapia", "Trout", "Tuna"};
    final private String [] pregnantList = {"Alcohol", "Unpasteurized", "Raw alfalfa", "Raw clover",
            "Raw radish", "Raw sprout", "Raw Sprouts", "Organ meat", "raw egg", "raw eggs", "raw",
            "raw meat", "raw fish", "shark", "swordfish", "king mackerel", "tuna", "mercury"};



    public ImageView resultImage;
    public TextView itemNameTV, unsafeResultTV;
    int[] images = {R.drawable.happy_comp, R.drawable.sad_comp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        itemNameTV = findViewById(R.id.FoodName);
        unsafeResultTV = findViewById(R.id.Result);
        search = (Button) findViewById(R.id.seachButton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        resultImage = findViewById(R.id.imageView);

        final DocumentReference documentReference = fStore.collection("Users")
                .document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                allergiesList = documentSnapshot.getString("allergies");
            }
        });

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText Barcode = findViewById(R.id.barcode);

                //If barcode field is blank
                if (Barcode.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"Please enter a barcode", Toast.LENGTH_SHORT).show();
                }
                else{
                //Toast.makeText(getApplicationContext(),"Barcode: " + inputBarcode, Toast.LENGTH_SHORT).show();
                    inputBarcode = Long.parseLong(Barcode.getText().toString());
                    fAuth = FirebaseAuth.getInstance();
                    fStore = FirebaseFirestore.getInstance();
                    userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

                    GetInfo getInfo = new GetInfo();
                    getInfo.execute();
                }


            }
        });



    }

    private class GetInfo extends AsyncTask<Void,Void,Void>
    {
        String ingredients = "";
        String unsafeItemsFound = UNSAFEHEADER;
        String test = "", itemName="";
        boolean validBarcode = true;
        @Override
        protected void onPreExecute() {
            //Toast.makeText(getApplicationContext(),"Barcode: " + inputBarcode, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground (Void... arg0){

            HttpHandler sh = new HttpHandler();
            String url = "https://world.openfoodfacts.org/api/v0/product/" + String.valueOf(inputBarcode) + ".json";

            //Stores the response received from API call in a string
            String jsonToString=sh.makeServiceCall(url);

            test = jsonToString;

            //If barcode is invalid
            if(jsonToString.contains("product not found"))
            {
                validBarcode = false;
            }

            //If barcode is valid, it parses the string and stores the ingredients and name in 2
            //different string variables

            else if(jsonToString != null)
            {

                JsonElement jelement = new JsonParser().parse(jsonToString);
                JsonObject jobject = jelement.getAsJsonObject();

                ingredients = jobject.getAsJsonObject("product").get("ingredients_text_debug").getAsString();
                itemName = jobject.getAsJsonObject("product").get("generic_name").getAsString();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if(!validBarcode)
            {
                Toast.makeText(getApplicationContext(),"Product not found!", Toast.LENGTH_SHORT).show();
            }

            else {

                Toast.makeText(getApplicationContext(), "RESULT", Toast.LENGTH_SHORT).show();
                itemNameTV.setText(itemName);
                //unsafeResultTV.setText(ingredients);
                unsafeResultTV.setText(filterItems());
                if (unsafeItemsFound.equals("No Unsafe Items!")){
                    resultImage.setImageResource(images[0]);
                }
                else{
                    resultImage.setImageResource(images[1]);
                }

            }
        }

        private String filterItems() {

            if (allergiesList.contains("Dairy")) {
                for (String unsafeItem : milkAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Eggs")) {
                for (String unsafeItem : eggAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Tree Nuts")) {
                for (String unsafeItem : treeNutAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Peanuts")) {
                for (String unsafeItem : peanutAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Shell Fish")) {
                for (String unsafeItem : shellFishAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Wheat")) {
                for (String unsafeItem : wheatAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Soy")) {
                for (String unsafeItem : soyAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }
            if (allergiesList.contains("Fish")) {
                for (String unsafeItem : fishAllergies) {
                    if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                        unsafeItemsFound += unsafeItem + ", ";
                    }
                }
            }

            for (String unsafeItem : pregnantList) {
                if (ingredients.toLowerCase().contains(unsafeItem.toLowerCase())) {
                    unsafeItemsFound += unsafeItem + ", ";
                }
            }


            if (unsafeItemsFound.equals(UNSAFEHEADER)){
                unsafeItemsFound = "No Unsafe Items!";
            }
            else{
                unsafeItemsFound = unsafeItemsFound.substring(0, unsafeItemsFound.length() - 2);
            }


            return unsafeItemsFound;
        }

    }

}
