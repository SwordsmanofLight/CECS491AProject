
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.HttpURLConnection;

import java.io.BufferedReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.journaldev.json.model.Address;
//import com.journaldev.json.model.Employee;

public class Connection {
	
	private static HttpURLConnection connection;

	public static void main(String[] args) {
		
		String url = "https://world.openfoodfacts.org/api/v0/product/737628064502.json";
		
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
			parse(responseContent.toString());
		
		
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		
	}
	
	//This method takes a string of the response received from the API and parses it, and gets the ingredients
	public static String parse (String responseBody) {
		
		System.out.println("In parse");
		
		JSONObject food = new JSONObject(responseBody);
		
		//System.out.println(food);
		
		long id = food.getLong("code");	
		System.out.println("ID: "  + id);
		System.out.println("\n\nIngredients \n");
		String ingredients = food.getJSONObject("product").getString("ingredients_text_debug");
		System.out.println(ingredients);
		
		filterItems(ingredients);
		
		
		return null;
		
	}
	
	
	//This method takes a string of ingredients and filters the items
	static void filterItems(String ingredients) {	
		
		String [] unsafeItems = new String[2];
		
		unsafeItems[0]= "SUGAR";
		unsafeItems[1]= "SPICES";
		
		for(int i = 0; i< unsafeItems.length; i++)
		{
			if(ingredients.contains(unsafeItems[i]))
			{
				System.out.println("\nFound: " +unsafeItems[i] + " which is not safe to eat");
			}
		}
		
	}
	
	/*public static String parse (String responseBody) {
	
	JSONArray albums = new JSONArray(responseBody);
	
	for(int i = 0; i< albums.length();i++) {
		JSONObject album = albums.getJSONObject(i);
		int id = album.getInt("id");
		int userID = album.getInt("userId");
		String title = album.getString("title");
		
		System.out.println("ID: " + id + " User ID: " + userID + " Title: " + title);
	}
	
	return null;
	
}*/
	

}
