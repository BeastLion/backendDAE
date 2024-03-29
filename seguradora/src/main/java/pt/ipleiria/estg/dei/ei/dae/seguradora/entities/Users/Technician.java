package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@DiscriminatorValue("Technician")
@Getter
@Setter
public class Technician extends User implements Serializable {

    public Technician() {
        super();
    }

    public Technician(String username,String name, String lastName, String phoneNumber, String password, String email) {
        super(username,name, lastName, phoneNumber,  password, email);
    }
}
