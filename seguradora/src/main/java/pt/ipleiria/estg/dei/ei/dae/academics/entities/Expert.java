package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("E")
@Table(name="Experts")
public class Expert extends User {

}
