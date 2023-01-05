package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;

    public void create(long id, String name, String lastName, LocalDate birthDate, String address, int phoneNumber, ClientType clientType, int financialNumber, String username, String password, String email) {
        var user = new User(id, name, lastName, birthDate, address, phoneNumber, clientType, financialNumber, username, password, email);
        em.persist(user);
    }
}
