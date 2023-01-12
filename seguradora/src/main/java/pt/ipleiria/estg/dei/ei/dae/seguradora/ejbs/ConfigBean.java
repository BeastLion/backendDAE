package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.OccurrenceType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDate;

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

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {

        System.out.println("Hello Java EE!");

        clientBean.create("ola", "ola","ola", LocalDate.parse("1979-12-28"),
                "ola", "910202022", 5000000,
                "ola", "ola@ola.pt");

        expertBean.create("expert1","Expert","1","912345678","expert1","expert1@mail.pt");
        expertBean.create("expert2","Expert","2","912345672","expert2","expert2@mail.pt");

        technicianBean.create("tech1","Tech","1","912345633","tech1","tech1@mail.pt");
        technicianBean.create("tech2","Tech","2","912345634","tech2","tech2@mail.pt");


        //Api get data
        insurerBean.getAll();
        policyBean.getAll();

        occurrenceBean.create(1l,"Aparelho dentario","Rua Bar la fiesta", OccurrenceType.OTHERS,"Aparelho","ola");
        occurrenceBean.create(1l,"Aparelho dentario","Rua Bar la fiesta", OccurrenceType.OTHERS,"Aparelho","ola");
    }
}
