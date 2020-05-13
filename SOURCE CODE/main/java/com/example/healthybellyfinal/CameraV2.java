package com.example.healthybellyfinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Objects;

public class CameraV2 extends AppCompatActivity
{
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
            "raw meat", "raw fish", "shark", "swordfish", "king mackerel", "tuna", "mercury", "mulethi",
            "Licorice", "Bisphenol A", "BPA", "Biphenols", "ephedra", "angelica", "kava", "yohimbe",
            "black cohosh", "blue cohosh", "dong quai", "borage oil", "pennyroyal", "mugwort",
            "MSG", "Monosodium glutamate", "Quinoline Yellow", "E104", "Cochineal", "E120",
            "Indigo Carmine", "E132", "Green S", "E142", "Ponceau 4R", "E124", "Allura Red AC", "E129",
            "Erythrosine", "E127", "Patent Blue V", "E131", "Tartrazine", "E102", "Phthalates",
            "Perfluoroalkyl Chemicals", "PFCs", "Perchlorate", "Nitrates", "Nitrites"};
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    // private TextView barcodeText;

    // THIS BIG BOI
    private String barcodeData;

    public static String globalBarcode;


    ///////////////

    public ImageView resultImage;
    public TextView unsafeResultTV, itemNameTV;
    int[] images = {R.drawable.happy_comp, R.drawable.sad_comp};

    //////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        surfaceView = findViewById(R.id.surface_view);
        //barcodeText = findViewById(R.id.barcode_text);
        initialiseDetectorsAndSources();

        /////////////
        itemNameTV = findViewById(R.id.barcode_text);
        unsafeResultTV = findViewById(R.id.Result);
        resultImage = findViewById(R.id.imageView);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        final DocumentReference documentReference = fStore.collection("Users")
                .document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                allergiesList = documentSnapshot.getString("allergies");
            }
        });

        /////////////
    }

    private void initialiseDetectorsAndSources()
    {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(CameraV2.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(CameraV2.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    itemNameTV.post(new Runnable() {

                        @Override
                        public void run()
                        {
                            //this is where the magic happens
                            barcodeData = barcodes.valueAt(0).displayValue;
                            globalBarcode = barcodeData;
                            //Toast.makeText(getApplicationContext(),barcodeData,Toast.LENGTH_SHORT).show();


                            //barcodeText.setText(barcodeData);

                            // stops from scanning multiples
                            if (barcodes.size() > 0) {
                                cameraSource.stop();
                                ///////////////////////////////////////////////////////////
                                fAuth = FirebaseAuth.getInstance();
                                fStore = FirebaseFirestore.getInstance();
                                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                ///////////////////////////////////////////////////////////
                                GetInfo2 getInfo = new GetInfo2();
                                getInfo.execute();
                                }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    ///////////////

    private class GetInfo2 extends AsyncTask<Void,Void,Void>
    {
        String ingredients = "";
        String unsafeItemsFound = UNSAFEHEADER;
        String test = "",
                itemName="";
        boolean validBarcode = true;

        @Override
        protected void onPreExecute() {
            //Toast.makeText(getApplicationContext(),"Barcode: " + inputBarcode, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground (Void... arg0){

            HttpHandler sh = new HttpHandler();

            // barcode: 012546638012
            String url = "https://us.openfoodfacts.org/api/v0/product/" + (CameraV2.globalBarcode) + ".json";

         try {
             //Stores the response received from API call in a string
             String jsonToString = sh.makeServiceCall(url);

             test = jsonToString;

             //If barcode is invalid
             if (jsonToString.contains("product not found")) {
                 validBarcode = false;
             }

             //If barcode is valid, it parses the string and stores the ingredients and name in 2
             //different string variables

             else {

                 JsonElement jelement = new JsonParser().parse(jsonToString);
                 JsonObject jobject = jelement.getAsJsonObject();

                 Log.e("j-element", jelement.toString());

                 ingredients = jobject.getAsJsonObject("product")
                         .get("ingredients_text_with_allergens").getAsString();
                 itemName = jobject.getAsJsonObject("product")
                         .get("product_name").getAsString();

             }
         }catch(Exception e)
            {
                Log.e("do in background", e.getMessage());}
            return null;

        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
            if(!validBarcode)
            {
                Toast.makeText(getApplicationContext(),"Product not found!", Toast.LENGTH_SHORT).show();
            }

            else{
            Toast.makeText(getApplicationContext(), itemName, Toast.LENGTH_SHORT).show();
            itemNameTV.setText(itemName);
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
