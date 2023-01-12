package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Stateless
public class LoremBean {
    public StringBuilder connect(String endpoint){
        try {
            URL url = new URL("http://host.docker.internal:8000/" + endpoint);
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
            return response;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
