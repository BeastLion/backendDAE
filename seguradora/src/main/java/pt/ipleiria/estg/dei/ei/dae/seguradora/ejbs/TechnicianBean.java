package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TechnicianBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String username, String name, String lastName, String phoneNumber, String password, String email) {
        var technician = new Technician(username, name, lastName, phoneNumber, password, email);
        em.persist(technician);
    }

    public void setRepairService(String username, long repairService_id) {
        var technician = find(username);
        if (technician.getRepairServices() == repairService_id) {
            return;
        }
        if (technician != null) {
            technician.setRepairServices(repairService_id);
        }
    }

    public Technician find(String username) {
        var technician = em.find(Technician.class, username);
        if (technician != null)
            return technician;
        return null;
    }
}