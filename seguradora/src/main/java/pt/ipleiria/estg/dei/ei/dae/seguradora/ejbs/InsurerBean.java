package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

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
    @EJB
    private TechnicianBean technicianBean;

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
                JsonObject objectOwner = array.getJsonObject(i);
                String insuranceOwner_id = objectOwner.getString("id");
                String insuranceOwner_name = objectOwner.getString("Insurer");

                JsonArray insuranceOwner_Experts = objectOwner.getJsonArray("Expert");
                List<Expert> experts = new ArrayList<>();
                for (int j = 0; j < insuranceOwner_Experts.size(); j++) {
                    JsonObject objectExperts = insuranceOwner_Experts.getJsonObject(j);
                    String expert_username = objectExperts.getString("username");
                    expertBean.setInsurer(expert_username, Integer.parseInt(insuranceOwner_id));
                    experts.add(expertBean.find(expert_username));
                }

                JsonArray insuranceOwner_insurances = objectOwner.getJsonArray("Insurances");
                List<Insurance> insurances = new ArrayList<>();
                for (int k = 0; k < insuranceOwner_insurances.size(); k++) {
                    JsonObject objectInsurances = insuranceOwner_insurances.getJsonObject(k);
                    String insurace_id = objectInsurances.getString("id");
                    String insurace_name = objectInsurances.getString("policyName");
                    InsuranceType insrance_type = setJsonToInsurerType(objectInsurances.getString("type"));

                    JsonArray cover = objectInsurances.getJsonArray("covers");
                    List<OccurrenceType> occurrences = new ArrayList<>();
                    for (int l = 0; l < cover.size(); l++) {
                        occurrences.add(setJsonToOccurrenceType(cover.getString(l)));
                    }

                    Insurance insurance = new Insurance(Integer.parseInt(insurace_id), insurace_name, insrance_type);
                    for (OccurrenceType o : occurrences) {
                        insurance.addOccurrenceType(o);
                    }
                    insurances.add(insurance);
                }

                JsonArray services = objectOwner.getJsonArray("Services");
                List<RepairServices> servicesArray = new ArrayList<>();
                for (int n = 0; n < services.size(); n++) {
                    JsonObject objectServices = services.getJsonObject(n);
                    String service_id = objectServices.getString("Id");
                    String service_name = objectServices.getString("name");
                    String service_location = objectServices.getString("location");
                    InsuranceType service_insurertype = setJsonToInsurerType(objectServices.getString("specialty"));

                    JsonArray service_technicians = objectServices.getJsonArray("technician");
                    List<Technician> technicians = new ArrayList<>();
                    for (int m = 0; m < service_technicians.size(); m++) {
                        JsonObject objectTechnician = service_technicians.getJsonObject(m);
                        String technician_username = objectTechnician.getString("username");
                        technicianBean.setRepairService(technician_username, Integer.parseInt(service_id));
                        technicians.add(technicianBean.find(technician_username));
                    }

                    RepairServices repairServices = new RepairServices(Long.parseLong(service_id), service_name, service_location, service_insurertype);
                    for (Technician t : technicians) {
                        repairServices.addRepairTechnician(t);
                    }
                    servicesArray.add(repairServices);
                }

                //Creation of insurerOwner and set insurerOwner on insurance class
                InsurerOwner insurerOwner = new InsurerOwner(insuranceOwner_name, Integer.parseInt(insuranceOwner_id));
                for (Expert e : experts) {
                    insurerOwner.addExpert(e);
                }
                for (Insurance insurance : insurances) {
                    insurerOwner.addInsurance(insurance);
                    insurance.setOwner(insurerOwner);
                }
                for (RepairServices s : servicesArray) {
                    insurerOwner.addRepairServices(s);
                }
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

    private InsuranceType setJsonToInsurerType(String type) {
        switch (type) {
            case "AUTO":
            case "auto":
                return InsuranceType.AUTO;
            case "HOUSING":
            case "housing":
                return InsuranceType.HOUSING;
            case "HEALTH":
            case "health":
                return InsuranceType.HEALTH;
            default:
                return InsuranceType.OTHERS;
        }
    }

}
