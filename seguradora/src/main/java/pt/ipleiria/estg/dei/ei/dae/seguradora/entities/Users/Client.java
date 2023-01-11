package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("C")
@Table(name = "Clients")
public class Client extends User {
    @NotNull
    @Getter
    @Setter
    private int financialNumber;
    @NotNull
    @Getter
    @Setter
    private ClientType clientType;
    @NotNull
    @Getter
    @Setter
    LocalDate birthDate;
    @NotNull
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private Long insurenceNumber;

    /*
    @OneToMany(mappedBy = "client")
    private List<Occurrence> occurrences;
     */

    public Client() {
        super();
    }

    public Client(String username,String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,  String password, String email, ClientType clientType) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.birthDate = birthDate;
        this.address = address;
        this.financialNumber = financialNumber;
        this.clientType = clientType;
        this.insurenceNumber = 1l;
    }
}
