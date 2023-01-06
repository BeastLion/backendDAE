package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Test {
    @Id
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    public Test() {
    }

    public Test(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
