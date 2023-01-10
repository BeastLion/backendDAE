package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class RepairServices implements Serializable {

    @Id
    private int id;

    @Transient
    private String name;

    @Transient
    private Insurer insurer;

}
