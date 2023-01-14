package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Hasher;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;


    public User find(String username) {
        return em.find(User.class, username);
    }

    public User findOrFail(String username) throws MyEntityNotFoundException {
        var user = em.getReference(User.class, username);
        if (user == null) {
            throw new MyEntityNotFoundException("User not found with name: " + username);
        }
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        var user = find(username);
        if (!user.getPassword().equals(hasher.hash(oldPassword)))
            return false;

        user.setPassword(hasher.hash(newPassword));
        return true;
    }

    public List<Occurrence> getOcccurrenceByUser(String username) throws MyEntityNotFoundException {
        var user = findOrFail(username);
        List<Occurrence> occurrenceList = user.getOccurrences();
        for(Occurrence o :user.getOccurrences()){
            if (o.getIsDeleted()){
                occurrenceList.remove(o);
            }
        }
        return occurrenceList;
    }
}
