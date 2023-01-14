package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Hasher;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ExpertBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private InsurerBean insurerBean;

    @Inject // import javax.inject.Inject;
    private Hasher hasher;

    public void create(String username, String name, String lastName, String phoneNumber, String password, String email) {
        var expert = new Expert(username, name, lastName, phoneNumber, hasher.hash(password), email);
        em.persist(expert);
    }

    public Expert find(String username) {
        var expert = em.find(Expert.class, username);
        if (expert != null)
            return expert;
        return null;
    }

}
