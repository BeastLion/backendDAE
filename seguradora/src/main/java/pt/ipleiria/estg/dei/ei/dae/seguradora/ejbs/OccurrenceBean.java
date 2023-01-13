package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OccurrenceBean {
    @EJB
    private PolicyBean policyBean;

    @EJB
    private InsurerBean insurerBean;

    @PersistenceContext
    EntityManager em;

    public Long create(long policyNumber, String description, String location, OccurrenceType type, String item, String userName) throws MyEntityNotFoundException {
        Occurrence occurrence;
        var user = findOrFailUser(userName);
        System.out.println("OLA");
        if (policyBean.valid(policyNumber, userName)) {
            System.out.println("aqui");
            System.out.println("aqui");
            System.out.println("aqui");
            occurrence = new Occurrence(policyNumber,description, location, type, item);
            user.addOccurrence(occurrence);
            occurrence.addUser(user);
            em.persist(occurrence);
            System.out.println(occurrence.getId());
            policyBean.addOccurence(occurrence.getPolicyNumber(),occurrence.getId());
            return occurrence.getId();
        }
        return -1l;
    }

    public void update(Long id, String description, String location, OccurrenceType type, String item) throws MyEntityNotFoundException {
        var occurrence = findOrFailOccurrence(id);
        em.lock(occurrence, LockModeType.OPTIMISTIC); //Enquanto user estiver fazer update mais ninguem pode mexer naquela ocorrencia
        occurrence.setDescription(description);
        occurrence.setLocation(location);
        occurrence.setType(type);
        occurrence.setItem(item);
    }

    public List<Occurrence> getAll() {
        return (List<Occurrence>) em.createNamedQuery("getAllOccurences", Occurrence.class).getResultList();
    }

    public void remove(Long id) throws MyEntityNotFoundException {
        var occurrence = findOrFailOccurrence(id);
        em.remove(occurrence);
    }

    public void enrollExpertOccurrence(String username, Long id) throws MyEntityNotFoundException {
        var expert = findOrFailExpert(username);
        var occurrence = findOrFailOccurrence(id);

        /*insurece number para expert
        insure number do occurrence

        //TODO VALIDAR SE ELES TEM MESMO INSURER NUMBER
        if (expert.equals(occurrence)) {
            expert.addOccurrence(occurrence);
            occurrence.addUser(expert);
        }*/
        expert.addOccurrence(occurrence);
        occurrence.addUser(expert);
    }

    public void unrollExpertOccurrence(String username, Long id) throws MyEntityNotFoundException {
        var expert = findOrFailExpert(username);
        var occurrence = findOrFailOccurrence(id);

        /*insurece number para expert
        insure number do occurrence
        //TODO VALIDAR SE ELES TEM MESMO INSURER NUMBER
            if (expert.equals(occurrence)) {
                expert.removeOccurrence(occurrence);
                occurrence.removeUser(expert);
            }*/

        expert.removeOccurrence(occurrence);
        occurrence.removeUser(expert);
    }

    public List<Occurrence> findOccurrenceByUsername(String username) throws MyEntityNotFoundException {
        var user = findOrFailUser(username);
        TypedQuery<Occurrence> query = em.createQuery("SELECT o FROM Occurrence o WHERE o.user = :user", Occurrence.class);
        query.setParameter("user", user);
        return new ArrayList<>(query.getResultList());
    }

    public User findOrFailUser(String username) throws MyEntityNotFoundException {
        var user = em.find(User.class, username);
        if (user == null) {
            throw new MyEntityNotFoundException("Client not found with name: " + username);
        }
        Hibernate.initialize(user);
        return user;
    }

    public Expert findOrFailExpert(String username) throws MyEntityNotFoundException {
        var expert = em.find(Expert.class, username);
        if (expert == null) {
            throw new MyEntityNotFoundException("Expert not found with name: " + username);
        }
        return expert;
    }

    public Occurrence findOrFailOccurrence(Long id) throws MyEntityNotFoundException {
        var occurrence = em.find(Occurrence.class, id);
        if (occurrence == null) {
            throw new MyEntityNotFoundException("Occurrence not found with id: " + id);
        }
        Hibernate.initialize(occurrence);
        return occurrence;
    }


    public Occurrence findOrFailOccurrenceForDelete(Long id) {
        var occurrence = em.find(Occurrence.class, id);
        return occurrence;
    }
}    //return null if has been deleted that means OK or return occurrence if its not deleted