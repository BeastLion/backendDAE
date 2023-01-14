package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class RepairServicesBean {
    @EJB
    private TechnicianBean technicianBean;

    public RepairServices create(Long id, String name, String location, InsuranceType insuranceType,String username) {
        var technician = technicianBean.find(username);
        var services = new RepairServices(id,name,location,insuranceType,technician);
        return services;
    }


}
