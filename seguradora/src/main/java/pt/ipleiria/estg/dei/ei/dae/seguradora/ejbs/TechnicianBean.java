package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

@Stateless
public class TechnicianBean {
    @PersistenceContext
    private EntityManager em;

    @Inject // import javax.inject.Inject;
    private Hasher hasher;

    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(s.username) FROM Technician s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long) query.getSingleResult() > 0L;
    }

    public void create(String username, String name, String lastName, String phoneNumber, String password, String email) throws MyEntityExistsException, MyEntityExistsException.MyConstraintViolationException {
        if (exists(username)) {
            throw new MyEntityExistsException("Technician with username '" + username + "' already exists");
        }
        try {
            var technician = new Technician(username, name, lastName, phoneNumber, hasher.hash(password), email);
            em.persist(technician);
        } catch (ConstraintViolationException e) {
            throw new MyEntityExistsException.MyConstraintViolationException(e);
        }
    }

    public Technician findOrFail(String username) throws MyEntityNotFoundException {
        var technician = em.find(Technician.class, username);
        if (technician == null) {
            throw new MyEntityNotFoundException("Technician not found with name: " + username);
        }
        return technician;
    }

    public Technician find(String username) {
        var technician = em.find(Technician.class, username);
        if (technician == null) {
            return null;
        }
        return technician;
    }
}
