package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.GET;
import java.io.Serializable;

@Entity
@DiscriminatorValue("E")
@Table(name="Experts")
public class Expert extends User implements Serializable {

    @Getter
    @Setter
    private Insurer insurer;

    public Expert(String name, String lastName, String phoneNumber, String username, String password, String email, Insurer insurer) {
        super(name, lastName, phoneNumber, username, password, email);
        this.insurer = insurer;
    }


}
