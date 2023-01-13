package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.InsurerOwner;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("Expert")
@Getter
@Setter
public class Expert extends User implements Serializable {
    @Transient
    private InsurerOwner insurerOwner;

    public Expert() {
        super();
    }

    public Expert(String username, String name, String lastName, String phoneNumber, String password, String email) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.insurerOwner = null;
    }
}
