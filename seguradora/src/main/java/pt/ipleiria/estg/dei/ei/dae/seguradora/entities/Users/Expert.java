package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("E")
@Table(name="Experts")

public class Expert extends User {
    public Expert() {
        super();
    }

    public Expert(String name, String lastName, String phoneNumber, String username, String password, String email) {
        super(name, lastName, phoneNumber, username, password, email);
    }
}
