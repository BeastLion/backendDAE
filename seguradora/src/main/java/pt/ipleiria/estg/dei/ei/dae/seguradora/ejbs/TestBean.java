package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Test;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Stateless
public class TestBean {
    @PersistenceContext
    private EntityManager em;
    public void getA() {
        try {
            System.out.println("ENTREI                   ");
            // Create a URL for the desired page
            URL url = new URL("http://host.docker.internal:8000/insurers");

            // Create an HttpURLConnection.  This is useful for setting the request method, headers, and so on.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println("                                        GETTTTTTTTTTTTTTTT");
            // Read the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            System.out.println("                                        BUFFER");
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            // Parse the JSON data and create a list of Insure objects
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            System.out.println("                                        JSONREADER");
            JsonArray array = jsonReader.readArray();
            for (int i = 0; i < array.size(); i++) {
                System.out.println("                                        FOR");
                JsonObject object = array.getJsonObject(i);
                String id = object.getString("id");
                System.out.println("                                        FOR");
                String nif = object.getString("nif");
                System.out.println("                                        FOR");
                Test test = new Test(Long.parseLong(id),nif);

                em.persist(test);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
