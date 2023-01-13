package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Document;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DocumentDTO implements Serializable {

    private long id;
    private String filename, filepath;

    private Occurrence occurrence;

    public DocumentDTO() {
    }

    public DocumentDTO(long id, String filename, String filePath, Occurrence occurrence) {
        this.id = id;
        this.filename = filename;
        this.filepath = filePath;
        this.occurrence = occurrence;
    }

    public static DocumentDTO from(Document document) {
        return new DocumentDTO(
                document.getId(),
                document.getFilename(),
                document.getFilepath(),
                document.getOccurrence()
        );
    }

    public static List<DocumentDTO> from(List<Document> documents) {
        return documents.stream().map(DocumentDTO::from).collect(Collectors.toList());
    }
}