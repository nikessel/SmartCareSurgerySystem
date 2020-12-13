package model;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *
 * @author Callum
 */
public class Geocoding {
    private static final String API_KEY = "AIzaSyD8oPDTMzQ-85uLrbyJerocg2Pv1Z3zYtU";
    public Boolean validateGeocode(String postcode) throws IOException {
        // Change url to inject postcode into the url after ensuring the URL is formatted correctly
        URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=" + postcode + "&key=" + API_KEY);
        try{                  
            //Making the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "apploication/json");
            //Check the response code
            if (conn.getResponseCode() != 200){
                throw new RuntimeException("Failed : HTTP eror code :" + conn.getResponseCode());
            }

            //Reading the data from the url
            BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
            String output;
            String out="";        
            while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                    out+=output;
            }
        
            conn.disconnect();              
        }
        catch (IOException e){
            throw new RuntimeException("Error : " + e.getMessage());            
        }       
        return false;
    }
}
