package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.LinkedList;

@Entity
public class Insurer  implements Serializable {

    private LinkedList<Expert> experts;

}
