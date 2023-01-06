package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;

}
