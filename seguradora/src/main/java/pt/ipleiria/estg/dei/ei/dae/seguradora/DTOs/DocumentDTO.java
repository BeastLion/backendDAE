package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DocumentDTO implements Serializable {
    @NotBlank
    private long id;

    @NotBlank
    private String filename;

    public DocumentDTO() {
    }

    public DocumentDTO(long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public static DocumentDTO from(Document document) {
        return new DocumentDTO(
                document.getId(),
                document.getFilename()
        );
    }

    public static List<DocumentDTO> from(List<Document> documents) {
        return documents.stream().map(DocumentDTO::from).collect(Collectors.toList());
    }
}