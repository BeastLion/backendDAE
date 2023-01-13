package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class InsuranceDTO {

    private int id;

    private String name;

    private InsurerOwner owner;

    private InsuranceType insuranceType;
    private LinkedList<OccurrenceType> occurrenceTypes;

    private List<Policy> policies;

    public InsuranceDTO() {
        this.occurrenceTypes = new LinkedList<>();
        this.policies = new LinkedList<>();
    }

    public InsuranceDTO(int id, String name, InsuranceType insuranceType) {
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

    public static InsuranceDTO toDTO(InsuranceDTO insuranceDTO) {
        return new InsuranceDTO(
                insuranceDTO.getId(),
                insuranceDTO.getName(),
                insuranceDTO.getInsuranceType()
        );
    }
}

