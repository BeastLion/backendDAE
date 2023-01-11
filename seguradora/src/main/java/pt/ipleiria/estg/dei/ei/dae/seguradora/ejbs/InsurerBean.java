package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Stateless
public class InsurerBean {
    public void getAll(){
        try {
            // Create a URL for the desired page
            URL url = new URL("http://127.0.0.1:8000/insurers");
            // Create an HttpURLConnection.  This is useful for setting the request method, headers, and so on.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Read the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
