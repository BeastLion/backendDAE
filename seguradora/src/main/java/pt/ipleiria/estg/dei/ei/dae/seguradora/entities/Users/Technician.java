package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@DiscriminatorValue("Technician")
public class Technician extends User implements Serializable {
    @Transient
    @Getter
    @Setter
    private RepairServices repairServices;

    public Technician() {
        super();
    }

    public Technician(String username,String name, String lastName, String phoneNumber, String password, String email) {
        super(username,name, lastName, phoneNumber,  password, email);
        this.repairServices = null;
    }
}
