package model;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
/**
 * @author Callum
 */
public class Geocoding {
    /**
     * @param args the command line arguments
     */
    private static String status = null;
    private static String formattedAddress = null;
    private static String postcode = null;
    private static String route = null;
    private static String locality = null;
    private static String postal_town = null;
    private static String administrative_area_level_1 = null;
    private static String country = null;      
    // API key is restricted to Goocode of Google Maps only
    private static final String API_KEY = "AIzaSyD8oPDTMzQ-85uLrbyJerocg2Pv1Z3zYtU";
    public static void validateGeocode(String postcode){
        // Change url to inject postcode into the url after ensuring the URL is formatted correctly         
        try{    
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=" + postcode + "+UK&key=" + API_KEY);
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
            String out = "";        
            while ((output = br.readLine()) != null) {    
                System.out.println(output);
                out+=output;
            }          
            // Create new Document object
            Document document = StringToDocument(out);
            // Normalise all document elements
            document.getDocumentElement().normalize();
            // Get all nodes begining with tag Gecode Response
            NodeList list = document.getElementsByTagName("GeocodeResponse");
            // Loop through array list (Will only loop once as there is only one GeoCodeResponse tag)
            for (int i = 0; i < list.getLength(); i++){              
                Node GeoResponse = list.item(i);
                Element GeoElement = (Element) GeoResponse;
                status = GeoElement.getElementsByTagName("status").item(0).getTextContent();                        
                if (status.equals("OK")){
                    formattedAddress = GeoElement.getElementsByTagName("formatted_address").item(0).getTextContent();       
                    Element eElement = (Element) GeoResponse;
                    NodeList list2 = eElement.getElementsByTagName("result");
                    if (list2.getLength() > 0){
                        Node result = list2.item(0);
                        List<Node> AddressComponents = getChildNodesWithName(result, "address_component");
                        for (int nodeIndex = 0; nodeIndex < AddressComponents.size(); nodeIndex++){                    
                            AssignAttribute((Element) AddressComponents.get(nodeIndex));
                        }
                    }
                }
            }                                                               
            conn.disconnect();                    
        }         
        catch (MalformedURLException ex){
            throw new RuntimeException("Malformed URL passed");
        }
        catch (IOException e){
            throw new RuntimeException("Error : " + e.getMessage());            
        } 
        catch (RuntimeException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }                
    } 
    private static void AssignAttribute(Element NodeElement){
        String TypeContent = NodeElement.getElementsByTagName("type").item(0).getTextContent();
        String ComponentContent = NodeElement.getElementsByTagName("long_name").item(0).getTextContent();
        switch (TypeContent){
            case "postal_code":
                postcode = ComponentContent;
                break;
            case "route":
                route = ComponentContent;
                break;
            case "locality":
                locality = ComponentContent;
                break;
            case "postal_town":
                postal_town = ComponentContent;
                break;
            case "country":
                country = ComponentContent;
                break;
            case "administrative_area_level_1":
                administrative_area_level_1 = ComponentContent;
                break;
            
        }
    }
    private static List<Node> getChildNodesWithName(Node parentNode, String name){
        NodeList ChildNodes = parentNode.getChildNodes();   
        List<Node> results = new ArrayList<>();
        for (int i = 0; i < ChildNodes.getLength(); i++){
            if (ChildNodes.item(i) instanceof Text){
                continue;
            }
            if (ChildNodes.item(i).getNodeName().equals(name)){
                results.add(ChildNodes.item(i));
            }
        }
        return results;
    }   
    private static Document StringToDocument(String XMLSource){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(XMLSource)));
        }
        catch (SAXException |ParserConfigurationException | IOException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    public String getStatus(){
        return status;
    }
    public String getPostcode(){
        return postcode;
    }
    public String getRoute(){
        return route;
    }
    public String getTown(){
        return locality;
    }
    public String getPostalTown(){
        return postal_town;
    }
    public String getAreaLevel1(){
        return administrative_area_level_1;
    }
    public String getCountry(){
        return country;
    }
    public String ToString(){
        return formattedAddress;
    }
}