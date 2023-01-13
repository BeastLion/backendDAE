package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
@Getter
@Setter
public class Insurance {
    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private int insurerOwner_id;
    @NotNull
    private String insurerOwner_name;
    @NotNull
    private InsuranceType insuranceType;
    private LinkedList<OccurrenceType> occurrenceTypes;

    public Insurance() {
        this.occurrenceTypes = new LinkedList<>();
    }

    public Insurance(int id, String name, InsuranceType insuranceType,int insurerOwner_id,String insurerOwner_name) {
        this();
        this.id = id;
        this.name = name;
        this.insuranceType = insuranceType;
        this.insurerOwner_id = insurerOwner_id;
        this.insurerOwner_name = insurerOwner_name;
    }

    public void addOccurrenceType(OccurrenceType occurrenceType) {
        this.occurrenceTypes.add(occurrenceType);
    }

    public void removeOccurrenceType(OccurrenceType occurrenceType) {
        this.occurrenceTypes.remove(occurrenceType);
    }

}