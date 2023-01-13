package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "userType")
@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {
    @Id
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    @Column(unique = true)
    @Pattern(regexp="^[9][0-9]{8}$",message="Invalid Phone Number") //começa por nove tem oito digitos asseguir de 0 a 9
    private String phoneNumber;
    @NotNull
    private String password;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<Occurrence> occurrences;
    @Version
    private int version;

    public User() {
        this.occurrences = new ArrayList<>();
    }

    public User(String username,String name, String lastName, String phoneNumber, String password, String email) {
        this();
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.occurrences = new ArrayList<>();
    }

    public String getUserType() {
        DiscriminatorValue userType = this.getClass().getAnnotation(DiscriminatorValue.class);
        return userType == null ? null : userType.value();
    }
    public void addOccurrence(Occurrence occurrence) {
        this.occurrences.add(occurrence);
    }

    public void removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
    }
}
