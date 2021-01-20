package model;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
/**
 * 
 * @author Callum Gill ID: 1020363
 * 
 */

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class Geocoding {

    public static class SSLTool {

        public static void disableCertificateValidation() {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

            // Ignore differences between given hostname and certificate hostname
            HostnameVerifier hv = (String hostname, SSLSession session) -> true;

            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(hv);
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
            }
        }
    }
    // Private attributes used to store all relevant data contained in the API response
    private static String status = null;
    private static String formattedAddress = null;
    private static String postcode = null;
    private static String route = null;
    private static String locality = null;
    private static String postal_town = null;
    private static String administrative_area_level_1 = null;
    private static String administrative_area_level_2 = null;
    private static String country = null;

    // API key is restricted to Geocode of Google Maps only
    private static final String API_KEY = "AIzaSyD8oPDTMzQ-85uLrbyJerocg2Pv1Z3zYtU";

    private static void clearAll() {
        status = null;
        formattedAddress = null;
        postcode = null;
        route = null;
        locality = null;
        postal_town = null;
        administrative_area_level_1 = null;
        administrative_area_level_2 = null;
        country = null;
    }

    public static boolean validateGeocode(String postcode) {

        try {
            if (postcode.isEmpty()) {
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        }

        clearAll();

        //Replace empty spaces with nothing       
        postcode = postcode.replace(" ", "");
        // Change url to inject postcode into the url after ensuring the URL is formatted correctly         
        try {
            // Cerate URL object to store the URL to geocode api and to inject postcode parameter and the API key
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=" + postcode + "+UK&key=" + API_KEY);
            //Making the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Set request method to GET
            conn.setRequestMethod("GET");
            //Set request propery
            conn.setRequestProperty("Accept", "application/xml");
            //Check the response code
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP eror code :" + conn.getResponseCode());
            }

            //Reading the data from the url using a Buffered reader
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            String out = "";

            // Loop through br reader and assemble XML string from the response
            while ((output = br.readLine()) != null) {
                out += output;
            }

            // Create new Document object
            Document document = StringToDocument(out);
            // Normalise all document elements
            document.getDocumentElement().normalize();
            // Get all nodes begining with tag Gecode Response
            NodeList list = document.getElementsByTagName("GeocodeResponse");
            // Loop through array list (Will only loop once as there is only one GeoCodeResponse tag)
            for (int i = 0; i < list.getLength(); i++) {
                // Create GeoResponse node and convert to element
                Node GeoResponse = list.item(i);
                Element GeoElement = (Element) GeoResponse;
                // Get the status of the response contained in the status node
                status = GeoElement.getElementsByTagName("status").item(0).getTextContent();

                // Check if the status is OK so that data can be assigned to the attirbutes
                if (status.equals("OK")) {
                    // Get the formatted address and assign to the attribute formattedAddress
                    formattedAddress = GeoElement.getElementsByTagName("formatted_address").item(0).getTextContent();
                    //Convert Georesponse ndoe to Element
                    Element eElement = (Element) GeoResponse;
                    // Get results node so that child nodes can be retrieved
                    NodeList list2 = eElement.getElementsByTagName("result");
                    // Check if there is a result node otherwise address components can't be extracted
                    if (list2.getLength() > 0) {
                        // Create result node variable
                        Node result = list2.item(0);
                        // Get all child nodes of the result node that have the tag address_component
                        List<Node> AddressComponents = getChildNodesWithName(result, "address_component");
                        // Loop through all child ndoes and assign the data to an attribute
                        for (int nodeIndex = 0; nodeIndex < AddressComponents.size(); nodeIndex++) {
                            AssignAttribute((Element) AddressComponents.get(nodeIndex));
                        }
                    }
                } else {
                    return false;
                }
            }
            // Disconnect the connection
            conn.disconnect();
            return true;
        } catch (SSLHandshakeException ex) {
            SSLTool.disableCertificateValidation();
            return validateGeocode(postcode);
        } catch (RuntimeException | IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static void AssignAttribute(Element NodeElement) {
        //Assign text content of the "type" text node
        String TypeContent = NodeElement.getElementsByTagName("type").item(0).getTextContent();
        // Get text content of "long_name" text node
        String ComponentContent = NodeElement.getElementsByTagName("long_name").item(0).getTextContent();
        // Determine which attribute to assign the text content to using the type content

        System.out.println(TypeContent + ": " + ComponentContent);
        switch (TypeContent) {
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
            case "administrative_area_level_2":
                administrative_area_level_2 = ComponentContent;
                break;
        }
    }

    private static List<Node> getChildNodesWithName(Node parentNode, String name) {
        // Get all child nodes of the parent node and assign to a NodeList
        NodeList ChildNodes = parentNode.getChildNodes();
        // Decalre a list of Nodes to store all nodes matching the criteria
        List<Node> results = new ArrayList<>();
        // Loop through the ChildNodes list and check if any of the nodes ahve the tag name passed to the function
        // Once all nodes have been checked then the results list is returned
        for (int i = 0; i < ChildNodes.getLength(); i++) {
            if (ChildNodes.item(i) instanceof Text) {
                continue;
            }
            if (ChildNodes.item(i).getNodeName().equals(name)) {
                results.add(ChildNodes.item(i));
            }
        }
        return results;
    }

    private static Document StringToDocument(String XMLSource) {
        try {
            // Create new instance of DocumentBuilderFactory object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Create new Document builder from the DocumentBuilderFactory object
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Return the new XML Document
            return builder.parse(new InputSource(new StringReader(XMLSource)));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // All appropiate get methods for all the attributes
    public static String getStatus() {
        return status;
    }

    public static String getPostcode() {
        return postcode;
    }

    public static String getRoute() {
        return route;
    }

    public static String getTown() {
        return locality;
    }

    public static String getPostalTown() {
        return postal_town;
    }

    public static String getAreaLevel1() {
        return administrative_area_level_1;
    }

    public static String getArealevel2() {
        return administrative_area_level_2;
    }

    public static String getCountry() {
        return country;
    }

    public static Address getAddress() {
        Address address = new Address(route, "", postcode, administrative_area_level_2, postal_town, "");

        return address;
    }

}
