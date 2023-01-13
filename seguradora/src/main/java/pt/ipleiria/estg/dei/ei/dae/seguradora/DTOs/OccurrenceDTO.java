package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceDTO implements Serializable {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private long policyNumber;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String occurrenceDate;
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

    public OccurrenceDTO(){

    }

    public OccurrenceDTO(Long id, long policyNumber, String description, String occurrenceDate, String location, OccurrenceType type, String item, OccurrenceStatus status) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.description = description;
        this.occurrenceDate = occurrenceDate;
        this.location = location;
        this.type = type;
        this.item = item;
        this.status = OccurrenceStatus.WAITING;
    }

    public static OccurrenceDTO toDTO(Occurrence occurrence){
        return new OccurrenceDTO(
                occurrence.getId(),
                occurrence.getPolicyNumber(),
                occurrence.getDescription(),
                occurrence.getOccurrenceDate().toString(),
                occurrence.getLocation(),
                occurrence.getType(),
                occurrence.getItem(),
                occurrence.getStatus()
        );
    }

    public static List<OccurrenceDTO> toDTOs(List<Occurrence> occurrences) {
        return occurrences.stream().map(OccurrenceDTO::toDTO).collect(Collectors.toList());
    }
}
