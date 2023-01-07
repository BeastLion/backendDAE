package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Stateless
public class ClientBean {
    @PersistenceContext
    EntityManager em;

    @Inject // import javax.inject.Inject;
    private Hasher hasher;

    public void create(String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,String username, String password, String email) {
        ClientType clientType = whatTypeOfClient(financialNumber);
        Client client = new Client(name,lastName,birthDate,address,phoneNumber,financialNumber,username, hasher.hash(password),email,clientType);
        em.persist(client);
    }

    //Verificar se o cliente é empresarial ou singular
    public ClientType whatTypeOfClient(int financialNumber){
        String financialNumberString = Integer.toString(financialNumber);  // Convert to string
        char firstDigit = financialNumberString.charAt(0);  // Extract first character

        switch (firstDigit) {
            case '1':
            case '2':
            case '3':
                return ClientType.CLIENT;
            case '5':
            case '6':
            case '8':
            case '9':
                return ClientType.ORGANIZATION;
            default:
                return null;
        }
    }

}
