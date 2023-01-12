package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Entity
@Table(name = "users",uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))

public class User implements Serializable {
    @Id
    @Getter
    @Setter
    private String username;
    @NotNull
    @Getter
    @Setter
    private String name;
    @NotNull
    @Getter
    @Setter
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp="^[9][0-9]{8}$",message="Invalid Phone Number") //começa por nove tem oito digitos asseguir de 0 a 9
    @Getter
    @Setter
    private String phoneNumber;
    @NotNull
    @Getter
    @Setter
    private String password;
    @NotNull
    @Email
    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    @OneToMany(mappedBy = "user")
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

}
