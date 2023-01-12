package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;

import javax.persistence.Transient;
import java.util.LinkedList;
import java.util.List;

public class Insurance {

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
    private InsurerOwner owner;

    @Transient
    @Getter
    @Setter
    private LinkedList<OccurrenceType> occurrenceTypes;

    @Transient
    @Getter
    @Setter
    private InsuranceType insuranceType;

    @Transient
    @Getter
    @Setter
    private List<Policy> policies;

    public Insurance() {
        this.occurrenceTypes = new LinkedList<>();
        this.policies = new LinkedList<>();
    }

    public Insurance(int id, String name, InsuranceType insuranceType) {
        this();
        this.id = id;
        this.name = name;
        this.insuranceType = insuranceType;
        this.owner = null;
    }

    public void addOccurrenceType(OccurrenceType occurrenceType) {
        this.occurrenceTypes.add(occurrenceType);
    }

    public void removeOccurrenceType(OccurrenceType occurrenceType) {
        this.occurrenceTypes.remove(occurrenceType);
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        this.policies.remove(policy);
    }
}