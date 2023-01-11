package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsurerType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;

import javax.ejb.EJB;
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
import java.util.ArrayList;
import java.util.List;

@Stateless
public class InsurerBean {
    @EJB
    private ExpertBean expertBean;

    public void getAll() {
        try {
            //---------------------------------
            // Create a URL for the desired page
            URL url = new URL("http://host.docker.internal:8000/insurers");
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
            //---------------------------------

            // Parse the JSON data and create a list of Insure objects
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonArray array = jsonReader.readArray();

            for (int i = 0; i < array.size(); i++) {
                System.out.println("--------------");
                JsonObject object = array.getJsonObject(i);
                String id = object.getString("id");
                String name = object.getString("Insurer");

                JsonArray expertNIF = object.getJsonArray("Expert");
                List<Expert> experts = new ArrayList<>();
                for (int j = 0; j < expertNIF.size(); j++) {
                    JsonObject object1 = expertNIF.getJsonObject(j);
                    String usernameExpert = object1.getString("username");
                    expertBean.setInsurer(usernameExpert, Integer.parseInt(id));
                    experts.add(expertBean.find(usernameExpert));
                }

                JsonArray insurances = object.getJsonArray("Insurances");
                List<Insurance> insurancesArray = new ArrayList<>();
                for (int k = 0; k < insurances.size(); k++) {
                    JsonObject object2 = insurances.getJsonObject(k);
                    String insurace_id = object2.getString("id");
                    String policyName = object2.getString("policyName");
                    InsurerType type = setJsonToInsurerType(object2.getString("type"));

                    JsonArray coverArray = object2.getJsonArray("covers");
                    List<OccurrenceType> occurrenceTypesArray = new ArrayList<>();

                    for (int l = 0; l < coverArray.size(); l++) {
                        occurrenceTypesArray.add(setJsonToOccurrenceType(coverArray.getString(l)));
                    }
                    Insurance insurance = new Insurance(Integer.parseInt(insurace_id), policyName, type);
                    for (OccurrenceType o : occurrenceTypesArray) {
                        insurance.addOccurrenceType(o);
                        System.out.println("OLA");
                    }
                    insurancesArray.add(insurance);
                }

                System.out.println("--------------");
                JsonArray services = object.getJsonArray("Services");
                List<RepairServices> servicesArray = new ArrayList<>();
                for(int n = 0; n < services.size();n++){
                    JsonObject object3 = services.getJsonObject(n);
                }

                //Creation of insurerOwner and set insurerOwner on insurance class
                InsurerOwner insurerOwner = new InsurerOwner(name, Integer.parseInt(id));
                for (Expert e : experts) {
                    insurerOwner.addExpert(e);
                }
                for (Insurance insurance : insurancesArray) {
                    insurerOwner.addInsurance(insurance);
                    insurance.setOwner(insurerOwner);
                }
                System.out.println(insurerOwner.getId());
                System.out.println(insurerOwner.getName());
                System.out.println("Experts: " + insurerOwner.getExperts().size());
                System.out.println("Insurances: " + insurerOwner.getInsurersList().size());
                System.out.println(insurerOwner.getRepairServices());
                System.out.println("--------------");
                //System.out.println("Expert  "+ expertNIF.toString());
                // Test test = new Test(Long.parseLong(id),Insurer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OccurrenceType setJsonToOccurrenceType(String type) {
        switch (type) {
            case "ACCIDENT":
            case "accident":
                return OccurrenceType.ACCIDENT;
            case "THEFT":
            case "theft":
                return OccurrenceType.THEFT;
            case "NATURAL_DISASTER":
            case "natural_disaster":
                return OccurrenceType.NATURAL_DISASTER;
            default:
                return OccurrenceType.OTHERS;
        }
    }

    private InsurerType setJsonToInsurerType(String type) {
        switch (type) {
            case "AUTO":
            case "auto":
                return InsurerType.AUTO;
            case "HOUSING":
            case "housing":
                return InsurerType.HOUSING;
            case "HEALTH":
            case "health":
                return InsurerType.HEALTH;
            default:
                return InsurerType.OTHERS;
        }
    }

}
