package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.LinkedList;

@Entity
public class Insurer implements Serializable {

    @Id
    private Long id;

    @Transient
    private LinkedList<Expert> experts; //podemos meter so experts

    @Transient
    private LinkedList<Client> clients;

    @Transient
    private LinkedList<RepairServices> repairServices;

}