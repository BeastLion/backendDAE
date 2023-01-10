package pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer;


import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsurerType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;

import javax.persistence.Transient;
import java.util.LinkedList;

public class Insurer {

    @Transient
    private int id;

    @Transient
    private String name;

    @Transient
    private InsurerOwner owner;

    @Transient
    private LinkedList<OccurrenceType> occurrenceTypes;

    @Transient
    private InsurerType insurerType;

    public Insurer() {
        this.occurrenceTypes = new LinkedList<>();
    }

    public Insurer(int id, String name, InsurerOwner owner, InsurerType insurerType) {
        this();
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.insurerType = insurerType;
    }
}