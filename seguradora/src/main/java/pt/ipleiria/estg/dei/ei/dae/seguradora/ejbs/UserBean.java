package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public User find(String username) {
        System.out.println("----------------------------------");
        System.out.println("--------------AQUIBEANFIND----------------");
        System.out.println(em.find(User.class, username));
        System.out.println("----------------------------------");
        return em.find(User.class, username);
    }

    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        System.out.println("----------------------------------");
        System.out.println("--------------AQUIBEAN----------------");
        System.out.println("----------------------------------");
        var user = find(username);
        System.out.println("----------------------------------");
        System.out.println("--------------AQUIBEAN2----------------");
        System.out.println("----------------------------------");
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        var user = find(username);
        if (!user.getPassword().equals(hasher.hash(oldPassword)))
            return false;

        user.setPassword(hasher.hash(newPassword));
        return true;
    }

}
