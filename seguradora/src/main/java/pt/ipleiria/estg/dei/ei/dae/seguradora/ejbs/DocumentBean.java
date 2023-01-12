package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Document;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DocumentBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private OccurrenceBean occurrenceBean;

    public Document create(String filepath, String filename, Long id) throws MyEntityNotFoundException {
        var occurrence = occurrenceBean.findOrFailOccurrence(id);
        Document document = new Document(filepath, filename, occurrence);
        em.persist(document);
        return document;
    }

    public Document find(Long id) {
        return em.find(Document.class, id);
    }

    public List<Document> getOccurenceDocuments(Long id) throws MyEntityNotFoundException {
        var occurrence = occurrenceBean.findOrFailOccurrence(id);
        return occurrence.getDocuments();
    }
}