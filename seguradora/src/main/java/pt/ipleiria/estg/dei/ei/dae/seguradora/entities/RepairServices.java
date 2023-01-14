package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RepairServices {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private InsuranceType insuranceType;
    private Technician technician;

    public RepairServices() {

    }

    public RepairServices(Long id, String name, String location, InsuranceType insuranceType, Technician technician) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.insuranceType = insuranceType;
        this.technician = technician;
    }
}
