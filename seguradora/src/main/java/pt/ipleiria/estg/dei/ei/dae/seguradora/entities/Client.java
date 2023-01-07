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

    public Client() {

    }

    public Client(String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,String username, String password, String email, ClientType clientType) {
        super(name, lastName, birthDate, address, phoneNumber,username, password, email);
        this.financialNumber = financialNumber;
        this.clientType = clientType;
    }
}
