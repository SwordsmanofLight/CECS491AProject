
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.HttpURLConnection;

import java.io.BufferedReader;

public class Connection {
	
	private static HttpURLConnection connection;

	public static void main(String[] args) {
		
	
		BufferedReader reader;
		String line;
		
		StringBuffer responseContent = new StringBuffer();
		try {
			
		//URL url = new URL("https://jsonplaceholder.typicode.com/albums");
		URL url = new URL("https://world.openfoodfacts.org/api/v0/product/8715700407760.json");
		
		connection = (HttpURLConnection)url.openConnection();
		
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		
		int status = connection.getResponseCode();
		 
		System.out.print(status);
		
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
		
		//JSONObject obj = new JSONObject();
		
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
		
		JSONArray food = new JSONArray(responseBody);
		
		for(int i = 0; i< food.length();i++) {
			JSONObject album = food.getJSONObject(i);
			
			String ingredients = album.getString("ingredients");
			
			System.out.println("Ingredients: "  + ingredients);
		}
		
		
		//System.out.println("In parse");
		return null;
		
	}
	

}
