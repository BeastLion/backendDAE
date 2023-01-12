package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

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

    @PostConstruct
    public void populateDB() {

        System.out.println("Hello Java EE!");

        clientBean.create("ola", "ola","ola", LocalDate.parse("1979-12-28"),
                "ola", "910202022", 5000000,
                "ola", "ola@ola.pt");

        expertBean.create("expert1","Expert","1","912345678","expert1","expert1@mail.pt");
        expertBean.create("expert2","Expert","2","912345672","expert2","expert2@mail.pt");

        technicianBean.create("tech1","Tech","1","912345633","tech1","tech1@mail.pt");
        technicianBean.create("tech2","Tech","2","912345634","tech2","tech2@mail.pt");

        //Api get data
        System.out.println("ola");
        System.out.println("ola");
        System.out.println("ola");
        insurerBean.getAll();
        policyBean.getAll();
    }
}
