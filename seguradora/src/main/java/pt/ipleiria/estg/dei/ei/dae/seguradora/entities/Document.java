package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "documents")
@Entity
@Getter
@Setter
public class Document implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    @NotNull
    private String filepath, filename;
    @ManyToOne
    @NotNull
    private Occurrence occurrence;

    public Document() {
    }

    public Document(String filepath, String filename, Occurrence occurrence) {
        this();
        this.filepath = filepath;
        this.filename = filename;
        this.occurrence = occurrence;
    }

}