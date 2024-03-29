package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateless
public class OccurrenceBean {
    @EJB
    private PolicyBean policyBean;

    @EJB
    private InsurerBean insurerBean;

    @EJB
    private ExpertBean expertBean;

    @EJB
    private UserBean userBean;

    @EJB
    private TechnicianBean technicianBean;

    @PersistenceContext
    EntityManager em;

    public Long create(long policyNumber, String description, String location, OccurrenceType type, String item, String userName) throws MyEntityNotFoundException, MyEntityExistsException.MyConstraintViolationException {
        Occurrence occurrence;
        var user = userBean.findOrFail(userName);
        try {
            if (policyBean.valid(policyNumber, userName,type)) {
                occurrence = new Occurrence(policyNumber, description, location, type, item);
                user.addOccurrence(occurrence);
                occurrence.addUser(user);
                em.persist(occurrence);
                policyBean.addOccurence(occurrence.getPolicyNumber(), occurrence.getId());
                return occurrence.getId();
            }
        } catch (ConstraintViolationException e) {
            throw new MyEntityExistsException.MyConstraintViolationException(e);
        }
        return -1l;
    }

    public void update(Long id, String description, String location, OccurrenceType type, String item) throws MyEntityNotFoundException {
        var occurrence = findOrFailOccurrence(id);
        if (occurrence.getStatus() == OccurrenceStatus.WAITING) {
            em.lock(occurrence, LockModeType.OPTIMISTIC); //Enquanto user estiver fazer update mais ninguem pode mexer naquela ocorrencia
            occurrence.setDescription(description);
            occurrence.setLocation(location);
            occurrence.setType(type);
            occurrence.setItem(item);
        }
    }

    public List<Occurrence> getAll() {
        return (List<Occurrence>) em.createNamedQuery("getAllOccurences", Occurrence.class).getResultList();
    }

    public void remove(Long id) throws MyEntityNotFoundException {
        var occurrence = findOrFailOccurrence(id);
        if (occurrence.getStatus() == OccurrenceStatus.WAITING) {
            occurrence.setIsDeleted(true);
            occurrence.setStatus(OccurrenceStatus.CANCELED);
        }
    }

    public void enrollTechinicianOccurrence(String username, Occurrence occurrence) throws MyEntityNotFoundException {
        var technician = technicianBean.findOrFail(username);
        technician.addOccurrence(occurrence);
        occurrence.addUser(technician);
        occurrence.setHasTechnician(true);

    }

    public void enrollExpertOccurrence(String username, Long id) throws MyEntityNotFoundException {
        var expert = expertBean.findOrFail(username);
        var occurrence = findOrFailOccurrence(id);

        if (occurrence.getHasExpert()){
            throw new RuntimeException("This occurrence has already an expert");
        }

        expert.addOccurrence(occurrence);
        occurrence.addUser(expert);
        occurrence.setHasExpert(true);
    }

    public void unrollExpertOccurrence(String username, Long id) throws MyEntityNotFoundException {
        var expert = expertBean.findOrFail(username);
        var occurrence = findOrFailOccurrence(id);

        if (!occurrence.getHasExpert()){
            throw new RuntimeException("This occurrence doesn't have an expert");
        }

        boolean aux = false;
        for(Occurrence o :expert.getOccurrences()){
            if (o.getId().equals(id)){
                aux = true;
                expert.removeOccurrence(occurrence);
                occurrence.removeUser(expert);
                occurrence.setHasExpert(false);
            }
        }
        if (aux == false) {
            throw new RuntimeException("This Expert can't remove other expert");
        }
    }

    public Occurrence findOrFailOccurrence(Long id) throws MyEntityNotFoundException {
        var occurrence = em.find(Occurrence.class, id);
        if (occurrence == null || occurrence.getIsDeleted()) {
            throw new MyEntityNotFoundException("Occurrence not found with id: " + id);
        }
        Hibernate.initialize(occurrence);
        return occurrence;
    }

    public Boolean findOccurrenceisDeleted(Long id) throws MyEntityNotFoundException {
        var occurrence = em.find(Occurrence.class, id);
        if (occurrence == null)
            throw new MyEntityNotFoundException("Occurrence not found with id: " + id);
        return occurrence.getIsDeleted();
    }

    public List<Occurrence> findAvailableForExpert(String username) throws MyEntityNotFoundException {
        //get id from expert get the ensurer where he works
        int insurerOwnerID = insurerBean.getIdfromExpert(username);
        List<Occurrence> all = getAll();
        List<Occurrence> available = new ArrayList<>();
        for (Occurrence o : all) {
            if (!o.getIsDeleted() && !o.getHasExpert()) {
                if (o.getStatus() == OccurrenceStatus.WAITING) {
                    if (insurerOwnerID == policyBean.getInsuranceOwnerID(o.getPolicyNumber())) {
                        available.add(o);
                    }
                }
            }
        }
        return available;
    }

    public boolean changeStatus(Long id, String username) throws MyEntityNotFoundException {
        var occurrence = findOrFailOccurrence(id);
        var user = userBean.findOrFail(username);

        if (Objects.equals(user.getUserType(), "Expert")) {
            if (occurrence.getStatus() == OccurrenceStatus.WAITING) {
                em.lock(occurrence, LockModeType.OPTIMISTIC);
                occurrence.setStatus(OccurrenceStatus.WAITINGFORCLIENT);
                return true;
            }
        }

        if (Objects.equals(user.getUserType(), "Technician")) {
            if (occurrence.getStatus() == OccurrenceStatus.WAITINGFORDONE) {
                em.lock(occurrence, LockModeType.OPTIMISTIC);
                occurrence.setStatus(OccurrenceStatus.DONE);
                return true;
            }
        }
        return false;
    }
    public boolean assignTechnician(Long id, String username,Long idRepairServices) throws MyEntityNotFoundException {
        var occurrence = findOrFailOccurrence(id);
        if (occurrence.getHasTechnician()){
            throw new RuntimeException("This occurrence has already an technician");
        }
        var user = userBean.findOrFail(username);
        if (Objects.equals(user.getUserType(), "Client")) {
            if (occurrence.getStatus() == OccurrenceStatus.WAITINGFORCLIENT) {
                em.lock(occurrence, LockModeType.OPTIMISTIC);
                System.out.println(insurerBean.getRepaiServicesById(idRepairServices).getTechnician().getUsername());
                enrollTechinicianOccurrence(insurerBean.getRepaiServicesById(idRepairServices).getTechnician().getUsername(),occurrence);
                occurrence.setStatus(OccurrenceStatus.WAITINGFORDONE);
                return true;
            }
        }
        return false;
    }
}