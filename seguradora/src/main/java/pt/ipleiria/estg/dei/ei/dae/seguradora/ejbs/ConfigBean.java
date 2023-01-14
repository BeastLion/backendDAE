package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDate;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private ClientBean clientBean;
    @EJB
    private InsurerBean insurerBean;
    @EJB
    private ExpertBean expertBean;
    @EJB
    private TechnicianBean technicianBean;
    @EJB
    private PolicyBean policyBean;
    @EJB
    private OccurrenceBean occurrenceBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {
        try {
            System.out.println("Hello Java EE!");

            clientBean.create("client1", "client1", "1", LocalDate.parse("1979-12-28"),
                    "client1", "910202022", 5000000,
                    "client1", "client1@ola.pt");

            clientBean.create("client2", "client2", "2", LocalDate.parse("1979-12-28"),
                    "client2", "910202012", 2000000,
                    "client2", "client2@ola.pt");

            expertBean.create("expert1", "Expert", "1", "912345678", "expert1", "expert1@mail.pt");
            expertBean.create("expert2", "Expert", "2", "912345672", "expert2", "expert2@mail.pt");

            expertBean.create("expert3", "Expert", "3", "912345674", "expert3", "expert3@mail.pt");
            expertBean.create("expert4", "Expert", "4", "911344672", "expert4", "expert4@mail.pt");


            technicianBean.create("tech1", "Tech", "1", "912345633", "tech1", "tech1@mail.pt");
            technicianBean.create("tech2", "Tech", "2", "912345634", "tech2", "tech2@mail.pt");
            technicianBean.create("tech3", "Tech", "3", "911345694", "tech3", "tech3@mail.pt");
            technicianBean.create("tech4", "Tech", "4", "912349614", "tech4", "tech4@mail.pt");


            //Api get data
            insurerBean.getAll();
            policyBean.getAll();

            occurrenceBean.create(1l, "Aparelho dentario", "Rua Bar la fiesta", OccurrenceType.DENTIST, "Aparelho", "client1");
            occurrenceBean.create(2l, "Aparelho dentario", "Rua Bar la fiesta", OccurrenceType.THEFT, "Aparelho", "client2");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
