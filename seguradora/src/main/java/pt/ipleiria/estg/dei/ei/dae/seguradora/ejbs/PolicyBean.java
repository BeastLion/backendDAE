package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import lombok.Getter;
import lombok.Setter;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class PolicyBean {
    @EJB
    private LoremBean loremBean;
    @EJB
    private InsurerBean insurerBean;
    @EJB
    private ClientBean clientBean;

    @Getter
    @Setter
    private List<Policy> policyList;

    private Map<Long, Policy> policyHashMap = new HashMap<>();
    public void getAll() {
        policyList = new ArrayList<>();
        try {
            // Connect to the API and retrieve the JSON data
            StringBuilder response = loremBean.connect("policies");

            // Parse the JSON data and create a list of Insure objects
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonArray array = jsonReader.readArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject object = array.getJsonObject(i);
                long policyCode = Long.parseLong(object.getString("policyCode"));
                Insurance insurance = insurerBean.getbyInsuranceForPolicy(Integer.parseInt(object.getString("InsurerOwner")),Integer.parseInt(object.getString("InsuranceID")));
                Client client = clientBean.findByNif(Integer.parseInt(object.getString("financialNumber")));
                long price = Long.parseLong(object.getString("price"));
                LocalDate subscriptionDate = LocalDate.parse(object.getString("subscriptionDate"));
                long loyaltyPeriod = Long.parseLong(object.getString("loyaltyPeriod"));
                long coverAmount = Long.parseLong(object.getString("coverAmount"));
                String securedGood = object.getString("securedGood");

                create(policyCode,insurance,client,price,subscriptionDate,loyaltyPeriod,coverAmount,securedGood);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Polices List: "+ policyList.size());
        initializeHashMap();
    }

    public void create(long policyCode, Insurance insurance, Client client, long price, LocalDate subscriptionDate, long loyaltyPeriod, long coverAmount, String securedGood){
        Policy policy = new Policy(policyCode,insurance,client,price,subscriptionDate,loyaltyPeriod,coverAmount,securedGood);
        policy.getInsurance().addPolicy(policy);
        policy.getClient().addPolicy(policy);
        policyList.add(policy);
    }

    public void initializeHashMap() {
        // Initialize the HashMap with the Policy objects from the policyList
        for (Policy i : policyList) {
            policyHashMap.put(i.getPolicyCode(), i);
        }
    }

    public Policy find(long policyCode) {
        // Look up the Policy object with the given code in the HashMap
        return policyHashMap.get(policyCode);
    }
}
