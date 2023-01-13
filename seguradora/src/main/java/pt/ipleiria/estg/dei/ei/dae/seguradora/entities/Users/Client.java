package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Client")
@Getter
@Setter
public class Client extends User {
    @Column(unique = true)
    @NotNull
    private int financialNumber;
    @NotNull
    private ClientType clientType;
    @NotNull
    LocalDate birthDate;
    @NotNull
    private String address;

    public Client() {
        super();
    }

    public Client(String username,String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,  String password, String email, ClientType clientType) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.birthDate = birthDate;
        this.address = address;
        this.financialNumber = financialNumber;
        this.clientType = clientType;
    }
}
