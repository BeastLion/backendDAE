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
@EqualsAndHashCode
@ToString
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
    @Transient
    private List<Policy> policies;

    public Client() {
        super();
        this.policies = new ArrayList<>();
    }

    public Client(String username,String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,  String password, String email, ClientType clientType) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.birthDate = birthDate;
        this.address = address;
        this.financialNumber = financialNumber;
        this.clientType = clientType;
        this.policies = new ArrayList<>();
    }
    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        this.policies.remove(policy);
    }
}
