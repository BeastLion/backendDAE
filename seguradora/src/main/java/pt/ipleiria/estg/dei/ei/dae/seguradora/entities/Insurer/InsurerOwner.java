package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Expert;

import javax.persistence.Transient;
import java.util.LinkedList;

public class InsurerOwner {
    @Transient
    @Getter
    @Setter
    private String name;

    @Transient
    @Getter
    @Setter
    private LinkedList<Insurer> insurersList;

    @Transient
    @Getter
    @Setter
    private LinkedList<Expert> experts;

    @Transient
    @Getter
    @Setter
    private LinkedList<RepairServices> repairServicesAuto;


    public InsurerOwner() {
        this.insurersList = new LinkedList<>();
        this.experts = new LinkedList<>();
    }

    public InsurerOwner(String name) {
        this();
        this.name = name;
    }
}
