package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Entity
@DiscriminatorValue("E")
@Table(name = "Experts")
public class Expert extends User implements Serializable {
    @Getter
    @Setter
    @NotNull
    private long insurerOwner;

    public Expert() {
        super();
    }

    public Expert(String username, String name, String lastName, String phoneNumber, String password, String email) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.insurerOwner = -1;
    }
}
