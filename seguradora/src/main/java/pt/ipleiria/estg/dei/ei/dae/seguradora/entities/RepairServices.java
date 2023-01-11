package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class RepairServices {

    @Transient
    @NotNull
    @Getter
    @Setter
    private Long id;
    @Transient
    @NotNull
    @Getter
    @Setter
    private String name;
    @Transient
    @NotNull
    @Getter
    @Setter
    private String location;
    @Transient
    @NotNull
    @Getter
    @Setter
    private InsuranceType insuranceType;
    @Transient
    @Getter
    @Setter
    private List<Technician> technicians;

    public RepairServices() {
        this.technicians = new ArrayList<>();
    }

    public RepairServices(Long id, String name, String location, InsuranceType insuranceType) {
        this();
        this.id = id;
        this.name = name;
        this.location = location;
        this.insuranceType = insuranceType;
    }

    public void addRepairTechnician(Technician technician) {
        this.technicians.add(technician);
    }

    public void removeRepairTechnician(Technician technician) {
        this.technicians.remove(technician);
    }
}
