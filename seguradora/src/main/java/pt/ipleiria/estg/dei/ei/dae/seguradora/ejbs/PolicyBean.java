package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import lombok.Getter;
import lombok.Setter;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
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
    private Map<Long, Policy> occurenceHashMap = new HashMap<>();
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
                Client client = clientBean.findOrFail(object.getString("username"));
                long policyCode = Long.parseLong(object.getString("policyCode"));
                //long InsurerOwner = Long.parseLong(object.getString("InsurerOwner"));
                //long InsuranceID = Long.parseLong(object.getString("InsuranceID"));
                long price = Long.parseLong(object.getString("price"));
                Insurance insurance = insurerBean.getbyInsuranceForPolicy(Integer.parseInt(object.getString("InsurerOwner")),Integer.parseInt(object.getString("InsuranceID")));
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
        LocalDate subscriptionEndDate = subscriptionDate.plusMonths(loyaltyPeriod);
        Boolean isExpired = subscriptionEndDate.compareTo(LocalDate.now()) < 0;
        Policy policy = new Policy(policyCode, insurance, client,price,subscriptionDate,loyaltyPeriod,coverAmount,securedGood,isExpired,subscriptionEndDate);
        System.out.println("Policy:"+policy.getClient().getFinancialNumber());
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

    public List<Policy> getPolicyByUsername(String username){
        List<Policy> aux = new ArrayList<>();
        for(Policy p :policyList){
            if(p.getClient().getUsername().equals(username)){
                aux.add(p);
            }
        }
        return aux;
    }

    public Policy getPolicyByUsernameDetail(String username, long policycode){
        List<Policy> aux = getPolicyByUsername(username);
        for (Policy policy : aux){
            if (policy.getPolicyCode() == policycode)
                return policy;
        }
        return null;
    }

    public boolean valid(long policyNumber, String username, OccurrenceType type) {
        Policy policy = policyHashMap.get(policyNumber);
        if (policy.getIsExpired())
            throw new RuntimeException("Policy is expired");
        for(OccurrenceType o:policy.getInsurance().getOccurrenceTypes()){
            if (o == type){
                return policy.getClient().getUsername().equals(username);
            }
        }
        throw new RuntimeException("This Policy doesn't insure this type of occurence");
    }

    public void addOccurence(long policyNumber, Long id) {
        occurenceHashMap.put(id,find(policyNumber));
    }

    public int getInsuranceOwnerID(long policyNumber){
        Policy policy = policyHashMap.get(policyNumber);
        return policy.getInsurance().getInsurerOwner_id();
    }
}
