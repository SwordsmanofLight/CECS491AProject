
import java.util.Scanner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.HttpURLConnection;

import java.io.BufferedReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Connection {
	
	//public String url;
	
	private static HttpURLConnection connection;

	public static void main(String[] args) {
		
	
		//String url = "https://world.openfoodfacts.org/api/v0/product/" + String.valueOf(barcode) + ".json";
		
		//setUpConnection(url);
		
		setURL(737628064502L);
	
	}
	
	public static void setURL(long barcode) {
		
		String url = "https://world.openfoodfacts.org/api/v0/product/" + String.valueOf(barcode) + ".json";
		setUpConnection(url);
		 
	}
	
	//This method takes the URL as parameter and sets up the connection
	public static void setUpConnection (String link) {
		
		BufferedReader reader;
		String line;
		
		StringBuffer responseContent = new StringBuffer();
		
		try {			
		
			URL url = new URL(link);
			
			connection = (HttpURLConnection)url.openConnection();			
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			
			int status = connection.getResponseCode();
			 
			//System.out.print(status);
			
			//Reader reads the error message
			if(status > 299) {
				
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				
				reader.close();
			}
			
			//If connection is successful
			
			else {
				
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				
				reader.close();
				
			}
			
			//System.out.println(responseContent.toString());
			
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		
		//Calls the parse method to perse the .JSON response
		parse(responseContent.toString());
		
	}
	
	//This method takes a string of the response received from the API and parses it, and gets the ingredients
	public static String parse (String responseBody) {
		
		
		JsonElement jelement = new JsonParser().parse(responseBody);
		JsonObject  jobject = jelement.getAsJsonObject();
		
		//System.out.print(jobject.toString());
		
		System.out.print(jobject.getAsJsonObject("product").get("ingredients_text_debug"));
		
		String ingredients = jobject.getAsJsonObject("product").get("ingredients_text_debug").getAsString();
		
		filterItems(ingredients);
		
		return null;
		
	}
	
	
	//This method takes a string of ingredients and filters the unsafe items
	static void filterItems(String ingredients) {	
		
		String [] unsafeItems = new String[3];
		String unsafeItemsFound = "";
		
		unsafeItems[0]= "SUGAR";
		unsafeItems[1]= "SPICES";
		unsafeItems[2]= "CHILI";
		
		for(int i = 0; i< unsafeItems.length; i++)
		{
			if(ingredients.contains(unsafeItems[i]))
			{
				unsafeItemsFound += unsafeItems[i] + ", ";
			}
		}
		
		System.out.println("\nFound: " +unsafeItemsFound + "which is not safe to eat");
	}

}
