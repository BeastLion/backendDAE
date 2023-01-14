package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Hasher;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

@Stateless
public class ExpertBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private InsurerBean insurerBean;

    @Inject // import javax.inject.Inject;
    private Hasher hasher;

    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(s.username) FROM Expert s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long) query.getSingleResult() > 0L;
    }

    public void create(String username, String name, String lastName, String phoneNumber, String password, String email) throws MyEntityExistsException, MyEntityExistsException.MyConstraintViolationException {
        if (exists(username)) {
            throw new MyEntityExistsException("Expert with username '" + username + "' already exists");
        }
        try {
            var expert = new Expert(username, name, lastName, phoneNumber, hasher.hash(password), email);
            em.persist(expert);
        } catch (ConstraintViolationException e) {
            throw new MyEntityExistsException.MyConstraintViolationException(e);
        }
    }

    public Expert findOrFail(String username) throws MyEntityNotFoundException {
        var expert = em.getReference(Expert.class, username);
        if (expert == null) {
            throw new MyEntityNotFoundException("Expert not found with name: " + username);
        }
        Hibernate.initialize(expert);
        return expert;
    }

}
