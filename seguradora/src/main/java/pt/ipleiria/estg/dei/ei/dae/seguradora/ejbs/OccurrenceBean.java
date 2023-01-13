package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OccurrenceBean {
    @EJB
    private PolicyBean policyBean;
    @PersistenceContext
    EntityManager em;

    public Occurrence create(long policyNumber, String description, String location, OccurrenceType type, String item, String userName) throws MyEntityNotFoundException {
        var policy = policyBean.find(policyNumber);
        var client = findOrFailClient(userName);
        var occurrence = new Occurrence(policy, description, location, type, item);
        client.addOccurrence(occurrence);
        occurrence.addUser(client);
        em.persist(occurrence);
        return occurrence;
    }

    public void update(Long id, String description, String location, OccurrenceType type, String item, List<User> users) throws MyEntityNotFoundException {
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

    /*
    public void enrollExpertOccurrence(String username, Long id) throws MyEntityNotFoundException {
        var expert = findOrFail(username);
        var occurrence = findOrFail(id);
        //var expertInsurer = expert.getInsurer();
        //var occurrenceInsurer = occurrence.getInsurer();
        if (expertInsurer.equals(occurrenceInsurer)) {
            student.addSubject(subject);
            subject.addStudent(student);
        }
    }

    public void unrollExpertOccurrence(String username, Long code) {
        var expert = findOrFail(username);
        var occurrence = findOrFail(id);
        //var expertInsurer = expert.getInsurer();
        //var occurrenceInsurer = occurrence.getInsurer();
        if (expertInsurer.equals(occurrenceInsurer)) {
            student.addSubject(subject);
            subject.addStudent(student);
        }
    }
*/
    public List<Occurrence> findOccurrenceByUsername(String username) throws MyEntityNotFoundException {
        var user = findOrFailClient(username);
        TypedQuery<Occurrence> query = em.createQuery("SELECT o FROM Occurrence o WHERE o.user = :user", Occurrence.class);
        query.setParameter("user", user);
        return new ArrayList<>(query.getResultList());
    }

    public Client findOrFailClient(String username) throws MyEntityNotFoundException {
        var client = em.find(Client.class, username);
        if (client == null) {
            throw new MyEntityNotFoundException("Client not found with name: " + username);
        }
        Hibernate.initialize(client);
        return client;
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


    public Occurrence findOrFailOccurrenceForDelete(Long id){
        var occurrence = em.find(Occurrence.class, id);
        return occurrence;
    }
}    //return null if has been deleted that means OK or return occurrence if its not deleted