package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.LinkedList;

@Entity
public class Insurer implements Serializable { //ver como tornar uma class nao persistente na BD pois o Transient nao esta funcar

    @Id
    private Long id;

    private LinkedList<Expert> experts;


}
