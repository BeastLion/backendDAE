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
import java.util.Objects;
@Stateless
public class OccurrenceBean {
    @PersistenceContext
    EntityManager em;

    /*
    public void create(Long id, String policyNumber, String description, LocalDate occurrenceDate, String location, OccurrenceType type, String object, OccurrenceStatus status, int nif) throws MyEntityNotFoundException {
        var client = findOrFail(nif);
        var occurrence = new Occurrence(id, policyNumber, description, occurrenceDate, location, type, object, status, client.getFinancialNumber());
        em.persist(occurrence);
    }
    */

    /*
    public void update(String username, String password, String name, String email, Long courseCode) {
        var student = findOrFail(username, false);
        em.lock(student, LockModeType.OPTIMISTIC); //enquanto nao terminar de fazer update ninguem pode fazer update
        //perguntar ao prof
        student.setPassword(password);
        student.setName(name);
        student.setEmail(email);
        if (!Objects.equals(student.getCourse().getCode(), courseCode)) {
            var course = findOrFail(courseCode);
            student.setCourse(course);
        }
    }
*/
    public List<Occurrence> getAll() {
        return (List<Occurrence>) em.createNamedQuery("getAllOccurences", Occurrence.class).getResultList();
    }
    public void remove(Long id) throws MyEntityNotFoundException {
        var occurrence = findOrFail(id);
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
    public Client findOrFail(int nif) throws MyEntityNotFoundException {
        var client = em.find(Client.class, nif);
        if(client == null){
            throw new MyEntityNotFoundException("Client not found with nif: "+ nif);
        }
        return client;
    }
    public Expert findOrFail(String username) throws MyEntityNotFoundException {
        var expert = em.find(Expert.class, username);
        if(expert == null){
            throw new MyEntityNotFoundException("Expert not found with name: "+ username);
        }
        return expert;
    }
    public Occurrence findOrFail(Long id) throws MyEntityNotFoundException {
        var occurrence = em.find(Occurrence.class, id);
        if(occurrence == null){
            throw new MyEntityNotFoundException("Occurrence not found with id: "+ id);
        }
        return occurrence;
    }
}