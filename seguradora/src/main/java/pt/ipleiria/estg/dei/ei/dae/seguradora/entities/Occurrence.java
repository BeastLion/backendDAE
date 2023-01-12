package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
@Entity

public class Occurrence implements Serializable {

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Getter
    @Setter
    private Long id;
    //Ensurer ensurer
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
    @NotNull
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private OccurrenceStatus status;
    @Getter
    @Setter
    private String item; //podiamos meter InsuredObject (ou seja mais uma class) assim podiamos ter uma lista de objetos
    //que depois tinham um ID para cada user, etc... etc... etc...

    @NotNull
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "username")
    private User user; //cada occurrence tem um user !!

    @Version
    private int version;

    public Occurrence() {
    }

    public Occurrence(Long id, String policyNumber, String description, LocalDate occurrenceDate, String location, OccurrenceType type, String item, OccurrenceStatus status, User user) {
        this();
        this.id = id;
        this.policyNumber = policyNumber;
        this.description = description;
        this.occurrenceDate = occurrenceDate;
        this.location = location;
        this.type = type;
        this.item = item;
        this.user = user;
        this.status = OccurrenceStatus.WAITING;
    }
}