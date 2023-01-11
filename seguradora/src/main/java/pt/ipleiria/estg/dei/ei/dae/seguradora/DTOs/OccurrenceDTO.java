package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;

import java.io.Serializable;
import java.time.LocalDate;

public class OccurrenceDTO implements Serializable {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String policyNumber;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private LocalDate occurrenceDate;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private OccurrenceType type;
    @Getter
    @Setter
    private OccurrenceStatus status;
    @Getter
    @Setter
    private String item;
    @Getter
    @Setter
    private String username;

    public OccurrenceDTO(){

    }

    public OccurrenceDTO(Long id, String policyNumber, String description, LocalDate occurrenceDate, String location, OccurrenceType type, String item, OccurrenceStatus status, String username) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.description = description;
        this.occurrenceDate = occurrenceDate;
        this.location = location;
        this.type = type;
        this.item = item;
        this.username = username;
        this.status = OccurrenceStatus.WAITING;
    }

    public static OccurrenceDTO toDTO(Occurrence occurrence){
        return new OccurrenceDTO(
                occurrence.getId(),
                occurrence.getPolicyNumber(),
                occurrence.getDescription(),
                occurrence.getOccurrenceDate(),
                occurrence.getLocation(),
                occurrence.getType(),
                occurrence.getItem(),
                occurrence.getStatus(),
                occurrence.getClient().getUsername()
        );
    }
}
