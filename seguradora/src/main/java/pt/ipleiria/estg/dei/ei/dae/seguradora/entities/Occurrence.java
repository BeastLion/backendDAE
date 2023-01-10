package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Occurence")
public class Occurrence implements Serializable {

    @Id
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
    private String object; //podiamos meter InsuredObject (ou seja mais uma class) assim podiamos ter uma lista de objetos
    //que depois tinham um ID para cada user, etc... etc... etc...

    /*
    @Getter
    @Setter
    @ManyToOne(mappedBy = "occurrences")
    private Client client; //cada occurrence tem um user !!
    */

    public Occurrence(Long id, String policyNumber, String description, LocalDate occurrenceDate, String location, OccurrenceType type, String object) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.description = description;
        this.occurrenceDate = occurrenceDate;
        this.location = location;
        this.type = type;
        this.object = object;
        //this.user = user;
    }
}
