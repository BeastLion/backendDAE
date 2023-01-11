package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;

import javax.persistence.Transient;
import java.util.LinkedList;

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

    public Insurance() {
        this.occurrenceTypes = new LinkedList<>();
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
}