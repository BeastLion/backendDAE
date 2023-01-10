package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.LinkedList;


public class Insurer {

    private LinkedList<Expert> experts; //podemos meter so experts

    private LinkedList<Client> clients;

    private LinkedList<RepairServices> repairServices;

}