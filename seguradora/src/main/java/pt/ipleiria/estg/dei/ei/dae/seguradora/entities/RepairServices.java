package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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
