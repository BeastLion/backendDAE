package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Enum.ClientType;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "getAllUsers", query = "SELECT u FROM User u ORDER BY u.id")
})
public class User {

    @Id
    @Getter
    @Setter
    private Long id;
    @NotNull
    @Getter
    @Setter
    private String name;
    @NotNull
    @Getter
    @Setter
    private String lastName;
    @NotNull
    @Getter
    @Setter
    private LocalDate birthDate;
    @NotNull
    @Getter
    @Setter
    private String address;
    @NotNull
    //@Pattern(regexp="^[9][0-9]{8}$",message="Invalid Phone Number") //começa por nove tem oito digitos asseguir de 0 a 9
    @Getter
    @Setter
    private int phoneNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ClientType clientType;
    @NotNull
    @Getter
    @Setter
    private int financialNumber;
    @NotNull
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Email
    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    public User() {
    }

    public User(Long id, String name, String lastName, LocalDate birthDate, String address, int phoneNumber, ClientType clientType, int financialNumber, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.financialNumber = financialNumber;
        this.username = username;
        this.password = password;
        this.email = email;
    }


}
