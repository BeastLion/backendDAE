package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceStatus;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Occurrence implements Serializable {

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    //Ensurer ensurer
    @NotNull
    private String policyNumber;
    @NotNull
    private String description;
    @NotNull
    private LocalDate occurrenceDate;
    @NotNull
    private String location;
    @NotNull
    private OccurrenceType type;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OccurrenceStatus status;

    private String item; //podiamos meter InsuredObject (ou seja mais uma class) assim podiamos ter uma lista de objetos
    //que depois tinham um ID para cada user, etc... etc... etc...

    @NotNull
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