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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurences",
                query = "SELECT s FROM Occurrence s where s.isDeleted <> true ORDER BY s.id" // JPQL
        )
})
public class Occurrence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long policyNumber;
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
    private String item;
    @NotNull
    @OneToMany(mappedBy = "occurrence")
    private List<Document> documents;

    private Boolean isDeleted;

    private Boolean hasExpert;

    private Boolean hasTechnician;

    @NotNull
    @ManyToMany
    @JoinTable(name = "userOccurrences",
            joinColumns = @JoinColumn(name = "occurrencesId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userUsername", referencedColumnName = "username"))
    private List<User> users; //cada occurrence tem um user !!

    @Version
    private int version;

    public Occurrence() {
        this.users = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public Occurrence(Long policyNumber,String description, String location, OccurrenceType type, String item) {
        this();
        this.policyNumber = policyNumber;
        this.description = description;
        this.occurrenceDate = LocalDate.now();
        this.location = location;
        this.type = type;
        this.item = item;
        this.status = OccurrenceStatus.WAITING;
        this.isDeleted = false;
        this.hasExpert = false;
        this.hasTechnician = false;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }
}