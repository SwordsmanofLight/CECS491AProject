import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

/*import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;*/

public class Connection {
	
	public static void main(String[] args) throws IOException {
	    //System.out.println("Hello world");
		MyGETRequest();
	}
	
	public static void MyGETRequest() throws IOException {
	    //URL urlForGetRequest = new URL("https://api.nal.usda.gov/fdc/v1/354148?api_key=BmpQTRpIIENgiaYN80vTCvm1cqieFWewKHEYvLHA");
		URL url = new URL("https://api.nal.usda.gov/fdc/v1/354148?api_key=BmpQTRpIIENgiaYN80vTCvm1cqieFWewKHEYvLHA");
	    String readLine = null;
	    //static String json = "...";
	    //JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
	    
	    HttpURLConnection conection = (HttpURLConnection) url.openConnection();
	    conection.setRequestMethod("GET");
	    //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
	    int responsecode = conection.getResponseCode();
	    String inline = null;
	    
	    /*if(responsecode != 200)
	    	throw new RuntimeException();
	    	else
	    	{
	    		Scanner sc = new Scanner(url.openStream());
	    		while(sc.hasNext())
	    		{
	    			inline+=sc.nextLine();
	    		}
	    		System.out.println("\n String data: ");
	    		System.out.println(inline);
	    		sc.close();
	    	}
	    
	    
	    JSONParser parse = new JSONParser();   
	    JSONObject jobj = (JSONObject)parse.parse(inline);
	    //JSONObject jobj = new JSONobject
	   
	    
	    JSONArray jsonarr_1 = (JSONArray) jobj.get(“ingredients”); */
	    
	    if (responsecode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	            
	        } in .close();
	        // print result
	        System.out.println("JSON String Result " + response.toString());
	        //contents = response.toString();
	        //GetAndPost.POSTRequest(response.toString());
	    } 
	    else {
	        System.out.println("GET NOT WORKED");
	    }	    
	}
}
