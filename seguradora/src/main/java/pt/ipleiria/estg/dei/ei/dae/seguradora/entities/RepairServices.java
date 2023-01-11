package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsurerType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class RepairServices {

    @NotNull
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @NotNull
    @Getter
    @Setter
    private String location;

    @NotNull
    @Getter
    @Setter
    private InsurerType insurerType;

    @Getter
    @Setter
    private List<Technician> technicians;

    public RepairServices() {
        this.technicians = new ArrayList<>();
    }

    public RepairServices(Long id, String name, String location, InsurerType insurerType) {
        this();
        this.id = id;
        this.name = name;
        this.location = location;
        this.insurerType = insurerType;

    }

    public void addRepairTechnician(Technician technician) {
        this.technicians.add(technician);
    }

    public void removeRepairTechnician(Technician technician) {
        this.technicians.remove(technician);
    }
}
