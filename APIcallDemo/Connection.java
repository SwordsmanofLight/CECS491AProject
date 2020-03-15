
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
		
	
		BufferedReader reader;
		String line;
		
		StringBuffer responseContent = new StringBuffer();
		try {
			
		//URL url = new URL("https://jsonplaceholder.typicode.com/albums");
		URL url = new URL("https://world.openfoodfacts.org/api/v0/product/737628064502.json");
		
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
	
	
	public static String parse (String responseBody) {
		
		
		System.out.println("In parse");
		
		JSONObject food = new JSONObject(responseBody);
		
		
		
		//System.out.println(food);
		
		int id = food.getInt("code");	
		System.out.println("ID: "  + id);
		
		System.out.println("\n\nIngredients \n");
		String ingredients = food.getJSONObject("product").getString("ingredients_text_debug");
		System.out.println(ingredients);
		//System.out.println(food.getJSONObject("product").getString("ingredients_text_debug"));
		
		
		//JSONObject product = new JSONObject(food.getJSONObject("product"));
		//System.out.println(product.);
		
		//System.out.println(product.get("image_ingredients_url"));
		//String x = product.getString("image_ingredients_url");
		//System.out.println("ID: "  + x);
		
		
		return null;
		
	}
	

}
