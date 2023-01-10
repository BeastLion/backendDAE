package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("C")
@Table(name="Clients")
public class Client extends User implements Serializable {

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

    }

    public Client(String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,String username, String password, String email, ClientType clientType, Long insurenceNumber) {
        super(name, lastName, phoneNumber,username, password, email);
        this.birthDate = birthDate;
        this.address = address;
        this.financialNumber = financialNumber;
        this.clientType = clientType;
        this.insurenceNumber = insurenceNumber;
    }
}
