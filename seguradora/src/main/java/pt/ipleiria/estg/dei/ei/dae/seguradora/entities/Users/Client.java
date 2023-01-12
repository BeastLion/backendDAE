package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@DiscriminatorValue("C")
public class Client extends User {

    @Column(unique = true)
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

    @Transient
    @Getter
    @Setter
    private List<Policy> policies;

    @OneToMany(mappedBy = "client")
    private List<Occurrence> occurrences;

    public Client() {
        super();
        this.policies = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }

    public Client(String username,String name, String lastName, LocalDate birthDate, String address, String phoneNumber, int financialNumber,  String password, String email, ClientType clientType) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.birthDate = birthDate;
        this.address = address;
        this.financialNumber = financialNumber;
        this.clientType = clientType;
        this.insurenceNumber = 1L;
        this.policies = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }
    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        this.policies.remove(policy);
    }
}
