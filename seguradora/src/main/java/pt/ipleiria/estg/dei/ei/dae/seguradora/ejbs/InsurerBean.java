package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import lombok.Getter;
import lombok.Setter;
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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class InsurerBean {
    @EJB
    private LoremBean loremBean;
    @EJB
    private ExpertBean expertBean;
    @EJB
    private TechnicianBean technicianBean;
    @EJB
    private RepairServicesBean repairServicesBean;

    @Getter
    @Setter
    private List<InsurerOwner> insurerOwnerList;

    private Map<Integer, InsurerOwner> insurerOwnerHashMap = new HashMap<>();
    private Map<String, Insurance> insuranceHashMap = new HashMap<>();
    public void getAll() {
        insurerOwnerList = new ArrayList<>();
        try {
            // Connect to the API and retrieve the JSON data
            StringBuilder response = loremBean.connect("insurers");

            // Parse the JSON data and create a list of Insure objects
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonArray array = jsonReader.readArray();

            for (int i = 0; i < array.size(); i++) {
                JsonObject objectOwner = array.getJsonObject(i);
                int insuranceOwner_id = Integer.parseInt(objectOwner.getString("id"));
                String insuranceOwner_name = objectOwner.getString("Insurer");

                JsonArray insuranceOwner_Experts = objectOwner.getJsonArray("Expert");
                List<Expert> expertList = new ArrayList<>();
                for (int j = 0; j < insuranceOwner_Experts.size(); j++) {
                    JsonObject objectExperts = insuranceOwner_Experts.getJsonObject(j);
                    expertList.add(expertBean.find(objectExperts.getString("username")));
                }

                JsonArray insuranceOwner_insurances = objectOwner.getJsonArray("Insurances");
                List<Insurance> insuranceList = new ArrayList<>();
                for (int k = 0; k < insuranceOwner_insurances.size(); k++) {
                    JsonObject objectInsurances = insuranceOwner_insurances.getJsonObject(k);
                    int insurace_id = Integer.parseInt(objectInsurances.getString("id"));
                    String insurace_name = objectInsurances.getString("policyName");
                    InsuranceType insrance_type = setJsonToInsurerType(objectInsurances.getString("type"));

                    JsonArray cover = objectInsurances.getJsonArray("covers");
                    List<OccurrenceType> occurrenceTypeList = new ArrayList<>();
                    for (int l = 0; l < cover.size(); l++) {
                        occurrenceTypeList.add(setJsonToOccurrenceType(cover.getString(l)));
                    }

                    Insurance insurance = createInsurance(insurace_id, insurace_name, insrance_type);
                    for (OccurrenceType o : occurrenceTypeList) {
                        insurance.addOccurrenceType(o);
                    }
                    insuranceList.add(insurance);
                }

                JsonArray services = objectOwner.getJsonArray("Services");
                List<RepairServices> repairServicesList = new ArrayList<>();
                for (int n = 0; n < services.size(); n++) {
                    JsonObject objectServices = services.getJsonObject(n);
                    long service_id = Long.parseLong(objectServices.getString("Id"));
                    String service_name = objectServices.getString("name");
                    String service_location = objectServices.getString("location");
                    InsuranceType service_insurertype = setJsonToInsurerType(objectServices.getString("specialty"));

                    JsonArray service_technicians = objectServices.getJsonArray("technician");
                    List<Technician> technicianList = new ArrayList<>();
                    for (int m = 0; m < service_technicians.size(); m++) {
                        JsonObject objectTechnician = service_technicians.getJsonObject(m);
                        technicianList.add(technicianBean.find(objectTechnician.getString("username")));
                    }

                    RepairServices repairServices = repairServicesBean.create(service_id, service_name, service_location, service_insurertype);
                    for (Technician t : technicianList) {
                        repairServices.addRepairTechnician(t);
                        t.setRepairServices(repairServices);
                    }
                    repairServicesList.add(repairServices);
                }

                //Creation of insurerOwner
                InsurerOwner insurerOwner = createInsurerOwner(insuranceOwner_name, insuranceOwner_id);
                for (Expert e : expertList) {
                    insurerOwner.addExpert(e);
                    e.setInsurerOwner(insurerOwner);
                }
                for (Insurance insurance : insuranceList) {
                    insurerOwner.addInsurance(insurance);
                    insurance.setOwner(insurerOwner);
                }
                for (RepairServices s : repairServicesList) {
                    insurerOwner.addRepairServices(s);
                }
                insurerOwnerList.add(insurerOwner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("insurerOwners: " + insurerOwnerList.size());
        initializeHashMap();
    }

    public InsurerOwner createInsurerOwner(String name, int id) {
        var insurerOwner = new InsurerOwner(name, id);
        return insurerOwner;
    }

    public Insurance createInsurance(int id, String name, InsuranceType insuranceType) {
        var insurance = new Insurance(id, name,insuranceType);
        return insurance;
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

    public void initializeHashMap() {
        for (InsurerOwner i : insurerOwnerList) {
            insurerOwnerHashMap.put(i.getId(), i);
            for (Insurance j : i.getInsuranceList()) {
                String key = i.getId() + "-" + j.getId();
                insuranceHashMap.put(key, j);
            }
        }
    }
    public Insurance getbyInsuranceForPolicy(int insurerOwner, int insuranceID) {
        InsurerOwner insurerOwnerAUX = insurerOwnerHashMap.get(insurerOwner);
        if (insurerOwnerAUX == null) {
            return null;
        }
        String insuranceKey = insurerOwner + "-" + insuranceID;
        return insuranceHashMap.get(insuranceKey);
    }
}
