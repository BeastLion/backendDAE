package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public class InsurerOwner {
    @Transient
    @Getter
    @Setter
    private int id;
    @Transient
    @Getter
    @Setter
    private String name;

    @Transient
    @Getter
    @Setter
    private List<Insurance> insurersList;

    @Transient
    @Getter
    @Setter
    private List<Expert> experts;

    @Transient
    @Getter
    @Setter
    private List<RepairServices> repairServices;

    public InsurerOwner() {
        this.insurersList = new ArrayList<>();
        this.experts = new ArrayList<>();
        this.repairServices = new ArrayList<>();
    }

    public InsurerOwner(String name,int id) {
        this();
        this.id = id;
        this.name = name;
    }

    public void addExpert(Expert expert) {
        this.experts.add(expert);
    }

    public void removeExpert(Expert expert) {
        this.experts.remove(expert);
    }

    public void addInsurance(Insurance insurance) {
        this.insurersList.add(insurance);
    }

    public void removeInsurance(Insurance insurance) {
        this.insurersList.remove(insurance);
    }

    public void addRepairServices(RepairServices repairServices) {
        this.repairServices.add(repairServices);
    }

    public void removeRepairServices(RepairServices repairServices) {
        this.repairServices.remove(repairServices);
    }
}
