package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientBean {

    @PersistenceContext
    EntityManager em;



}
