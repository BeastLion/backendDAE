package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;

import javax.ejb.Stateless;

@Stateless
public class RepairServicesBean {
    public RepairServices create(Long id, String name, String location, InsuranceType insuranceType){
        var services = new RepairServices(id,name,location,insuranceType);
        return services;
    }
}
