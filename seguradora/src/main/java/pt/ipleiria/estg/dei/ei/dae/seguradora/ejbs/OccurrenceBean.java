package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class OccurrenceBean {
    @PersistenceContext
    EntityManager em;

    public void create(Long id, String policyNumber, String description, LocalDate occurrenceDate, String location, OccurrenceType type, String item, OccurrenceStatus status, String userName) throws MyEntityNotFoundException {
        var client = findOrFailClient(userName);
        var occurrence = new Occurrence(id, policyNumber, description, occurrenceDate, location, type, item, status, client);
        em.persist(occurrence);
    }

    public void update(Long id, String description, String location, OccurrenceType type, String item, String userName) throws MyEntityNotFoundException {
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
    public Client findOrFailClient(String username) throws MyEntityNotFoundException {
        var client = em.find(Client.class, username);
        if (client == null) {
            throw new MyEntityNotFoundException("Client not found with name: " + username);
        }
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
        return occurrence;
    }
}