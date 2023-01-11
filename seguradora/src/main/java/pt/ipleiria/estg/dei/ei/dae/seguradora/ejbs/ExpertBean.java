package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ExpertBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String username, String name, String lastName, String phoneNumber, String password, String email) {
        var expert = new Expert(username, name, lastName, phoneNumber, password, email);
        em.persist(expert);
    }

    public void setInsurer(String username, int insurer_id) {
        var expert = find(username);
        if (expert.getInsurerOwner() == insurer_id) {
            return;
        }
        if (expert != null) {
            expert.setInsurerOwner(insurer_id);
        }
    }

    public Expert find(String username) {
        var expert = em.find(Expert.class, username);
        if (expert != null)
            return expert;
        return null;
    }
}
