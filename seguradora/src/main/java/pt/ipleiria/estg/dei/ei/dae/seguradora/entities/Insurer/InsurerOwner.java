package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InsurerOwner {
    @NotNull
    private int id;
    @NotNull
    private String name;
    private List<Insurance> insuranceList;
    private List<Expert> experts;
    private List<RepairServices> repairServices;

    public InsurerOwner() {
        this.insuranceList = new ArrayList<>();
        this.experts = new ArrayList<>();
        this.repairServices = new ArrayList<>();
    }

    public InsurerOwner(String name, int id) {
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
        this.insuranceList.add(insurance);
    }

    public void removeInsurance(Insurance insurance) {
        this.insuranceList.remove(insurance);
    }

    public void addRepairServices(RepairServices repairServices) {
        this.repairServices.add(repairServices);
    }

    public void removeRepairServices(RepairServices repairServices) {
        this.repairServices.remove(repairServices);
    }
}
